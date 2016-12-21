package info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.config.Configuration;


public class DefaultUnmarshallerTest {

    DefaultUnmarshaller cut = new DefaultUnmarshaller();

    @Test
    @DisplayName("can't handle any type")
    void cantHandleAnyType() {
        assertThat(cut.canHandle(null)).isFalse();
    }

    @Test
    @DisplayName("always returns empty optional")
    void alwaysReturnsEmptyOptional() {
        Configuration config = mock(Configuration.class);
        String key = "key";
        Optional<Object> value = cut.unmarshal(config, key);
        assertThat(value).isEmpty();
        verifyZeroInteractions(config);
    }

}
