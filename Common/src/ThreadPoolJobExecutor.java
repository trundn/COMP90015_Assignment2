import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Class ThreadPoolJobExecutor.
 */
public class ThreadPoolJobExecutor {

    /** The is stopped. */
    private AtomicBoolean isStopped;

    /** The current thread count. */
    private AtomicInteger currentThreadCount;

    /** The minimum thread count. */
    private int minThreadCount;

    /** The maximum thread count. */
    private int maxThreadCount;

    /** The max queue size. */
    private int maxQueueSize;

    /** The job queue. */
    private BlockingQueue<Runnable> jobQueue;

    /** The thread list. */
    private ArrayList<Worker> threadList;

    /**
     * Instantiates a new thread pool job executor.
     *
     * @param minThreadCount the min thread count
     * @param maxThreadCount the max thread count
     * @param maxQueueSize   the max queue size
     * @throws Exception the exception
     */
    public ThreadPoolJobExecutor(int minThreadCount, int maxThreadCount,
            int maxQueueSize) throws Exception {
        if (this.minThreadCount > this.maxThreadCount) {
            throw new IllegalArgumentException(
                    "The minimum thread count is greater than the maximum thread count.");
        }

        this.minThreadCount = minThreadCount;
        this.maxThreadCount = maxThreadCount;
        this.maxQueueSize = maxQueueSize;

        this.threadList = new ArrayList<Worker>();
        this.jobQueue = new ArrayBlockingQueue<Runnable>(this.maxQueueSize);
        this.isStopped = new AtomicBoolean(false);
        this.currentThreadCount = new AtomicInteger(0);

        if (this.minThreadCount > 0) {
            for (int i = 0; i < this.minThreadCount; i++) {
                // Instantiate the worker thread.
                Worker worker = this.addThread();
                // Start the worker thread.
                worker.start();
            }
        }
    }

    /**
     * Adds the thread.
     *
     * @return the worker
     */
    private Worker addThread() {
        // Instantiate the worker thread.
        Worker worker = new Worker(this.isStopped, this.jobQueue);

        // Increase the current thread count.
        this.currentThreadCount.incrementAndGet();
        this.threadList.add(worker);

        return worker;
    }

    /**
     * Adds the thread if under max.
     */
    private void addThreadIfUnderMax() {
        if (this.maxThreadCount == -1
                || this.currentThreadCount.get() < this.maxThreadCount) {
            Worker worker = this.addThread();
            worker.start();
        }
    }

    /**
     * Queue.
     *
     * @param runnable the runnable
     * @throws InterruptedException the interrupted exception
     */
    public void queue(Runnable runnable) throws InterruptedException {
        if (!this.isStopped.get()) {
            this.jobQueue.put(runnable);
            this.addThreadIfUnderMax();
        } else {
            throw new IllegalStateException(
                    "Thread pool job executor is being terminated. Cannot queue a new runnable task.");
        }
    }

    /**
     * Terminate.
     */
    public void terminate() {
        this.jobQueue.clear();
        stop();
    }

    /**
     * Stop.
     */
    public void stop() {
        this.isStopped.set(true);

        for (Worker worker : this.threadList) {
            for (Runnable runnable : worker.getHandlingJobList()) {
                if (runnable instanceof AbstractJob) {
                    AbstractJob job = (AbstractJob) runnable;
                    job.stop();
                }
            }
        }
    }

    /**
     * Force interrupt.
     */
    public void forceInterrupt() {
        try {
            for (Thread thread : this.threadList) {
                thread.interrupt();
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Wait for termination.
     *
     * @param timeout the timeout in millisecond
     * @throws TimeoutException the timeout exception
     */
    public void waitForTermination(long timeout) throws TimeoutException {
        if (!this.isStopped.get()) {
            throw new IllegalStateException(
                    "Thread pool job executor is not terminated before waiting for termination.");
        }

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= timeout) {
            boolean isAllStopped = true;

            // Check thread alive statuses.
            for (Thread thread : this.threadList) {
                if (thread.isAlive()) {
                    isAllStopped = false;
                    break;
                }
            }

            // All threads are stopped.
            if (isAllStopped) {
                return;
            }

            // Sleep 1 second before checking thread alive status again.
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // Throw the timeout exception.
        throw new TimeoutException(
                "Unable to terminate the thread pool job exectuor within the specified timeout ["
                        + timeout + "ms].");
    }
}
