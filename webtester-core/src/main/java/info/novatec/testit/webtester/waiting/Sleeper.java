package info.novatec.testit.webtester.waiting;

/**
 * Implementations of this interface can be used to trigger a 'sleep' state for a certain amount of time.
 *
 * @since 2.0
 */
interface Sleeper {
    /**
     * Sleep for the given amount of milliseconds.
     *
     * @param milliseconds the milliseconds to sleep
     * @throws InterruptedException in case the sleep operation is interrupted
     * @since 2.0
     */
    void sleep(long milliseconds) throws InterruptedException;
}
