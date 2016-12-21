package info.novatec.testit.webtester.mouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class OnPageFragmentTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractMouseOnActionTest {

        @Mock
        MouseDriver mouseDriver;
        @Mock
        PageFragment fragment;
        @InjectMocks
        OnPageFragment cut;

    }

    public static class Click extends AbstractMouseOnActionTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.click();
            verify(mouseDriver).click(fragment);
        }

        @Test
        public void invocationReturnsSameInstance() {
            OnPageFragment fluentInstance = cut.click();
            assertThat(fluentInstance).isSameAs(cut);
        }

    }

    public static class DoubleClick extends AbstractMouseOnActionTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.doubleClick();
            verify(mouseDriver).doubleClick(fragment);
        }

        @Test
        public void invocationReturnsSameInstance() {
            OnPageFragment fluentInstance = cut.doubleClick();
            assertThat(fluentInstance).isSameAs(cut);
        }

    }

    public static class ContextClick extends AbstractMouseOnActionTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.contextClick();
            verify(mouseDriver).contextClick(fragment);
        }

        @Test
        public void invocationReturnsSameInstance() {
            OnPageFragment fluentInstance = cut.contextClick();
            assertThat(fluentInstance).isSameAs(cut);
        }

    }

}
