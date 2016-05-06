package integration.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import integration.BaseIntegrationTest;


public class PageSourceIntegrationTest extends BaseIntegrationTest {

    /**
     * Matches timestamp based file names like: 2016-01-03T10_15_30.201.html
     */
    private static final String FILE_NAME_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}_\\d{2}_\\d{2}\\.\\d{3}.html";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected String getHTMLFilePath() {
        return "html/browser/pageSource.html";
    }

    @Test
    public final void savePageSourceWithDefaults() {

        File actualFile = browser().pageSource().save().get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(configuration().getPageSourceFolder());
        assertThat(actualFile.getName()).matches(FILE_NAME_PATTERN);

    }

    @Test
    public final void savePageSourceWithCustomFolder() throws IOException {

        File targetFolder = temporaryFolder.newFolder();

        File actualFile = browser().pageSource().save(targetFolder).get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(targetFolder);
        assertThat(actualFile.getName()).matches(FILE_NAME_PATTERN);

    }

    @Test
    public final void savePageSourceWithCustomFolderAndName() throws IOException {

        File targetFolder = temporaryFolder.newFolder();
        String fileNameWithoutSuffix = "foo-bar";

        File actualFile = browser().pageSource().save(targetFolder, fileNameWithoutSuffix).get();

        assertThat(actualFile).isFile();
        assertThat(actualFile).hasParent(targetFolder);
        assertThat(actualFile).hasName("foo-bar.html");

    }

}
