package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class IntegerConfigurationValueGetterTest {

    IntegerConfigurationValueGetter cut = new IntegerConfigurationValueGetter();

    @Test
    @DisplayName("can handle Integer type")
    void canHandleIntegerType() {
        boolean result = cut.canHandle(Integer.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as Integer")
    void canGetValueAsInteger() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of(1)).when(config).getIntegerProperty("key");

        Optional<Integer> result = cut.get(config, "key");
        assertThat(result).hasValue(1);

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getIntegerProperty("key");

        Optional<Integer> result = cut.get(config, "key");
        assertThat(result).isEmpty();

    }

}
