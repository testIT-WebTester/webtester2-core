package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class DoubleConfigurationValueGetterTest {

    DoubleConfigurationValueGetter cut = new DoubleConfigurationValueGetter();

    @Test
    @DisplayName("can handle Double type")
    void canHandleDoubleType() {
        boolean result = cut.canHandle(Double.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as Double")
    void canGetValueAsDouble() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of(1.5d)).when(config).getDoubleProperty("key");

        Optional<Double> result = cut.get(config, "key");
        assertThat(result).hasValue(1.5d);

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getDoubleProperty("key");

        Optional<Double> result = cut.get(config, "key");
        assertThat(result).isEmpty();

    }

}
