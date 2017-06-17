package info.novatec.testit.webtester.internal.implementation.advices;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.internal.OffersBrowserGetter;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.waiting.Wait;


@Slf4j
public class ActionAdvice {

    @Advice.OnMethodEnter
    public static void action(@Advice.This OffersBrowserGetter browserGetter, @Advice.Origin Method method) {
        new ActionAdviceImpl(browserGetter, method).execute();
    }

    public static class ActionAdviceImpl {

        private final OffersBrowserGetter browserGetter;
        private final Method method;

        public ActionAdviceImpl(OffersBrowserGetter browserGetter, Method method) {
            this.browserGetter = browserGetter;
            this.method = method;
        }

        public void execute() {

            Browser browser = browserGetter.browser();
            Configuration configuration = browser.configuration();
            if (configuration.getActionDeceleration() == 0 || !method.isAnnotationPresent(Action.class)) {
                return;
            }

            long deceleration = configuration.getActionDeceleration();
            if (deceleration > 0) {
                log.trace("decelerating method execution by: {}", deceleration);
                Wait.exactly(deceleration, TimeUnit.MILLISECONDS);
            }

        }

    }

}
