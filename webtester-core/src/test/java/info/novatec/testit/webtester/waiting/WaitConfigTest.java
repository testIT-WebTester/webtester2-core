package info.novatec.testit.webtester.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class WaitConfigTest {

    public static class Construction {

        @Test
        public void defaultsAreSet() {
            WaitConfig config = new WaitConfig();
            assertThat(config.getTimeout()).isEqualTo(2);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(100L);
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class GetTimeoutInMillis {

        @Test
        public void secondsCanBeReturnedAsMilliseconds() {

            WaitConfig config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);

            long result = config.getTimeoutInMillis();
            assertThat(result).isEqualTo(1000L);

        }

        @Test
        public void littleLessThanOneMillisecondIsReturnedAsZero() {

            WaitConfig config = new WaitConfig();
            config.setTimeout(999);
            config.setTimeUnit(TimeUnit.MICROSECONDS);

            long result = config.getTimeoutInMillis();
            assertThat(result).isEqualTo(0L);

        }

        @Test
        public void littleMoreThanOneMillisecondIsReturnedAsOne() {

            WaitConfig config = new WaitConfig();
            config.setTimeout(1001);
            config.setTimeUnit(TimeUnit.MICROSECONDS);

            long result = config.getTimeoutInMillis();
            assertThat(result).isEqualTo(1L);

        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class FactoryMethods {

        @Mock
        Configuration configuration;
        @Mock
        Browser browser;
        @Mock
        PageFragment pageFragment;

        @Test
        public void canBeCreatedFromConfiguration() {

            doReturn(1).when(configuration).getWaitTimeout();
            doReturn(10L).when(configuration).getWaitInterval();

            WaitConfig config = WaitConfig.from(configuration);

            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(10L);

        }

        @Test
        public void canBeCreatedFromBrowser() {

            doReturn(configuration).when(browser).configuration();
            doReturn(2).when(configuration).getWaitTimeout();
            doReturn(20L).when(configuration).getWaitInterval();

            WaitConfig config = WaitConfig.from(browser);

            assertThat(config.getTimeout()).isEqualTo(2);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(20L);

        }

        @Test
        public void canBeCreatedFromPageFragment() {

            doReturn(browser).when(pageFragment).browser();
            doReturn(configuration).when(browser).configuration();
            doReturn(3).when(configuration).getWaitTimeout();
            doReturn(30L).when(configuration).getWaitInterval();

            WaitConfig config = WaitConfig.from(pageFragment);

            assertThat(config.getTimeout()).isEqualTo(3);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(30L);

        }

    }

}
