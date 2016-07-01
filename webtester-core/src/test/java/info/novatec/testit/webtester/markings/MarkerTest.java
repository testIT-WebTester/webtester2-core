package info.novatec.testit.webtester.markings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.support.Color;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.css.CssProperties;
import info.novatec.testit.webtester.css.StyleChanger;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class MarkerTest {

    @RunWith(MockitoJUnitRunner.class)
    private static abstract class AbstractMarkerTest {

        @Mock
        StyleChanger styleChanger;
        @InjectMocks
        Marker cut;

        @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        PageFragment fragment;
        @Mock
        Configuration configuration;
        @Captor
        ArgumentCaptor<Map<String, String>> cssCaptor;

        @Before
        public void configuration() {
            when(fragment.browser().configuration()).thenReturn(configuration);
        }

    }

    public static class MarkAsRead extends AbstractMarkerTest {

        @Test
        public void styleIsNotChangedIfMarkingsAreDeactivated() {
            doReturn(false).when(configuration).isMarkingsEnabled();
            cut.markAsRead(fragment);
            verifyZeroInteractions(styleChanger);
        }

        @Test
        public void styleIsChangedIfMarkingsAreActivated() {

            doReturn(Color.fromString("#aaaaaa")).when(configuration).getMarkingsColorReadBackground();
            doReturn(Color.fromString("#bbbbbb")).when(configuration).getMarkingsColorReadOutline();
            doReturn(true).when(configuration).isMarkingsEnabled();

            cut.markAsRead(fragment);

            verify(styleChanger).changeStyleInformation(same(fragment), cssCaptor.capture());
            Map<String, String> properties = cssCaptor.getValue();

            assertThat(properties).containsEntry(CssProperties.OUTLINE_STYLE, "solid");
            assertThat(properties).containsEntry(CssProperties.OUTLINE_WIDTH, "2px");
            assertThat(properties).containsEntry(CssProperties.OUTLINE_COLOR, "#bbbbbb");
            assertThat(properties).containsEntry(CssProperties.BACKGROUND_COLOR, "#aaaaaa");

        }

    }

    public static class MarkAsUsed extends AbstractMarkerTest {

        @Test
        public void styleIsNotChangedIfMarkingsAreDeactivated() {
            doReturn(false).when(configuration).isMarkingsEnabled();
            cut.markAsUsed(fragment);
            verifyZeroInteractions(styleChanger);
        }

        @Test
        public void styleIsChangedIfMarkingsAreActivated() {

            doReturn(Color.fromString("#cccccc")).when(configuration).getMarkingsColorUsedBackground();
            doReturn(Color.fromString("#dddddd")).when(configuration).getMarkingsColorUsedOutline();
            doReturn(true).when(configuration).isMarkingsEnabled();

            cut.markAsUsed(fragment);

            verify(styleChanger).changeStyleInformation(same(fragment), cssCaptor.capture());
            Map<String, String> properties = cssCaptor.getValue();

            assertThat(properties).containsEntry(CssProperties.OUTLINE_STYLE, "solid");
            assertThat(properties).containsEntry(CssProperties.OUTLINE_WIDTH, "2px");
            assertThat(properties).containsEntry(CssProperties.OUTLINE_COLOR, "#dddddd");
            assertThat(properties).containsEntry(CssProperties.BACKGROUND_COLOR, "#cccccc");

        }

    }

}
