package info.novatec.testit.webtester.waiting;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class WaitUntil<T extends PageFragment> {

    // TODO document

    private final T fragment;
    private final long timeout;
    private final TimeUnit timeUnit;

    public WaitUntil(T fragment) {
        this.fragment = fragment;
        this.timeout = fragment.getBrowser().configuration().getWaitTimeout();
        this.timeUnit = TimeUnit.SECONDS;
    }

    public WaitUntil(T fragment, long timeout, TimeUnit timeUnit) {
        this.fragment = fragment;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public WaitUntil<T> is(Predicate<T> condition) throws TimeoutException {
        WaitOperations.waitUntil(timeout, timeUnit, fragment, condition);
        return this;
    }

    public WaitUntil<T> isNot(Predicate<T> condition) throws TimeoutException {
        return not(condition);
    }

    public WaitUntil<T> not(Predicate<T> condition) throws TimeoutException {
        WaitOperations.waitUntil(timeout, timeUnit, fragment, Conditions.not(condition));
        return this;
    }

    public WaitUntil<T> and() {
        return this;
    }

    public WaitUntil<T> but() {
        return this;
    }

}
