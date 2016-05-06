package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.operations.AlertHandler;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseIntegrationTest {

    private static Browser browser;

    @BeforeClass
    public static void setUpBeforeClass() {
        if (browser == null) {
            browser = new TestBrowserFactory().createBrowser();
        }
    }

    @Before
    public final void navigateToTestURL() {
        String htmlFilePath = getHTMLFilePath();
        if (htmlFilePath != null) {
            browser.open().url(getUrlFor(htmlFilePath));
        }
    }

    protected void open(String filePath) {
        browser.open().url(getUrlFor(filePath));
    }

    // ABSTRACTS

    protected String getHTMLFilePath() {
        return null;
    }

    // UTILITY

    protected static Browser browser() {
        return browser;
    }

    protected Configuration configuration() {
        return browser().configuration();
    }

    protected AlertHandler alert() {
        return browser().alert();
    }

    protected <T extends Page> T create(Class<T> pageClass) {
        return browser().create(pageClass);
    }

    protected String currentPageTitle() {
        return browser().currentPageTitle();
    }

    protected void assertPageFragmentCanBeInitialized(PageFragment fragment) {
        assertThat(fragment.isPresent()).isTrue();
    }

    protected EventSystem eventSystem(){
        return browser().events();
    }

    protected static String getUrlFor(String fileName) {

        File testResourceFolder = getTestResourceFolder();
        File testResourceFile = new File(testResourceFolder, fileName);

        assertThat(testResourceFile.isFile()).withFailMessage("file not found: " + testResourceFile).isTrue();

        String systemDependentPath = testResourceFile.getAbsolutePath();
        String replacedBackslashes = systemDependentPath.replaceAll("\\\\", "/");
        String replacedWhitespaces = replacedBackslashes.replaceAll(" ", "%20");

        return "file:///" + replacedWhitespaces;

    }

    private static File getTestResourceFolder() {
        File srcFolder = new File("src");
        File srcTestFolder = new File(srcFolder, "test");
        return new File(srcTestFolder, "resources");
    }

}
