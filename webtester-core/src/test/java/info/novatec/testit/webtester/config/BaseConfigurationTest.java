package info.novatec.testit.webtester.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;

import info.novatec.testit.webtester.config.exceptions.InvalidValueTypeException;
import info.novatec.testit.webtester.config.exceptions.SetNullValuesException;


@RunWith(Enclosed.class)
public class BaseConfigurationTest {

    public static class DefaultNamedProperties {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void defaultActionDecelerationIsReturned() {
            assertThat(cut.getActionDeceleration()).isEqualTo(0L);
        }

        @Test
        public void defaultEventSystemEnabledIsReturned() {
            assertThat(cut.isEventSystemEnabled()).isEqualTo(true);
        }

        @Test
        public void defaultDefaultEntryPointIsReturned() {
            assertThat(cut.getDefaultEntryPoint()).isEqualTo(Optional.empty());
        }

        @Test
        public void defaultScreenshotFolderIsReturned() {
            assertThat(cut.getScreenshotFolder()).isEqualTo(new File("screenshots"));
        }

        @Test
        public void defaultPageSourceFolderIsReturned() {
            assertThat(cut.getPageSourceFolder()).isEqualTo(new File("sourcecode"));
        }

        @Test
        public void defaultLogFolderIsReturned() {
            assertThat(cut.getLogFolder()).isEqualTo(new File("logs"));
        }

        @Test
        public void defaultMarkingsEnabledIsReturned() {
            assertThat(cut.isMarkingsEnabled()).isEqualTo(false);
        }

        @Test
        public void defaultMarkingsColorUsedBackgroundIsReturned() {
            assertThat(cut.getMarkingsColorUsedBackground()).isEqualTo(Color.fromString("#ffd2a5"));
        }

        @Test
        public void defaultMarkingsColorUsedOutlineIsReturned() {
            assertThat(cut.getMarkingsColorUsedOutline()).isEqualTo(Color.fromString("#916f22"));
        }

        @Test
        public void defaultMarkingsColorReadBackgroundIsReturned() {
            assertThat(cut.getMarkingsColorReadBackground()).isEqualTo(Color.fromString("#90ee90"));
        }

        @Test
        public void defaultMarkingsColorReadOutlineIsReturned() {
            assertThat(cut.getMarkingsColorReadOutline()).isEqualTo(Color.fromString("#008000"));
        }

        @Test
        public void defaultWaitTimeoutIsReturned() {
            assertThat(cut.getWaitTimeout()).isEqualTo(2);
        }

        @Test
        public void defaultWaitIntervalIsReturned() {
            assertThat(cut.getWaitInterval()).isEqualTo(100L);
        }

        @Test
        public void defaultRemoteBrowserNameIsReturned() {
            assertThat(cut.getRemoteBrowserName()).isEqualTo("firefox");
        }

        @Test
        public void defaultRemoteBrowserVersionIsReturned() {
            assertThat(cut.getRemoteBrowserVersion()).isEqualTo("");
        }

        @Test
        public void defaultRemoteFirefoxMarionetteIsReturned() {
            assertThat(cut.getRemoteFirefoxMarionette()).isEqualTo(true);
        }

        @Test
        public void defaultRemoteHostIsReturned() {
            assertThat(cut.getRemoteHost()).isEqualTo("localhost");
        }

        @Test
        public void defaultRemotePortIsReturned() {
            assertThat(cut.getRemotePort()).isEqualTo(4444);
        }

    }

    public static class NamedProperties {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void actionDecelerationCanBeChanged() {
            Configuration configuration = cut.setActionDeceleration(100L);
            assertThat(cut.getActionDeceleration()).isEqualTo(100L);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void eventSystemEnabledCanBeChanged() {
            Configuration configuration = cut.setEventSystemEnabled(false);
            assertThat(cut.isEventSystemEnabled()).isEqualTo(false);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void defaultEntryPointCanBeChanged() {
            Configuration configuration = cut.setDefaultEntryPoint("http://www.foo.bar");
            assertThat(cut.getDefaultEntryPoint()).isEqualTo(Optional.of("http://www.foo.bar"));
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void screenshotFolderCanBeChanged() {
            Configuration configuration = cut.setScreenshotFolder(new File("screens"));
            assertThat(cut.getScreenshotFolder().getAbsolutePath()).isEqualTo(new File("screens").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void pageSourceFolderCanBeChanged() {
            Configuration configuration = cut.setPageSourceFolder(new File("code"));
            assertThat(cut.getPageSourceFolder().getAbsolutePath()).isEqualTo(new File("code").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void logFolderCanBeChanged() {
            Configuration configuration = cut.setLogFolder(new File("files"));
            assertThat(cut.getLogFolder().getAbsolutePath()).isEqualTo(new File("files").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void markingsEnabledCanBeChanged() {
            Configuration configuration = cut.setMarkingsEnabled(true);
            assertThat(cut.isMarkingsEnabled()).isEqualTo(true);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void markingsColorUsedBackgroundCanBeChanged() {
            Color color = Colors.BLUEVIOLET.getColorValue();
            Configuration configuration = cut.setMarkingsColorUsedBackground(color);
            assertThat(cut.getMarkingsColorUsedBackground()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void markingsColorUsedOutlineCanBeChanged() {
            Color color = Colors.ALICEBLUE.getColorValue();
            Configuration configuration = cut.setMarkingsColorUsedOutline(color);
            assertThat(cut.getMarkingsColorUsedOutline()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void markingsColorReadBackgroundCanBeChanged() {
            Color color = Colors.ANTIQUEWHITE.getColorValue();
            Configuration configuration = cut.setMarkingsColorReadBackground(color);
            assertThat(cut.getMarkingsColorReadBackground()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void markingsColorReadOutlineCanBeChanged() {
            Color color = Colors.AZURE.getColorValue();
            Configuration configuration = cut.setMarkingsColorReadOutline(color);
            assertThat(cut.getMarkingsColorReadOutline()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void waitTimeoutCanBeChanged() {
            Configuration configuration = cut.setWaitTimeout(10);
            assertThat(cut.getWaitTimeout()).isEqualTo(10);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void waitIntervalCanBeChanged() {
            Configuration configuration = cut.setWaitInterval(200L);
            assertThat(cut.getWaitInterval()).isEqualTo(200L);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void remoteBrowserNameCanBeChanged() {
            Configuration configuration = cut.setRemoteBrowserName("chrome");
            assertThat(cut.getRemoteBrowserName()).isEqualTo("chrome");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void remoteBrowserVersionCanBeChanged() {
            Configuration configuration = cut.setRemoteBrowserVersion("46.0.1");
            assertThat(cut.getRemoteBrowserVersion()).isEqualTo("46.0.1");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void remoteFirefoxMarionetteCanBeChanged() {
            Configuration configuration = cut.setRemoteFirefoxMarionette(false);
            assertThat(cut.getRemoteFirefoxMarionette()).isEqualTo(false);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void remoteHostCanBeChanged() {
            Configuration configuration = cut.setRemoteHost("127.0.0.1");
            assertThat(cut.getRemoteHost()).isEqualTo("127.0.0.1");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void remotePortCanBeChanged() {
            Configuration configuration = cut.setRemotePort(1337);
            assertThat(cut.getRemotePort()).isEqualTo(1337);
            assertThat(configuration).isSameAs(cut);
        }

    }

    public static class SettingOfProperties {

        BaseConfiguration cut = new BaseConfiguration();

        /* setProperty(...) good-cases are part of the "GettingOfProperties" set
         * of tests */

        @Test(expected = SetNullValuesException.class)
        public void testThatSettingNullValueLeadsToException() {
            cut.setProperty("null-value", null);
        }

        @Test(expected = InvalidValueTypeException.class)
        public void testThatSettingInvalidTypeValueLeadsToException() {
            cut.setProperty("invalid-type", this);
        }

        @Test
        public void settingPropertyReturnsSameConfigurationInstance() {
            Configuration configuration = cut.setProperty("property", "value");
            assertThat(configuration).isSameAs(cut);
        }

    }

    public static class GettingOfProperties {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void testSetAndGetString() {
            cut.setProperty("string", "42");
            Optional<String> stringProperty = cut.getStringProperty("string");
            assertThat(stringProperty).contains("42");
        }

        @Test
        public void testSetAndGetInteger() {
            cut.setProperty("integer", 42);
            Optional<Integer> integerProperty = cut.getIntegerProperty("integer");
            assertThat(integerProperty).contains(42);
        }

        @Test
        public void testSetAndGetLong() {
            cut.setProperty("long", 42L);
            Optional<Long> longProperty = cut.getLongProperty("long");
            assertThat(longProperty).contains(42L);
        }

        @Test
        public void testSetAndGetFloat() {
            cut.setProperty("float", 42.0f);
            Optional<Float> floatProperty = cut.getFloatProperty("float");
            assertThat(floatProperty).contains(42.0f);
        }

        @Test
        public void testSetAndGetDouble() {
            cut.setProperty("double", 42.0d);
            Optional<Double> doubleProperty = cut.getDoubleProperty("double");
            assertThat(doubleProperty).contains(42.0d);
        }

        @Test
        public void testSetAndGetBoolean() {
            cut.setProperty("boolean", true);
            Optional<Boolean> booleanProperty = cut.getBooleanProperty("boolean");
            assertThat(booleanProperty).contains(true);
        }

        @Test
        public void testSetAndGetProperty() {
            cut.setProperty("property", "value");
            Optional<Object> property = cut.getProperty("property");
            assertThat(property).contains("value");
        }

    }

    public static class GettingOfDefaultProperties {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void testGetStringWithDefault() {
            String stringProperty = cut.getStringProperty("string", "42");
            assertThat(stringProperty).isEqualTo("42");
        }

        @Test
        public void testGetIntegerWithDefault() {
            Integer integerProperty = cut.getIntegerProperty("integer", 42);
            assertThat(integerProperty).isEqualTo(42);
        }

        @Test
        public void testGetLongWithDefault() {
            Long longProperty = cut.getLongProperty("long", 42L);
            assertThat(longProperty).isEqualTo(42L);
        }

        @Test
        public void testGetFloatWithDefault() {
            Float floatProperty = cut.getFloatProperty("float", 42.0f);
            assertThat(floatProperty).isEqualTo(42.0f);
        }

        @Test
        public void testGetDoubleWithDefault() {
            Double doubleProperty = cut.getDoubleProperty("double", 42.0d);
            assertThat(doubleProperty).isEqualTo(42.0d);
        }

        @Test
        public void testGetBooleanWithDefault() {
            Boolean booleanProperty = cut.getBooleanProperty("boolean", true);
            assertThat(booleanProperty).isEqualTo(true);
        }

        @Test
        public void testGetPropertyWithDefault() {
            Object property = cut.getProperty("property", "value");
            assertThat(property).isEqualTo(( Object ) "value");
        }

    }

    public static class RemovalOfProperties {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void testRemoveProperty() {
            cut.setProperty("property", "value");
            cut.removeProperty("property");
            assertThat(cut.getProperty("property")).isEmpty();
        }

        @Test
        public void removingPropertyReturnsSameConfigurationInstance() {
            Configuration configuration = cut.removeProperty("property");
            assertThat(configuration).isSameAs(cut);
        }

    }

    public static class GettingOfPropertyKeys {

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void testGetPropertyKeys() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            assertThat(cut.getKeys()).containsOnly("property-one", "property-two");
        }

        @Test(expected = UnsupportedOperationException.class)
        public void testThatKeysAreReturnedAsImmutableSet_Add() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            cut.getKeys().add("property-three");
        }

        @Test(expected = UnsupportedOperationException.class)
        public void testThatKeysAreReturnedAsImmutableSet_Remove() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            cut.getKeys().remove("property-three");
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class AddingOfExporters {

        @Mock
        ConfigurationExporter exporter;

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void addingExporterReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporter(exporter);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void addingMultipleExportersReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporters(exporter, exporter);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        public void addingListOfExportersReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporters(Arrays.asList(exporter, exporter));
            assertThat(configuration).isSameAs(cut);
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class BehaviorTests {

        @Mock
        ConfigurationExporter firstExporter;
        @Mock
        ConfigurationExporter secondExporter;
        @Mock
        ConfigurationExporter thirdExporter;
        @Mock
        ConfigurationExporter fourthExporter;
        @Mock
        ConfigurationExporter fifthExporter;

        BaseConfiguration cut = new BaseConfiguration();

        @Test
        public void testThatSettingPropertiesCallsExportersInOrder() {

            cut.addExporter(firstExporter);
            cut.addExporters(secondExporter, thirdExporter);
            cut.addExporters(Arrays.asList(fourthExporter, fifthExporter));

            cut.setProperty("property", "foo");
            cut.setProperty("another-property", "bar");

            InOrder inOrder = inOrder(firstExporter, secondExporter, thirdExporter, fourthExporter, fifthExporter);

            inOrder.verify(firstExporter).export("property", "foo");
            inOrder.verify(secondExporter).export("property", "foo");
            inOrder.verify(thirdExporter).export("property", "foo");
            inOrder.verify(fourthExporter).export("property", "foo");
            inOrder.verify(fifthExporter).export("property", "foo");

            inOrder.verify(firstExporter).export("another-property", "bar");
            inOrder.verify(secondExporter).export("another-property", "bar");
            inOrder.verify(thirdExporter).export("another-property", "bar");
            inOrder.verify(fourthExporter).export("another-property", "bar");
            inOrder.verify(fifthExporter).export("another-property", "bar");

            inOrder.verifyNoMoreInteractions();

        }

    }

}
