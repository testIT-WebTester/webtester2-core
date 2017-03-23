package info.novatec.testit.webtester.spring4.config.adapters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.testit.testutils.mockito.junit5.EnableMocking;

import com.google.common.collect.Sets;

import info.novatec.testit.webtester.config.Configuration;


@EnableMocking
class SpringEnvironmentConfigurationAdapterTest {

    @Mock
    Configuration configuration;
    @Mock
    Environment environment;

    @InjectMocks
    SpringEnvironmentConfigurationAdapter cut;

    @Test
    void testThatAdaptationIsNotExecutedIfEnvironmentIsNotSet() {
        cut.setEnvironment(null);
        assertThat(cut.adapt(configuration), is(false));
    }

    @Test
    void testThatOnlyKeysWhichCouldBeResolvedAgainstTheEnvironmentAreAdapted() {

        doReturn(Sets.newHashSet("foo", "bar")).when(configuration).getKeys();
        doReturn("fooValue").when(environment).getProperty("foo");

        cut.adapt(configuration);

        verify(configuration).getKeys();
        verify(configuration).setProperty("foo", "fooValue");
        verifyNoMoreInteractions(configuration);

    }

    @Test
    void testThatAllKeysWhichCouldBeResolvedAgainstTheEnvironmentAreAdapted() {

        doReturn(Sets.newHashSet("foo", "bar")).when(configuration).getKeys();
        doReturn("fooValue").when(environment).getProperty("foo");
        doReturn("barValue").when(environment).getProperty("bar");

        cut.adapt(configuration);

        verify(configuration).getKeys();
        verify(configuration).setProperty("foo", "fooValue");
        verify(configuration).setProperty("bar", "barValue");
        verifyNoMoreInteractions(configuration);

    }

}
