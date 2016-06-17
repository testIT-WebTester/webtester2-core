package info.novatec.testit.webtester.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;


public class AbstractEventTest {

    @Test
    public void creatingNewEventSetsTimestampToNow() {

        AbstractEvent event = new TestEvent();

        long actualEpoch = event.getTimestamp().toEpochSecond(ZoneOffset.UTC);
        long nowEpoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        assertThat(actualEpoch).isGreaterThan(nowEpoch - 1000).isLessThanOrEqualTo(nowEpoch);

    }

    public static class TestEvent extends AbstractEvent {

        @Override
        public String describe() {
            return StringUtils.EMPTY;
        }

    }

}
