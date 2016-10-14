package info.novatec.testit.webtester.internal.proxies.befores;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.waiting.Wait;


@Slf4j
@AllArgsConstructor
public class ActionOperation implements BeforeOperation {

    @NonNull
    private final Configuration configuration;

    @Override
    public boolean shouldBeInvokedFor(Method method) {
        return method.isAnnotationPresent(Action.class);
    }

    @Override
    public void invoke(Object proxy, Method method, Object[] args) {
        long deceleration = configuration.getActionDeceleration();
        if (deceleration > 0) {
            log.trace("decelerating method execution by: {}", deceleration);
            Wait.exactly(deceleration, TimeUnit.MILLISECONDS);
        }
    }

}
