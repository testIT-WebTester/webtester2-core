package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class is used to store configuration parameters for wait operations. This includes a timeout, the unit of the
 * timeout
 * as well as an invervall which should be used by periodical check waits like a 'wait until X'.
 *
 * @see Wait
 * @see WaitUntil
 * @since 2.0
 */
@Getter
@Setter
@Accessors(chain = true)
@SuppressWarnings("PMD")
class WaitConfig {

    public static final int DEFAULT_TIMEOUT = 2;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    public static final long DEFAULT_INTERVAL = 100L;

    /** The timeout to use. It's actual value is relative to it's {@link #timeUnit}. */
    private int timeout = DEFAULT_TIMEOUT;
    /** The time unit to use when evaluating the {@link #timeout}. */
    private TimeUnit timeUnit = DEFAULT_TIME_UNIT;
    /** The interval in which to check conditions of 'wait until X' style wait operations. */
    private long interval = DEFAULT_INTERVAL;

    /**
     * Converts the set {@link #timeout} into milliseconds.
     *
     * @return the timeout in milliseconds
     * @since 2.0
     */
    long getTimeoutInMillis() {
        return timeUnit.toMillis(timeout);
    }

    /**
     * Creates a new {@link WaitConfig} from the given {@link PageFragment}'s {@link Configuration}.
     *
     * @param fragment the fragment who's configuration should be used
     * @return the new wait config instance
     * @since 2.0
     */
    static WaitConfig from(PageFragment fragment) {
        return from(fragment.browser());
    }

    /**
     * Creates a new {@link WaitConfig} from the given {@link Browser}'s {@link Configuration}.
     *
     * @param browser the browser who's configuration should be used
     * @return the new wait config instance
     * @since 2.0
     */
    static WaitConfig from(Browser browser) {
        return from(browser.configuration());
    }

    /**
     * Creates a new {@link WaitConfig} from the given {@link Configuration}.
     *
     * @param configuration the configuration to used
     * @return the new wait config instance
     * @since 2.0
     */
    static WaitConfig from(Configuration configuration) {
        return new WaitConfig().setTimeout(configuration.getWaitTimeout())
            .setTimeUnit(TimeUnit.SECONDS)
            .setInterval(configuration.getWaitInterval());
    }

}
