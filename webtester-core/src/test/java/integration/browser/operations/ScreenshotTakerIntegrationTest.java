package integration.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import integration.BaseIntegrationTest;


public class ScreenshotTakerIntegrationTest extends BaseIntegrationTest {

    /**
     * Matches timestamp based file names like: 2016-01-03T10_15_30.201.png
     */
    private static final String FILE_NAME_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}_\\d{2}_\\d{2}\\.\\d{3}.png";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getHTMLFilePath() {
        return "html/browser/empty.html";
    }

    @Test
    public final void takeAndStoreScreenshotWithDefaults() {

        File actualFile = browser().screenshot().takeAndStore().get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(configuration().getScreenshotFolder());
        assertThat(actualFile.getName()).matches(FILE_NAME_PATTERN);

    }

    @Test
    public final void takeAndStoreScreenshotWithCustomFolder() throws IOException {

        File targetFolder = temporaryFolder.newFolder();

        File actualFile = browser().screenshot().takeAndStore(targetFolder).get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(targetFolder);
        assertThat(actualFile.getName()).matches(FILE_NAME_PATTERN);

    }

    @Test
    public final void takeAndStoreScreenshotWithCustomFolderAndName() throws IOException {

        File targetFolder = temporaryFolder.newFolder();
        String fileNameWithoutSuffix = "foo-bar";

        File actualFile = browser().screenshot().takeAndStore(targetFolder, fileNameWithoutSuffix).get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(targetFolder);
        assertThat(actualFile).hasName("foo-bar.png");

    }

}
