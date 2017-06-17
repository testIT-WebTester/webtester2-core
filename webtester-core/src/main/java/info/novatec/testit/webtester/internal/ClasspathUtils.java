package info.novatec.testit.webtester.internal;

public final class ClasspathUtils {

    public static final String KOTLIN_PAGE_CLASS = "info.novatec.testit.webtester.kotlin.pages.Page";
    public static final String KOTLIN_PAGE_FRAGMENT_CLASS =
        "info.novatec.testit.webtester.kotlin.pagefragments.PageFragment";

    public static final boolean KOTLIN_AVAILABLE = isClassAvailable("kotlin.KotlinVersion");
    public static final boolean KOTLIN_MODULE_LOADED =
        isClassAvailable(KOTLIN_PAGE_CLASS) && isClassAvailable(KOTLIN_PAGE_FRAGMENT_CLASS);

    private static boolean isClassAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
