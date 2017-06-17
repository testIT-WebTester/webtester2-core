package info.novatec.testit.webtester.internal.implementation.advices;

import java.lang.reflect.Method;

import net.bytebuddy.asm.Advice;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.css.DefaultStyleChanger;
import info.novatec.testit.webtester.markings.Marker;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;


public class MarkingAdvice {

    private static final Marker MARKER = new Marker(new DefaultStyleChanger());

    @Advice.OnMethodEnter
    public static void mark(@Advice.This PageFragment pageFragment, @Advice.Origin Method method) {
        new MarkingAdviceImpl(pageFragment, method).execute();
    }

    public static class MarkingAdviceImpl {

        private final PageFragment pageFragment;
        private final Method method;

        public MarkingAdviceImpl(PageFragment pageFragment, Method method) {
            this.pageFragment = pageFragment;
            this.method = method;
        }

        public void execute() {

            Browser browser = pageFragment.browser();
            Configuration configuration = browser.configuration();
            Mark mark = method.getAnnotation(Mark.class);
            if (!configuration.isMarkingsEnabled() || mark == null) {
                return;
            }

            switch (mark.value()) {
                case READ:
                    MARKER.markAsRead(pageFragment);
                    break;
                case USED:
                    MARKER.markAsUsed(pageFragment);
                    break;
            }

        }

    }

}
