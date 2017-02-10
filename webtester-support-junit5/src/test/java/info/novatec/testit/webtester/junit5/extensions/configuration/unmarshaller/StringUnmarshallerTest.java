package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


class StringUnmarshallerTest {

    StringUnmarshaller cut = new StringUnmarshaller();

    @Test
    @DisplayName("can handle String type")
    void canHandleStringType() {
        boolean result = cut.canHandle(String.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as String")
    void canGetValueAsString() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of("string")).when(config).getStringProperty("key");

        Optional<String> result = cut.unmarshal(config, "key");
        assertThat(result).hasValue("string");

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getStringProperty("key");

        Optional<String> result = cut.unmarshal(config, "key");
        assertThat(result).isEmpty();

    }

}
