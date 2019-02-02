package info.novatec.testit.webtester.waiting;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Clock;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(Enclosed.class)
public class DefaultWaiterTest {

    public static class Construction {

        @Test
        public void defaultWaiterCanBeInitialized() {
            DefaultWaiter waiter = new DefaultWaiter();
            assertThat(waiter.getSleeper()).isInstanceOf(CurrentThreadSleeper.class);
            assertThat(waiter.getClock()).isNotNull();
        }

        @Test
        public void sleeperCanBeCustomized() {
            Sleeper sleeper = mock(Sleeper.class);
            DefaultWaiter waiter = new DefaultWaiter(sleeper);
            assertThat(waiter.getSleeper()).isSameAs(sleeper);
            assertThat(waiter.getClock()).isNotNull();
        }

        @Test
        public void sleeperAndClockCanBeCustomized() {
            Sleeper sleeper = mock(Sleeper.class);
            Clock clock = mock(Clock.class);
            DefaultWaiter waiter = new DefaultWaiter(sleeper, clock);
            assertThat(waiter.getSleeper()).isSameAs(sleeper);
            assertThat(waiter.getClock()).isSameAs(clock);
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class WaitExactly {

        @Mock
        Clock clock;
        @Mock
        Sleeper sleeper;
        @InjectMocks
        DefaultWaiter cut;

        @Test
        public void waitingOneSecondsSleepsForOneThousandMilliseconds() {
            cut.waitExactly(1, TimeUnit.SECONDS);
            verify(sleeper).sleep(1000L);
        }

        @Test
        public void waitingOneMillisecondSleepsForOneMilliseconds() {
            cut.waitExactly(1, TimeUnit.MILLISECONDS);
            verify(sleeper).sleep(1L);
        }

        @Test
        public void waitingLittleLessThanOneMillisecondDoesNotSleep() {
            cut.waitExactly(999, TimeUnit.MICROSECONDS);
            verifyZeroInteractions(sleeper);
        }

        @Test
        public void waitingLittleMoreThanOneMillisecondDoesSleepForOne() {
            cut.waitExactly(1001, TimeUnit.MICROSECONDS);
            verify(sleeper).sleep(1L);
        }

        @Test
        public void interruptedExceptionAreNotPropagated() {
            InterruptedException cause = new InterruptedException();
            InterruptionException exception = new InterruptionException(cause);
            doThrow(exception).when(sleeper).sleep(anyLong());
            cut.waitExactly(1, TimeUnit.MILLISECONDS);
            verify(sleeper).sleep(1L);
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class WaitUntil {

        @Mock
        Sleeper sleeper;
        @Mock
        Clock clock;
        @InjectMocks
        DefaultWaiter cut;

        @Mock
        Supplier<Boolean> condition;

        long currentTime = 42L;

        @Before
        public void stubClockToMoveTimeEveryTimeTheSleeperIsInvoked() {
            doReturn(currentTime).when(clock).millis();
            doAnswer((invocation) -> {
                currentTime += (Long) invocation.getArguments()[0];
                doReturn(currentTime).when(clock).millis();
                return null;
            }).when(sleeper).sleep(anyLong());
        }

        @Test(expected = TimeoutException.class)
        public void timeoutExceptionIsThrowIfConditionIsNeverMet() {

            doReturn(false).when(condition).get();

            WaitConfig config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);
            config.setInterval(100);

            try {
                cut.waitUntil(config, condition);
            } finally {
                verify(condition, times(10)).get();
                verify(sleeper, times(10)).sleep(100);
            }

        }

        @Test
        public void whenConditionIsMetInstantlyNoSleepIsExecuted() {

            doReturn(true).when(condition).get();

            WaitConfig config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);
            config.setInterval(100);

            try {
                cut.waitUntil(config, condition);
            } finally {
                verify(condition, times(1)).get();
                verifyZeroInteractions(sleeper);
            }

        }

        @Test(expected = TimeoutException.class)
        public void whenConditionIsNeverMetTheTimeoutHasNoCause() {

            doReturn(false).when(condition).get();

            WaitConfig config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);
            config.setInterval(100);

            try {
                cut.waitUntil(config, condition);
            } catch (TimeoutException e) {
                assertThat(e).hasNoCause();
                throw e;
            }

        }

        @Test(expected = TimeoutException.class)
        public void whenConditionIsNotMetBecauseOfExceptionTheTimeoutHasACause() {

            RuntimeException exception = mock(RuntimeException.class);
            doThrow(exception).when(condition).get();

            WaitConfig config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);
            config.setInterval(100);

            try {
                cut.waitUntil(config, condition);
            } catch (TimeoutException e) {
                assertThat(e.getCause()).isSameAs(exception);
                throw e;
            }

        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class WaitUntilWithAction {
        @Mock
        Sleeper sleeper;
        @Mock
        Clock clock;
        @InjectMocks
        DefaultWaiter cut;

        @Mock
        Supplier<Boolean> condition;
        @Mock
        Supplier<Boolean> actionCondition;
        @Mock
        WaitAction action;

        WaitConfig config;
        WaitingAction waitingAction;

        long currentTime = 42L;

        @Before
        public void setup() {
            config = new WaitConfig();
            config.setTimeout(1);
            config.setTimeUnit(TimeUnit.SECONDS);
            config.setInterval(100);

            doReturn(currentTime).when(clock).millis();
            doAnswer((invocation) -> {
                currentTime += (Long) invocation.getArguments()[0];
                doReturn(currentTime).when(clock).millis();
                return null;
            }).when(sleeper).sleep(anyLong());
        }

        @Test
        public void whenConditionIsMetInstantlyActionIsNotExecuted() {
            doReturn(true).when(condition).get();
            waitingAction = new WaitingAction(actionCondition, action);

            cut.waitUntilWithAction(config, condition, waitingAction);
            verify(action, never()).perform();
        }

        @Test(expected = TimeoutException.class)
        public void whenActionConditionIsNotMetBecauseOfExceptionTheTimeoutHasACause() {
            doReturn(false).when(condition).get();
            RuntimeException exception = mock(RuntimeException.class);
            doThrow(exception).when(actionCondition).get();
            waitingAction = new WaitingAction(actionCondition, action);

            try {
                cut.waitUntilWithAction(config, condition, waitingAction);
            } catch (TimeoutException e) {
                assertThat(e.getCause()).isSameAs(exception);
                throw e;
            }
        }

        @Test(expected = TimeoutException.class)
        public void whenConditionIsNotMetAndWaitActionFailsBecauseOfExceptionTheTimeoutHasACause() {
            doReturn(false).when(condition).get();
            doReturn(true).when(actionCondition).get();
            RuntimeException exception = mock(RuntimeException.class);
            doThrow(exception).when(action).perform();
            waitingAction = new WaitingAction(actionCondition, action);

            try {
                cut.waitUntilWithAction(config, condition, waitingAction);
            } catch (TimeoutException e) {
                assertThat(e.getCause()).hasCause(exception)
                        .isInstanceOf(WaitActionFailedException.class);
                throw e;
            }
        }

        @Test(expected = TimeoutException.class)
        public void wrapsWaitingActionFailsIntoWaitConditionExceptionWhenBothThrowExceptions() {
            RuntimeException conditionException = spy(new RuntimeException("conditionspy"));
            RuntimeException performException = mock(RuntimeException.class);
            when(condition.get())
                    .thenThrow(conditionException)
                    .thenReturn(false);
            when(actionCondition.get())
                    .thenReturn(false)
                    .thenReturn(true)
                    .thenReturn(false);
            doThrow(performException).when(action).perform();
            waitingAction = new WaitingAction(actionCondition, action);

            try {
                cut.waitUntilWithAction(config, condition, waitingAction);
            } catch (TimeoutException e) {
                assertThat(e.getCause()).isSameAs(conditionException);
                assertThat(e.getCause().getSuppressed()[0]).hasCause(performException)
                        .isInstanceOf(WaitActionFailedException.class);
                throw e;
            }
        }

        @Test(expected = TimeoutException.class)
        public void executesAlwaysWhenConditionIsNotMetButActionConditionIsMet() {
            doReturn(false).when(condition).get();
            doReturn(true).when(actionCondition).get();
            waitingAction = new WaitingAction(actionCondition, action);

            cut.waitUntilWithAction(config, condition, waitingAction);
            verify(action, times(10)).perform();
        }

        @Test
        public void runsWaitActionWhenConditionThrowsException() {
            when(condition.get())
                    .thenThrow(RuntimeException.class)
                    .thenReturn(true);
            when(actionCondition.get()).thenReturn(true);
            waitingAction = new WaitingAction(actionCondition, action);

            cut.waitUntilWithAction(config, condition, waitingAction);
            verify(action, times(1)).perform();
        }
    }
}
