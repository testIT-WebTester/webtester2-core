package info.novatec.testit.webtester.waiting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class CurrentThreadSleeperTest {

    CurrentThreadSleeper cut = new CurrentThreadSleeper();

    @Test
    public void threadIsPutToSleepForGivenAmountOfTime() {
        long start = now();
        cut.sleep(100L);
        long stop = now();
        assertThat(stop - start).isGreaterThan(85).isLessThan(115);
    }

    private long now() {
        return System.currentTimeMillis();
    }

}
