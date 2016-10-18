package info.novatec.testit.webtester.css;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriverException;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.operations.JavaScriptExecutor;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class DefaultStyleChangerTest {

    @Mock
    PageFragment fragment;
    @Mock
    Browser browser;
    @Mock
    JavaScriptExecutor javaScript;

    @InjectMocks
    DefaultStyleChanger cut;

    @Before
    public void init() {
        doReturn(browser).when(fragment).browser();
        doReturn(javaScript).when(browser).javaScript();
    }

    @Test
    public void changingSinglePropertyCreatesCorrectScript() {

        boolean styleWasChanged = changeSingleProperty();
        assertThat(styleWasChanged).isTrue();

        String expectedScript = "arguments[0].style.backgroundColor='#214284';";
        verify(javaScript).execute(expectedScript, fragment);

    }

    @Test
    public void changingSinglePropertyIsAnOptionalOperation() {
        throwExceptionOnJavaScriptExecution(); // will be caught
        boolean styleWasChanged = changeSingleProperty();
        assertThat(styleWasChanged).isFalse();
    }

    boolean changeSingleProperty() {
        return cut.changeStyleInformation(fragment, CssProperties.BACKGROUND_COLOR, "#214284");
    }

    @Test
    public void changingMultiplePropertiesCreatesCorrectScript() {

        boolean styleWasChanged = changeMultipleProperties();
        assertThat(styleWasChanged).isTrue();

        String expectedScript = "arguments[0].style.outlineColor='#214284';"
            + "arguments[0].style.outlineStyle='solid';"
            + "arguments[0].style.outlineWidth='2px';";
        verify(javaScript).execute(expectedScript, fragment);

    }

    @Test
    public void changingMultiplePropertiesIsAnOptionalOperation() {
        throwExceptionOnJavaScriptExecution(); // will be caught
        boolean styleWasChanged = changeMultipleProperties();
        assertThat(styleWasChanged).isFalse();
    }

    boolean changeMultipleProperties() {
        Map<String, String> properties = new TreeMap<>();
        properties.put(CssProperties.OUTLINE_COLOR, "#214284");
        properties.put(CssProperties.OUTLINE_STYLE, "solid");
        properties.put(CssProperties.OUTLINE_WIDTH, "2px");
        return cut.changeStyleInformation(fragment, properties);
    }

    private void throwExceptionOnJavaScriptExecution() {
        doThrow(new WebDriverException()).when(javaScript).execute(anyString(), any(PageFragment.class));
    }

}
