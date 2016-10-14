package info.novatec.testit.webtester.waiting;

import lombok.extern.slf4j.Slf4j;


/**
 * This implementation of {@link Sleeper} will execute the sleep on the current {@link Thread}.
 *
 * @see Sleeper
 * @since 2.0
 */
@Slf4j
public class CurrentThreadSleeper implements Sleeper {

    @Override
    public void sleep(long milliseconds) throws InterruptionException {
        try {
            log.trace("sleeping for {}ms ...", milliseconds);
            Thread.sleep(milliseconds);
            log.trace("... done sleeping");
        } catch (java.lang.InterruptedException e) {
            throw new InterruptionException(e);
        }
    }

}
