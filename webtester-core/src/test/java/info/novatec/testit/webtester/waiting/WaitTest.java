package info.novatec.testit.webtester.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class WaitTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractWaitTest {

        @Mock
        Waiter waiter;
        Waiter originalWaiter;

        @Before
        public void rememberAndReplaceOriginalWaiter() throws InterruptedException {
            originalWaiter = Wait.waiter;
            Wait.waiter = waiter;
        }

        @After
        public void restoreOriginalWaiter() {
            Wait.waiter = originalWaiter;
        }

    }

    public static class WithTimeoutOf_Timeout extends AbstractWaitTest {

        @Test
        public void configuredWaitIsCreatedWithSameWaiter() {
            ConfiguredWait wait = Wait.withTimeoutOf(1);
            assertThat(wait.getWaiter()).isSameAs(waiter);
        }

        @Test
        public void configuredWaitIsCreatedWithCorrectConfiguration() {
            ConfiguredWait wait = Wait.withTimeoutOf(1);
            WaitConfig config = wait.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(WaitConfig.DEFAULT_TIME_UNIT);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

    }

    public static class WithTimeoutOf_TimeoutAndUnit extends AbstractWaitTest {

        @Test
        public void configuredWaitIsCreatedWithSameWaiter() {
            ConfiguredWait wait = Wait.withTimeoutOf(1, TimeUnit.MINUTES);
            assertThat(wait.getWaiter()).isSameAs(waiter);
        }

        @Test
        public void configuredWaitIsCreatedWithCorrectConfiguration() {
            ConfiguredWait wait = Wait.withTimeoutOf(1, TimeUnit.MINUTES);
            WaitConfig config = wait.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.MINUTES);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

    }

    public static class Until_Object extends AbstractWaitTest {

        @Mock
        Object object;

        @Test
        public void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<Object> until = Wait.until(object);
            assertThat(until.getWaiter()).isSameAs(waiter);
        }

        @Test
        public void waitUntilIsCreatedWithDefaultConfiguration() {
            WaitUntil<Object> until = Wait.until(object);
            WaitConfig config = until.getConfig();
            assertThat(config.getTimeout()).isEqualTo(WaitConfig.DEFAULT_TIMEOUT);
            assertThat(config.getTimeUnit()).isEqualTo(WaitConfig.DEFAULT_TIME_UNIT);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

        @Test
        public void waitUntilIsCreatedForObject() {
            WaitUntil<Object> until = Wait.until(object);
            assertThat(until.getObject()).isSameAs(object);
        }

    }

    public static class Until_PageFragment extends AbstractWaitTest {

        @Mock
        Configuration configuration;
        @Mock
        Browser browser;
        @Mock
        PageFragment fragment;

        @Before
        public void setUpFragment() {
            doReturn(configuration).when(browser).configuration();
            doReturn(browser).when(fragment).getBrowser();
            doReturn(1).when(configuration).getWaitTimeout();
            doReturn(50L).when(configuration).getWaitInterval();
        }

        @Test
        public void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            assertThat(until.getWaiter()).isSameAs(waiter);
        }

        @Test
        public void waitUntilIsCreatedWithFragmentsConfiguration() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            WaitConfig config = until.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(50L);
        }

        @Test
        public void waitUntilIsCreatedForPageFragment() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            assertThat(until.getObject()).isSameAs(fragment);
        }

    }

    public static class Exactly extends AbstractWaitTest {

        @Test
        public void isDirectlyDelegatedToWaiter() {
            Wait.exactly(10, TimeUnit.SECONDS);
            verify(waiter).waitExactly(10, TimeUnit.SECONDS);
        }

    }

}
