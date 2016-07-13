package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class FloatConfigurationValueGetterTest {

    FloatConfigurationValueGetter cut = new FloatConfigurationValueGetter();

    @Test
    @DisplayName("can handle Float type")
    void canHandleFloatType() {
        boolean result = cut.canHandle(Float.class);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("can't handle Object type")
    void cantHandleObjectType() {
        boolean result = cut.canHandle(Object.class);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("value is returned as Float")
    void canGetValueAsFloat() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.of(1.5f)).when(config).getFloatProperty("key");

        Optional<Float> result = cut.get(config, "key");
        assertThat(result).hasValue(1.5f);

    }

    @Test
    @DisplayName("if no value is present an empty Optional is returned")
    void ifNotFoundEmptyOptionalIsReturned() {

        Configuration config = mock(Configuration.class);
        doReturn(Optional.empty()).when(config).getFloatProperty("key");

        Optional<Float> result = cut.get(config, "key");
        assertThat(result).isEmpty();

    }

}
