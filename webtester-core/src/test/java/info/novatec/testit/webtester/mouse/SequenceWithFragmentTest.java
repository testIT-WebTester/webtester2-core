package info.novatec.testit.webtester.mouse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class SequenceWithFragmentTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractSequenceWithFragmentTest {

        @Mock
        MouseDriver mouseDriver;
        @Mock
        PageFragment fragment;
        @InjectMocks
        SequenceWithFragment cut;

    }

    public static class Click extends AbstractSequenceWithFragmentTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.click();
            verify(mouseDriver).click(fragment);
        }

        @Test
        public void invocationReturnsSameSequenceWithFragment() {
            SequenceWithFragment sequence = cut.click();
            assertThat(sequence).isSameAs(cut);
        }

    }

    public static class ClickPageFragment extends AbstractSequenceWithFragmentTest {

        @Mock
        PageFragment anotherFragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.click(anotherFragment);
            verify(mouseDriver).click(anotherFragment);
        }

        @Test
        public void invocationReturnsNewSequenceWithFragment() {
            SequenceWithFragment sequence = cut.click(anotherFragment);
            assertThat(sequence).isNotSameAs(cut);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(anotherFragment);
        }

    }

    public static class DoubleClick extends AbstractSequenceWithFragmentTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.doubleClick();
            verify(mouseDriver).doubleClick(fragment);
        }

        @Test
        public void invocationReturnsSameSequenceWithFragment() {
            SequenceWithFragment sequence = cut.doubleClick();
            assertThat(sequence).isSameAs(cut);
        }

    }

    public static class DoubleClickPageFragment extends AbstractSequenceWithFragmentTest {

        @Mock
        PageFragment anotherFragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.doubleClick(anotherFragment);
            verify(mouseDriver).doubleClick(anotherFragment);
        }

        @Test
        public void invocationReturnsNewSequenceWithFragment() {
            SequenceWithFragment sequence = cut.doubleClick(anotherFragment);
            assertThat(sequence).isNotSameAs(cut);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(anotherFragment);
        }

    }

    public static class ContextClick extends AbstractSequenceWithFragmentTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.contextClick();
            verify(mouseDriver).contextClick(fragment);
        }

        @Test
        public void invocationReturnsSameSequenceWithFragment() {
            SequenceWithFragment sequence = cut.contextClick();
            assertThat(sequence).isSameAs(cut);
        }

    }

    public static class ContextClickPageFragment extends AbstractSequenceWithFragmentTest {

        @Mock
        PageFragment anotherFragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.contextClick(anotherFragment);
            verify(mouseDriver).contextClick(anotherFragment);
        }

        @Test
        public void invocationReturnsNewSequenceWithFragment() {
            SequenceWithFragment sequence = cut.contextClick(anotherFragment);
            assertThat(sequence).isNotSameAs(cut);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(anotherFragment);
        }

    }

    public static class MoveToPageFragment extends AbstractSequenceWithFragmentTest {

        @Mock
        PageFragment anotherFragment;

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.moveTo(anotherFragment);
            verify(mouseDriver).moveTo(anotherFragment);
        }

        @Test
        public void invocationReturnsNewSequenceWithFragment() {
            SequenceWithFragment sequence = cut.moveTo(anotherFragment);
            assertThat(sequence).isNotSameAs(cut);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(anotherFragment);
        }

    }

}
