package info.novatec.testit.webtester.internal;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class TestItHome {

    private static final String ENV_TESTIT_HOME = "TESTIT_HOME";
    private static final String PROPERTY_USER_HOME = "user.home";
    private static final String FOLDER_TESTIT = ".testit";

    private TestItHome() {
    }

    public static File getOrCreateTestITHomeFolder() {

        File testITHomeFolder = getTestITHomeFolder();
        if (!testITHomeFolder.isDirectory() && testITHomeFolder.mkdirs()) {
            log.info("created testIT home folder: {}", testITHomeFolder);
        }

        return testITHomeFolder;

    }

    public static File getTestITHomeFolder() {

        String testITHome = System.getenv(ENV_TESTIT_HOME);
        if (!StringUtils.isEmpty(testITHome)) {
            return new File(testITHome);
        }

        String userHome = System.getProperty(PROPERTY_USER_HOME);
        if (!StringUtils.isEmpty(userHome)) {
            File userHomeFolder = new File(userHome);
            return new File(userHomeFolder, FOLDER_TESTIT);
        }

        return new File("home");

    }

    public static File getTestITTempFolder() {
        File tempFolder = getTestITHomeSubFolder("temp");
        if (!tempFolder.isDirectory() && tempFolder.mkdirs()) {
            log.info("created testIT temp folder: {}", tempFolder);
        }
        return tempFolder;
    }

    public static File getTestITConfigFolder() {
        File confFolder = getTestITHomeSubFolder("config");
        if (!confFolder.isDirectory() && confFolder.mkdirs()) {
            log.info("created testIT configuration folder: {}", confFolder);
        }
        return confFolder;
    }

    public static File getTestITHomeSubFolder(String subFolderName) {
        return new File(getTestITHomeFolder(), subFolderName);
    }

}
