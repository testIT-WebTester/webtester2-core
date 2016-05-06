package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class ConfiguredWait {

    // TODO document

    private final long timeout;
    private final TimeUnit timeUnit;

    public ConfiguredWait(long timeout) {
        this(timeout, TimeUnit.SECONDS);
    }

    public ConfiguredWait(long timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public <T extends PageFragment> WaitUntil<T> until(T fragment) {
        return new WaitUntil<>(fragment, timeout, timeUnit);
    }

}
