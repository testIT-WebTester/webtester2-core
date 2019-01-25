package info.novatec.testit.webtester.waiting;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testit.testutils.mockito.junit5.EnableMocking;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@EnableMocking
class ConfiguredWaitTest {

    @Mock
    Waiter waiter;
    @Mock
    WaitConfig config;
    @InjectMocks
    ConfiguredWait cut;

    @Nested
    class FluentUntil {

        @Mock
        Object object;

        @Test
        void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getWaiter()).isSameAs(waiter);
        }

        @Test
        void waitUntilIsCreatedWithSameConfiguration() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getConfig()).isSameAs(config);
        }

        @Test
        void waitUntilIsCreatedForObject() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getObjectSupplier().get()).isSameAs(object);
        }

    }

    @Nested
    class FluentUntilSupplied {

        @Mock
        Object object;

        @Test
        void waitUntilIsCreatedWithSameWaiter() {
            Supplier<Object> supplier = () -> object;
            WaitUntil<Object> waitUntil = cut.untilSupplied(supplier);
            assertThat(waitUntil.getWaiter()).isSameAs(waiter);
        }

        @Test
        void waitUntilIsCreatedWithSameConfiguration() {
            Supplier<Object> supplier = () -> object;
            WaitUntil<Object> waitUntil = cut.untilSupplied(supplier);
            assertThat(waitUntil.getConfig()).isSameAs(config);
        }

        @Test
        void waitUntilIsCreatedForObject() {
            Supplier<Object> supplier = () -> object;
            WaitUntil<Object> waitUntil = cut.untilSupplied(supplier);
            assertThat(waitUntil.getObjectSupplier().get()).isSameAs(object);
        }

    }

    @Nested
    class Until {

        @Test
        void usesWaiterToWaitForSupplierToReturnTrue() {
            Supplier<Boolean> supplier = () -> true;
            cut.until(supplier);
            verify(waiter).waitUntil(config, supplier);
        }

    }

    @Nested
    class UntilWithAction {

        @Test
        void usesWaiterToWaitForSupplierToReturnTrue() {
            Supplier<Boolean> supplier = () -> true;
            WaitingAction waitingAction = WaitingAction.doNothing();
            cut.untilWithAction(supplier, waitingAction);
            verify(waiter).waitUntilWithAction(config, supplier, waitingAction);
        }
    }


}
