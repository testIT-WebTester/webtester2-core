package info.novatec.testit.webtester.waiting;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testit.testutils.mockito.junit5.EnableMocking;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@EnableMocking
class WaitTest {

    @Mock
    Waiter waiter;

    @BeforeEach
    void rememberAndReplaceOriginalWaiter() {
        Wait.setWaiter(() -> waiter);
    }

    @AfterEach
    void restoreOriginalWaiter() {
        Wait.setWaiter(Wait.DEFAULT_WAITER);
    }

    @Nested
    class WithTimeoutOf_Timeout {

        @Test
        void configuredWaitIsCreatedWithSameWaiter() {
            ConfiguredWait wait = Wait.withTimeoutOf(1);
            assertThat(wait.getWaiter()).isSameAs(waiter);
        }

        @Test
        void configuredWaitIsCreatedWithCorrectConfiguration() {
            ConfiguredWait wait = Wait.withTimeoutOf(1);
            WaitConfig config = wait.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(WaitConfig.DEFAULT_TIME_UNIT);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

    }

    @Nested
    class WithTimeoutOf_TimeoutAndUnit {

        @Test
        void configuredWaitIsCreatedWithSameWaiter() {
            ConfiguredWait wait = Wait.withTimeoutOf(1, TimeUnit.MINUTES);
            assertThat(wait.getWaiter()).isSameAs(waiter);
        }

        @Test
        void configuredWaitIsCreatedWithCorrectConfiguration() {
            ConfiguredWait wait = Wait.withTimeoutOf(1, TimeUnit.MINUTES);
            WaitConfig config = wait.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.MINUTES);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

    }

    @Nested
    class Until_Object {

        @Mock
        Object object;

        @Test
        void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<Object> until = Wait.until(object);
            assertThat(until.getWaiter()).isSameAs(waiter);
        }

        @Test
        void waitUntilIsCreatedWithDefaultConfiguration() {
            WaitUntil<Object> until = Wait.until(object);
            WaitConfig config = until.getConfig();
            assertThat(config.getTimeout()).isEqualTo(WaitConfig.DEFAULT_TIMEOUT);
            assertThat(config.getTimeUnit()).isEqualTo(WaitConfig.DEFAULT_TIME_UNIT);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

        @Test
        void waitUntilIsCreatedForObject() {
            WaitUntil<Object> until = Wait.until(object);
            assertThat(until.getObjectSupplier().get()).isSameAs(object);
        }

    }

    @Nested
    class Until_ObjectSupplier {

        @Mock
        Object object;

        @Test
        void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<Object> until = Wait.untilSupplied(() -> object);
            assertThat(until.getWaiter()).isSameAs(waiter);
        }

        @Test
        void waitUntilIsCreatedWithDefaultConfiguration() {
            WaitUntil<Object> until = Wait.untilSupplied(() -> object);
            WaitConfig config = until.getConfig();
            assertThat(config.getTimeout()).isEqualTo(WaitConfig.DEFAULT_TIMEOUT);
            assertThat(config.getTimeUnit()).isEqualTo(WaitConfig.DEFAULT_TIME_UNIT);
            assertThat(config.getInterval()).isEqualTo(WaitConfig.DEFAULT_INTERVAL);
        }

        @Test
        void waitUntilIsCreatedForObject() {
            WaitUntil<Object> until = Wait.untilSupplied(() -> object);
            assertThat(until.getObjectSupplier().get()).isSameAs(object);
        }

    }

    @Nested
    class Until_BooleanSupplier {

        @Test
        void delegatesToWaiter() {
            Supplier<Boolean> supplier = () -> true;
            Wait.until(supplier);
            verify(waiter).waitUntil(any(WaitConfig.class), eq(supplier));
        }

    }

    @Nested
    class Until_WithAction {

        @Test
        void delegateToWaiter() {
            Supplier<Boolean> supplier = () -> true;
            WaitingAction waitingAction = new WaitingAction(() -> false, null);
            Wait.untilWithAction(supplier, waitingAction);
            verify(waiter).waitUntilWithAction(any(WaitConfig.class), eq(supplier), eq(waitingAction));
        }
    }


    @Nested
    class Until_PageFragment {

        @Mock
        Configuration configuration;
        @Mock
        Browser browser;
        @Mock
        PageFragment fragment;

        @BeforeEach
        void setUpFragment() {
            doReturn(configuration).when(browser).configuration();
            doReturn(browser).when(fragment).browser();
            doReturn(1).when(configuration).getWaitTimeout();
            doReturn(50L).when(configuration).getWaitInterval();
        }

        @Test
        void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            assertThat(until.getWaiter()).isSameAs(waiter);
        }

        @Test
        void waitUntilIsCreatedWithFragmentsConfiguration() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            WaitConfig config = until.getConfig();
            assertThat(config.getTimeout()).isEqualTo(1);
            assertThat(config.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
            assertThat(config.getInterval()).isEqualTo(50L);
        }

        @Test
        void waitUntilIsCreatedForPageFragment() {
            WaitUntil<PageFragment> until = Wait.until(fragment);
            assertThat(until.getObjectSupplier().get()).isSameAs(fragment);
        }

    }

    @Nested
    class Exactly {

        @Test
        void isDirectlyDelegatedToWaiter() {
            Wait.exactly(10, TimeUnit.SECONDS);
            verify(waiter).waitExactly(10, TimeUnit.SECONDS);
        }

    }

}
