package info.novatec.testit.webtester.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.function.Supplier;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(Enclosed.class)
public class ConfiguredWaitTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractConfiguredWaitTest {

        @Mock
        Waiter waiter;
        @Mock
        WaitConfig config;
        @InjectMocks
        ConfiguredWait cut;

    }

    public static class FluentUntil extends AbstractConfiguredWaitTest {

        @Mock
        Object object;

        @Test
        public void waitUntilIsCreatedWithSameWaiter() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getWaiter()).isSameAs(waiter);
        }

        @Test
        public void waitUntilIsCreatedWithSameConfiguration() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getConfig()).isSameAs(config);
        }

        @Test
        public void waitUntilIsCreatedForObject() {
            WaitUntil<Object> waitUntil = cut.until(object);
            assertThat(waitUntil.getObject()).isSameAs(object);
        }

    }

    public static class Until extends AbstractConfiguredWaitTest {

        @Test
        public void usesWaiterToWaitForSupplierToReturnTrue() {
            Supplier<Boolean> supplier = () -> true;
            cut.until(supplier);
            verify(waiter).waitUntil(config, supplier);
        }

    }

}
