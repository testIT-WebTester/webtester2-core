package info.novatec.testit.webtester.waiting;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class WaitsTest {

    @Mock
    Supplier<Boolean> supplier;

    @Test
    public void testGenericWaitUntil_GoodCase() {
        when(supplier.get()).thenReturn(false, false, true);
        WaitOperations.waitUntil(100, TimeUnit.MILLISECONDS, 20, supplier);
        verify(supplier, times(3)).get();
    }

    @Test(expected = TimeoutException.class)
    public void testGenericWaitUntil_BadCase() {
        try {
            when(supplier.get()).thenReturn(false);
            WaitOperations.waitUntil(50, TimeUnit.MILLISECONDS, 25, supplier);
        } finally {
            verify(supplier, atMost(2)).get();
        }
    }

    @Test
    public void testGenericWaitUntil_OriginalExceptionAsCauseOfTimeout() {
        Throwable expected = new IllegalStateException();
        try {
            when(supplier.get()).thenThrow(expected);
            WaitOperations.waitUntil(50, TimeUnit.MILLISECONDS, 25, supplier);
            Assert.fail("exception not reached");
        } catch (TimeoutException e) {
            Assertions.assertThat(e.getCause()).isSameAs(expected);
        }
    }

}
