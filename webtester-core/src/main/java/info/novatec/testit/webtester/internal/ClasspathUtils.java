package info.novatec.testit.webtester.internal;

public final class ClasspathUtils {

    public static final boolean KOTLIN_AVAILABLE = isKotlinOnClasspath();

    private static boolean isKotlinOnClasspath() {
        try {
            Class.forName("kotlin.KotlinVersion");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
