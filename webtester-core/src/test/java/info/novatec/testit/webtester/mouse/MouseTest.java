package info.novatec.testit.webtester.mouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class MouseTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractMouseTest {

        @Mock
        MouseDriver mouseDriver;

        @Before
        public void rememberAndReplaceOriginalWaiter() {
            Mouse.setMouseDriver(() -> mouseDriver);
        }

        @After
        public void restoreOriginalWaiter() {
            Mouse.setMouseDriver(Mouse.DEFAULT_MOUSE_DRIVER);
        }

    }

    public static class Click extends AbstractMouseTest {

        @Mock
        PageFragment fragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            Mouse.click(fragment);
            verify(mouseDriver).click(fragment);
        }

    }

    public static class DoubleClick extends AbstractMouseTest {

        @Mock
        PageFragment fragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            Mouse.doubleClick(fragment);
            verify(mouseDriver).doubleClick(fragment);
        }

    }

    public static class ContextClick extends AbstractMouseTest {

        @Mock
        PageFragment fragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            Mouse.contextClick(fragment);
            verify(mouseDriver).contextClick(fragment);
        }

    }

    public static class MoveTo extends AbstractMouseTest {

        @Mock
        PageFragment fragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            Mouse.moveTo(fragment);
            verify(mouseDriver).moveTo(fragment);
        }

    }

    public static class MoveToEach extends AbstractMouseTest {

        @Mock
        PageFragment fragment1;
        @Mock
        PageFragment fragment2;
        @Mock
        PageFragment fragment3;

        @Test
        public void varargsInvocationIsDelegatedToDriver() {
            Mouse.moveToEach(fragment1, fragment2, fragment3);
            verify(mouseDriver).moveToEach(fragment1, fragment2, fragment3);
        }

        @Test
        public void collectionInvocationIsDelegatedToDriver() {
            List<PageFragment> list = Arrays.asList(fragment1, fragment2, fragment3);
            Mouse.moveToEach(list);
            verify(mouseDriver).moveToEach(list);
        }

    }

    public static class OnFactory extends AbstractMouseTest {

        @Mock
        PageFragment fragment;

        @Test
        public void invocationCreatesNewOnPageFragment() {
            OnPageFragment on = Mouse.on(fragment);
            assertThat(on.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(on.getFragment()).isSameAs(fragment);
        }

    }

    public static class SequenceFactory extends AbstractMouseTest {

        @Test
        public void invocationCreatesNewSequence() {
            Sequence on = Mouse.sequence();
            assertThat(on.getMouseDriver()).isSameAs(mouseDriver);
        }

    }

}
