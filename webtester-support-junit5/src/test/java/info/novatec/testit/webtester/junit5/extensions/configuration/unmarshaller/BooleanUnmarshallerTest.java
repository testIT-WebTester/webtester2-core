package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class BooleanUnmarshallerTest {

    BooleanUnmarshaller cut = new BooleanUnmarshaller();

    @Test
    @DisplayName("can handle Boolean type")
    void canHandleBooleanType() {
        boolean result = cut.canHandle(Boolean.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as Boolean")
    void canGetValueAsBoolean() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of(Boolean.TRUE)).when(config).getBooleanProperty("key");

        Optional<Boolean> result = cut.unmarshal(config, "key");
        assertThat(result).hasValue(Boolean.TRUE);

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getBooleanProperty("key");

        Optional<Boolean> result = cut.unmarshal(config, "key");
        assertThat(result).isEmpty();

    }

}
