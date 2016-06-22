package info.novatec.testit.webtester.waiting;

/**
 * This implementation of {@link Sleeper} will execute the sleep on the current {@link Thread}.
 *
 * @see Sleeper
 * @since 2.0
 */
class CurrentThreadSleeper implements Sleeper {

    @Override
    public void sleep(long milliseconds) throws InterruptionException {
        try {
            Thread.sleep(milliseconds);
        } catch (java.lang.InterruptedException e) {
            throw new InterruptionException(e);
        }
    }

}
