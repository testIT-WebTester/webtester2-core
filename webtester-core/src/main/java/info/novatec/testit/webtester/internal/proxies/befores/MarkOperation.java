package info.novatec.testit.webtester.internal.proxies.befores;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.markings.Marker;


@Slf4j
public class MarkOperation implements BeforeOperation {

    @Override
    public boolean shouldBeInvokedFor(Method method) {
        return method.isAnnotationPresent(Mark.class);
    }

    @Override
    public void invoke(Object proxy, Method method, Object[] args) {
        Mark annotation = method.getAnnotation(Mark.class);
        markPageFragment(( PageFragment ) proxy, annotation.value());
    }

    private void markPageFragment(PageFragment proxy, As as) {
        if (As.READ == as) {
            Marker.markAsRead(proxy);
        } else if (As.USED == as) {
            Marker.markAsUsed(proxy);
        }
    }

}
