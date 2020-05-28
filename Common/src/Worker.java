import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The Class Worker.
 */
public class Worker extends Thread {

    /** The is stopped. */
    private AtomicBoolean isStopped;

    /** The job queue. */
    private BlockingQueue<Runnable> jobQueue;

    /** The handling job list. */
    private ArrayList<Runnable> handlingJobList;

    /**
     * Worker.
     *
     * @param isStopped the is stopped
     * @param jobQueue  the job queue
     */
    public Worker(AtomicBoolean isStopped, BlockingQueue<Runnable> jobQueue) {
        this.isStopped = isStopped;
        this.jobQueue = jobQueue;
        this.handlingJobList = new ArrayList<Runnable>();
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        try {
            while (!isStopped.get()) {
                Runnable runnable = jobQueue.take();
                if (runnable != null) {
                    this.handlingJobList.add(runnable);
                    runnable.run();
                }
            }
        } catch (RuntimeException | InterruptedException ex) {
            //ex.printStackTrace();
        }
    }

    /**
     * Gets the handling job list.
     *
     * @return the handling job list
     */
    public ArrayList<Runnable> getHandlingJobList() {
        return handlingJobList;
    }

}
