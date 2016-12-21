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
public class SequenceTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractSequenceTest {

        @Mock
        MouseDriver mouseDriver;
        @Mock
        PageFragment fragment;
        @InjectMocks
        Sequence cut;

    }

    public static class Click extends AbstractSequenceTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.click(fragment);
            verify(mouseDriver).click(fragment);
        }

        @Test
        public void invocationReturnsSequenceWithFragment() {
            SequenceWithFragment sequence = cut.click(fragment);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(fragment);
        }

    }

    public static class DoubleClick extends AbstractSequenceTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.doubleClick(fragment);
            verify(mouseDriver).doubleClick(fragment);
        }

        @Test
        public void invocationReturnsSequenceWithFragment() {
            SequenceWithFragment sequence = cut.doubleClick(fragment);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(fragment);
        }

    }

    public static class ContextClick extends AbstractSequenceTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.contextClick(fragment);
            verify(mouseDriver).contextClick(fragment);
        }

        @Test
        public void invocationReturnsSequenceWithFragment() {
            SequenceWithFragment sequence = cut.contextClick(fragment);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(fragment);
        }

    }

    public static class MoveTo extends AbstractSequenceTest {

        @Test
        public void invocationIsDelegatedToDriver() {
            cut.moveTo(fragment);
            verify(mouseDriver).moveTo(fragment);
        }

        @Test
        public void invocationReturnsSequenceWithFragment() {
            SequenceWithFragment sequence = cut.moveTo(fragment);
            assertThat(sequence.getMouseDriver()).isSameAs(mouseDriver);
            assertThat(sequence.getFragment()).isSameAs(fragment);
        }

    }

}
