package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.AfterClass;
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

    private static final String TEST_PAGE_SERVER_HOST = System.getProperty("testPageServerHost", "localhost");
    private static final int TEST_PAGE_SERVER_PORT = Integer.parseInt(System.getProperty("testPageServerPort", "8080"));
    private static final String TEST_PAGE_SERVER_BASE_ADDRESS = String.format("http://%s:%d/", TEST_PAGE_SERVER_HOST, TEST_PAGE_SERVER_PORT);

    private static Server server;
    private static Browser browser;

    @BeforeClass
    public static void setupBrowser() {
        if (browser == null) {
            browser = new TestBrowserFactory().createBrowser();
        }
    }

    @BeforeClass
    public static void startTestPageServer() throws Exception {
        server = new Server(TEST_PAGE_SERVER_PORT);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(getTestResourceFolder().getCanonicalPath());
        server.setHandler(resourceHandler);
        server.start();
    }

    @AfterClass
    public static void stopTestPageServer() throws Exception {
        server.stop();
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
        assertThatFileResourceExists(fileName);
        return TEST_PAGE_SERVER_BASE_ADDRESS + fileName;
    }

    private static void assertThatFileResourceExists(String fileName) {
        File testResourceFile = new File(getTestResourceFolder(), fileName);
        assertThat(testResourceFile.isFile()).withFailMessage("file not found: " + testResourceFile).isTrue();
    }

    private static File getTestResourceFolder() {
        File srcFolder = new File("src");
        File srcTestFolder = new File(srcFolder, "test");
        return new File(srcTestFolder, "resources");
    }

}
