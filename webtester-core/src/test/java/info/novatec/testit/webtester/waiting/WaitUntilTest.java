package info.novatec.testit.webtester.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(Enclosed.class)
public class WaitUntilTest {

    @RunWith(MockitoJUnitRunner.class)
    public static abstract class AbstractWaitUntilTest {

        @Captor
        ArgumentCaptor<Supplier<Boolean>> supplierCaptor;

        @Mock
        Waiter waiter;
        @Mock
        WaitConfig config;
        @Mock
        Object object;

        WaitUntil<Object> cut;

        @Before
        public void init() {
            cut = new WaitUntil<>(waiter, config, object);
        }

    }

    public static class Is extends AbstractWaitUntilTest {

        @Test
        public void conditionsAreTransformedToSupplier() {

            cut.is(object -> true);

            verify(waiter).waitUntil(same(config), supplierCaptor.capture());
            Supplier<Boolean> supplier = supplierCaptor.getValue();
            assertThat(supplier.get()).isTrue();

        }

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.is(object -> true);
            assertThat(actual).isSameAs(cut);
        }

    }

    public static class Has extends AbstractWaitUntilTest {

        @Test
        public void conditionsAreTransformedToSupplier() {

            cut.has(object -> true);

            verify(waiter).waitUntil(same(config), supplierCaptor.capture());
            Supplier<Boolean> supplier = supplierCaptor.getValue();
            assertThat(supplier.get()).isTrue();

        }

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.has(object -> true);
            assertThat(actual).isSameAs(cut);
        }

    }

    public static class IsNot extends AbstractWaitUntilTest {

        @Test
        public void conditionsAreNegatedAndTransformedToSupplier() {

            cut.isNot(object -> false);

            verify(waiter).waitUntil(same(config), supplierCaptor.capture());
            Supplier<Boolean> supplier = supplierCaptor.getValue();
            assertThat(supplier.get()).isTrue();

        }

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.isNot(object -> false);
            assertThat(actual).isSameAs(cut);
        }

    }

    public static class HasNot extends AbstractWaitUntilTest {

        @Test
        public void conditionsAreNegatedAndTransformedToSupplier() {

            cut.hasNot(object -> false);

            verify(waiter).waitUntil(same(config), supplierCaptor.capture());
            Supplier<Boolean> supplier = supplierCaptor.getValue();
            assertThat(supplier.get()).isTrue();

        }

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.hasNot(object -> false);
            assertThat(actual).isSameAs(cut);
        }

    }

    public static class Not extends AbstractWaitUntilTest {

        @Test
        public void conditionsAreNegatedAndTransformedToSupplier() {

            cut.not(object -> false);

            verify(waiter).waitUntil(same(config), supplierCaptor.capture());
            Supplier<Boolean> supplier = supplierCaptor.getValue();
            assertThat(supplier.get()).isTrue();

        }

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.not(object -> false);
            assertThat(actual).isSameAs(cut);
        }

    }

    public static class And extends AbstractWaitUntilTest {

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.and();
            assertThat(actual).isSameAs(cut);
        }

        @Test
        public void doesNotChangeState() {
            cut.and();
            verifyZeroInteractions(waiter, config, object);
        }

    }

    public static class But extends AbstractWaitUntilTest {

        @Test
        public void returnsSameInstance() {
            WaitUntil actual = cut.but();
            assertThat(actual).isSameAs(cut);
        }

        @Test
        public void doesNotChangeState() {
            cut.but();
            verifyZeroInteractions(waiter, config, object);
        }

    }

}
