package info.novatec.testit.webtester.waiting;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
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
public class WaitOperationsTest {

    @Mock
    Supplier<Boolean> supplier;

    @Test
    public void conditionIsCheckedAtLeastOnceEvenWithoutTimeout() {
        doReturn(true).when(supplier).get();
        WaitOperations.waitUntil(0, TimeUnit.MILLISECONDS, 100, supplier);
        verify(supplier, times(1)).get();
    }

    @Test(expected = TimeoutException.class)
    public void conditionIsCheckedAtMostTimeoutDividedByIntervalTimes() {
        doReturn(false).when(supplier).get();
        try {
            WaitOperations.waitUntil(50, TimeUnit.MILLISECONDS, 25, supplier);
        } finally {
            verify(supplier, atMost(2)).get();
        }
    }

    @Test
    public void conditionIsCheckedUntilItMatches() {
        when(supplier.get()).thenReturn(false, false, true);
        WaitOperations.waitUntil(100, TimeUnit.MILLISECONDS, 5, supplier);
        verify(supplier, times(3)).get();
    }

    @Test
    public void timeoutExceptionHasOriginalExceptionAsItsCause() {
        Throwable expected = new IllegalStateException();
        try {
            when(supplier.get()).thenThrow(expected);
            WaitOperations.waitUntil(0, TimeUnit.MILLISECONDS, 0, supplier);
            Assert.fail("exception not reached");
        } catch (TimeoutException e) {
            Assertions.assertThat(e.getCause()).isSameAs(expected);
        }
    }


}
