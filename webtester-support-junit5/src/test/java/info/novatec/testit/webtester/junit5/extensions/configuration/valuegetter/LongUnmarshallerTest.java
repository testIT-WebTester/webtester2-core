package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class LongUnmarshallerTest {

    LongUnmarshaller cut = new LongUnmarshaller();

    @Test
    @DisplayName("can handle Long type")
    void canHandleLongType() {
        boolean result = cut.canHandle(Long.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as Long")
    void canGetValueAsLong() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of(1L)).when(config).getLongProperty("key");

        Optional<Long> result = cut.unmarshal(config, "key");
        assertThat(result).hasValue(1L);

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getLongProperty("key");

        Optional<Long> result = cut.unmarshal(config, "key");
        assertThat(result).isEmpty();

    }

}
