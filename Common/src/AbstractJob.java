import javafx.concurrent.Task;

/**
 * The Class AbstractJob.
 */
public class AbstractJob extends Task<Void> {

    /**
     * Call.
     *
     * @return the void
     * @throws Exception the exception
     */
    @Override
    protected Void call() throws Exception {
        return null;
    }

    /**
     * Stop.
     */
    public void stop() {
        this.cancel();
    }
}
