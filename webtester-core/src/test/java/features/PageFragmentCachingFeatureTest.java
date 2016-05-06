package features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Cached;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdStartsWith;
import info.novatec.testit.webtester.pages.Page;


@Slf4j
public class PageFragmentCachingFeatureTest extends BaseIntegrationTest {

    private static final int ITERATIONS = 10;

    @Before
    public void openPage() {
        open("html/features/page-fragment-caching.html");
        browser().configuration().setCaching(false);
    }

    @Test
    public void demonstrateWebElementCache() {

        log.info("measuring impact of caching on page fragment");

        FeaturePage page = browser().create(FeaturePage.class);

        // warm up
        page.text().webElement();
        page.cachedText().webElement();

        // execute web element loading
        long[] uncached = executeTimes(ITERATIONS, () -> page.text().webElement());
        long[] cached = executeTimes(ITERATIONS, () -> page.cachedText().webElement());

        // cached calls should always be faster then non cached
        for (int i = 0; i < ITERATIONS; i++) {
            log.info("{}: {}ms >> {}ms", (i + 1), toMillis(uncached[i]), toMillis(cached[i]));
        }

        long sum = Arrays.stream(uncached).sum();
        long cachedSum = Arrays.stream(cached).sum();
        log.info("total time: {}ms >> {}ms", toMillis(sum), toMillis(cachedSum));
        assertThat(cachedSum).isLessThan(sum);

    }

    @Test
    public void demonstrateWebElementListCache() {

        log.info("measuring impact of caching on list of page fragments");

        FeaturePage page = browser().create(FeaturePage.class);

        // warm up
        page.texts().forEach(PageFragment::webElement);
        page.cachedTexts().forEach(PageFragment::webElement);

        // execute web element loading
        long[] uncached = executeTimes(ITERATIONS, () -> page.texts().forEach(PageFragment::webElement));
        long[] cached = executeTimes(ITERATIONS, () -> page.cachedTexts().forEach(PageFragment::webElement));

        // cached calls should always be faster then non cached
        for (int i = 0; i < ITERATIONS; i++) {
            log.info("{}: {}ms >> {}ms", (i + 1), toMillis(uncached[i]), toMillis(cached[i]));
        }

        long sum = Arrays.stream(uncached).sum();
        long cachedSum = Arrays.stream(cached).sum();
        log.info("total time: {}ms >> {}ms", toMillis(sum), toMillis(cachedSum));
        assertThat(cachedSum).isLessThan(sum);

    }

    long[] executeTimes(int times, Runnable runnable) {
        long[] durations = new long[times];
        for (int i = 0; i < times; i++) {
            long start = System.nanoTime();
            runnable.run();
            long stop = System.nanoTime();
            durations[i] = stop - start;
        }
        return durations;
    }

    long toMillis(long nanoseconds) {
        return TimeUnit.NANOSECONDS.toMillis(nanoseconds);
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @IdentifyUsing("#text")
        TextField text();

        @Cached
        @IdentifyUsing("#text")
        TextField cachedText();

        @IdentifyUsing(value = "texts", how = IdStartsWith.class)
        List<TextField> texts();

        @Cached
        @IdentifyUsing(value = "texts", how = IdStartsWith.class)
        List<TextField> cachedTexts();

    }

}
