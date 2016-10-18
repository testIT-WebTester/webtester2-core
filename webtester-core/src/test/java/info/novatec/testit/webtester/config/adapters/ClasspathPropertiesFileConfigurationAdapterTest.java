package info.novatec.testit.webtester.config.adapters;

import static info.novatec.testit.webtester.config.adapters.ClasspathPropertiesFileConfigurationAdapter.Importance.REQUIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

import info.novatec.testit.webtester.config.Configuration;


public class ClasspathPropertiesFileConfigurationAdapterTest {

    static final String PATH = "configurations/classpath-properties-file.properties";
    static final String UNKNOWN_PATH = "unknown.properties";

    ClasspathPropertiesFileConfigurationAdapter cut;

    @Test
    public void existingFileIsUsedToAdaptConfiguration() {

        Configuration configuration = mock(Configuration.class);

        cut = new ClasspathPropertiesFileConfigurationAdapter(PATH);
        boolean adapt = cut.adapt(configuration);
        assertThat(adapt).isTrue();

    }

    @Test
    public void unknownFileIsNotUsedToAdaptConfiguration() {

        Configuration configuration = mock(Configuration.class);

        cut = new ClasspathPropertiesFileConfigurationAdapter(UNKNOWN_PATH);
        boolean adapt = cut.adapt(configuration);
        assertThat(adapt).isFalse();

    }

    @Test
    public void propertiesFromFileAreSetOnConfiguration() {

        Configuration configuration = mock(Configuration.class);

        cut = new ClasspathPropertiesFileConfigurationAdapter(PATH);
        cut.adapt(configuration);

        verify(configuration).setProperty("property.key1", "foo");
        verify(configuration).setProperty("property.key2", "bar");
        verifyNoMoreInteractions(configuration);

    }

    @Test(expected = IllegalStateException.class)
    public void unknownRequiredFileThrowsException() {
        cut = new ClasspathPropertiesFileConfigurationAdapter(UNKNOWN_PATH, REQUIRED);
        cut.adapt(mock(Configuration.class));
    }

}
