package info.novatec.testit.webtester.internal;

public final class Caches {

    public static void clear() {
        WebElementFinder.clearCache();
    }

    private Caches() {
        // utility class constructor
    }

}
