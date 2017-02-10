package info.novatec.testit.webtester.config;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.inOrder;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.testit.testutils.mockito.junit5.EnableMocking;

import info.novatec.testit.webtester.config.exceptions.InvalidValueTypeException;
import info.novatec.testit.webtester.config.exceptions.SetNullValuesException;


class BaseConfigurationTest {

    BaseConfiguration cut = new BaseConfiguration();

    @Nested
    class DefaultValues {

        @Test
        void defaultActionConfiguration() {
            assertThat(cut.getActionDeceleration()).isZero();
        }

        @Test
        void defaultEventSystemConfiguration() {
            assertThat(cut.isEventSystemEnabled()).isTrue();
        }

        @Test
        void defaultEntryPointConfiguration() {
            assertThat(cut.getDefaultEntryPoint()).isEmpty();
        }

        @Test
        void defaultFolderConfiguration() {
            assertThat(cut.getScreenshotFolder()).isEqualTo(folderInWorkingDirectory("screenshots"));
            assertThat(cut.getPageSourceFolder()).isEqualTo(folderInWorkingDirectory("sourcecode"));
            assertThat(cut.getLogFolder()).isEqualTo(folderInWorkingDirectory("logs"));
        }

        @Test
        void defaultMarkingConfiguration() {
            assertThat(cut.isMarkingsEnabled()).isFalse();
            assertThat(cut.getMarkingsColorUsedBackground().asHex()).isEqualTo("#ffd2a5");
            assertThat(cut.getMarkingsColorUsedOutline().asHex()).isEqualTo("#916f22");
            assertThat(cut.getMarkingsColorReadBackground().asHex()).isEqualTo("#90ee90");
            assertThat(cut.getMarkingsColorReadOutline().asHex()).isEqualTo("#008000");
        }

        @Test
        void defaultWaitConfiguration() {
            assertThat(cut.getWaitTimeout()).isEqualTo(2);
            assertThat(cut.getWaitInterval()).isEqualTo(100L);
        }

        @Test
        void defaultRemoteConfiguration() {
            assertThat(cut.getRemoteBrowserName()).isEqualTo("firefox");
            assertThat(cut.getRemoteBrowserVersion()).isEqualTo("");
            assertThat(cut.getRemoteFirefoxMarionette()).isTrue();
            assertThat(cut.getRemoteHost()).isEqualTo("localhost");
            assertThat(cut.getRemotePort()).isEqualTo(4444);
        }

    }

    @Nested
    class NamedProperties {

        @Test
        void actionDecelerationCanBeChanged() {
            Configuration configuration = cut.setActionDeceleration(100L);
            assertThat(cut.getActionDeceleration()).isEqualTo(100L);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void eventSystemEnabledCanBeChanged() {
            Configuration configuration = cut.setEventSystemEnabled(false);
            assertThat(cut.isEventSystemEnabled()).isEqualTo(false);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void defaultEntryPointCanBeChanged() {
            Configuration configuration = cut.setDefaultEntryPoint("http://www.foo.bar");
            assertThat(cut.getDefaultEntryPoint()).isEqualTo(Optional.of("http://www.foo.bar"));
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void screenshotFolderCanBeChanged() {
            Configuration configuration = cut.setScreenshotFolder(folderInWorkingDirectory("screens"));
            assertThat(cut.getScreenshotFolder().getAbsolutePath()).isEqualTo(
                folderInWorkingDirectory("screens").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void pageSourceFolderCanBeChanged() {
            Configuration configuration = cut.setPageSourceFolder(folderInWorkingDirectory("code"));
            assertThat(cut.getPageSourceFolder().getAbsolutePath()).isEqualTo(
                folderInWorkingDirectory("code").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void logFolderCanBeChanged() {
            Configuration configuration = cut.setLogFolder(folderInWorkingDirectory("files"));
            assertThat(cut.getLogFolder().getAbsolutePath()).isEqualTo(folderInWorkingDirectory("files").getAbsolutePath());
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void markingsEnabledCanBeChanged() {
            Configuration configuration = cut.setMarkingsEnabled(true);
            assertThat(cut.isMarkingsEnabled()).isEqualTo(true);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void markingsColorUsedBackgroundCanBeChanged() {
            Color color = Colors.BLUEVIOLET.getColorValue();
            Configuration configuration = cut.setMarkingsColorUsedBackground(color);
            assertThat(cut.getMarkingsColorUsedBackground()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void markingsColorUsedOutlineCanBeChanged() {
            Color color = Colors.ALICEBLUE.getColorValue();
            Configuration configuration = cut.setMarkingsColorUsedOutline(color);
            assertThat(cut.getMarkingsColorUsedOutline()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void markingsColorReadBackgroundCanBeChanged() {
            Color color = Colors.ANTIQUEWHITE.getColorValue();
            Configuration configuration = cut.setMarkingsColorReadBackground(color);
            assertThat(cut.getMarkingsColorReadBackground()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void markingsColorReadOutlineCanBeChanged() {
            Color color = Colors.AZURE.getColorValue();
            Configuration configuration = cut.setMarkingsColorReadOutline(color);
            assertThat(cut.getMarkingsColorReadOutline()).isEqualTo(color);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void waitTimeoutCanBeChanged() {
            Configuration configuration = cut.setWaitTimeout(10);
            assertThat(cut.getWaitTimeout()).isEqualTo(10);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void waitIntervalCanBeChanged() {
            Configuration configuration = cut.setWaitInterval(200L);
            assertThat(cut.getWaitInterval()).isEqualTo(200L);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void remoteBrowserNameCanBeChanged() {
            Configuration configuration = cut.setRemoteBrowserName("chrome");
            assertThat(cut.getRemoteBrowserName()).isEqualTo("chrome");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void remoteBrowserVersionCanBeChanged() {
            Configuration configuration = cut.setRemoteBrowserVersion("46.0.1");
            assertThat(cut.getRemoteBrowserVersion()).isEqualTo("46.0.1");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void remoteFirefoxMarionetteCanBeChanged() {
            Configuration configuration = cut.setRemoteFirefoxMarionette(false);
            assertThat(cut.getRemoteFirefoxMarionette()).isEqualTo(false);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void remoteHostCanBeChanged() {
            Configuration configuration = cut.setRemoteHost("127.0.0.1");
            assertThat(cut.getRemoteHost()).isEqualTo("127.0.0.1");
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void remotePortCanBeChanged() {
            Configuration configuration = cut.setRemotePort(1337);
            assertThat(cut.getRemotePort()).isEqualTo(1337);
            assertThat(configuration).isSameAs(cut);
        }

    }

    @Nested
    class SettingOfProperties {

        /* setProperty(...) good-cases are part of the "GettingOfProperties" set
         * of tests */

        @Test
        void testThatSettingNullValueLeadsToException() {
            assertThrows(SetNullValuesException.class, () -> cut.setProperty("null-value", null));
        }

        @Test
        void testThatSettingInvalidTypeValueLeadsToException() {
            assertThrows(InvalidValueTypeException.class, () -> cut.setProperty("invalid-type", this));
        }

        @Test
        void settingPropertyReturnsSameConfigurationInstance() {
            Configuration configuration = cut.setProperty("property", "value");
            assertThat(configuration).isSameAs(cut);
        }

    }

    @Nested
    class GettingOfProperties {

        @Test
        void testSetAndGetString() {
            cut.setProperty("string", "42");
            Optional<String> stringProperty = cut.getStringProperty("string");
            assertThat(stringProperty).contains("42");
        }

        @Test
        void testSetAndGetInteger() {
            cut.setProperty("integer", 42);
            Optional<Integer> integerProperty = cut.getIntegerProperty("integer");
            assertThat(integerProperty).contains(42);
        }

        @Test
        void testSetAndGetLong() {
            cut.setProperty("long", 42L);
            Optional<Long> longProperty = cut.getLongProperty("long");
            assertThat(longProperty).contains(42L);
        }

        @Test
        void testSetAndGetFloat() {
            cut.setProperty("float", 42.0f);
            Optional<Float> floatProperty = cut.getFloatProperty("float");
            assertThat(floatProperty).contains(42.0f);
        }

        @Test
        void testSetAndGetDouble() {
            cut.setProperty("double", 42.0d);
            Optional<Double> doubleProperty = cut.getDoubleProperty("double");
            assertThat(doubleProperty).contains(42.0d);
        }

        @Test
        void testSetAndGetBoolean() {
            cut.setProperty("boolean", true);
            Optional<Boolean> booleanProperty = cut.getBooleanProperty("boolean");
            assertThat(booleanProperty).contains(true);
        }

        @Test
        void testSetAndGetProperty() {
            cut.setProperty("property", "value");
            Optional<Object> property = cut.getProperty("property");
            assertThat(property).contains("value");
        }

    }

    @Nested
    class GettingOfPropertiesWithDefault {

        @Test
        void testGetStringWithDefault() {
            String stringProperty = cut.getStringProperty("string", "42");
            assertThat(stringProperty).isEqualTo("42");
        }

        @Test
        void testGetIntegerWithDefault() {
            Integer integerProperty = cut.getIntegerProperty("integer", 42);
            assertThat(integerProperty).isEqualTo(42);
        }

        @Test
        void testGetLongWithDefault() {
            Long longProperty = cut.getLongProperty("long", 42L);
            assertThat(longProperty).isEqualTo(42L);
        }

        @Test
        void testGetFloatWithDefault() {
            Float floatProperty = cut.getFloatProperty("float", 42.0f);
            assertThat(floatProperty).isEqualTo(42.0f);
        }

        @Test
        void testGetDoubleWithDefault() {
            Double doubleProperty = cut.getDoubleProperty("double", 42.0d);
            assertThat(doubleProperty).isEqualTo(42.0d);
        }

        @Test
        void testGetBooleanWithDefault() {
            Boolean booleanProperty = cut.getBooleanProperty("boolean", true);
            assertThat(booleanProperty).isEqualTo(true);
        }

        @Test
        void testGetPropertyWithDefault() {
            Object property = cut.getProperty("property", "value");
            assertThat(property).isEqualTo(( Object ) "value");
        }

    }

    @Nested
    class RemovalOfProperties {

        @Test
        void testRemoveProperty() {
            cut.setProperty("property", "value");
            cut.removeProperty("property");
            assertThat(cut.getProperty("property")).isEmpty();
        }

        @Test
        void removingPropertyReturnsSameConfigurationInstance() {
            Configuration configuration = cut.removeProperty("property");
            assertThat(configuration).isSameAs(cut);
        }

    }

    @Nested
    class GettingOfPropertyKeys {

        @Test
        void testGetPropertyKeys() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            assertThat(cut.getKeys()).containsOnly("property-one", "property-two");
        }

        @Test
        void testThatKeysAreReturnedAsImmutableSet_Add() {
            assertThrows(UnsupportedOperationException.class, () -> {
                cut.setProperty("property-one", "value");
                cut.setProperty("property-two", "value");
                cut.getKeys().add("property-three");
            });
        }

        @Test
        void testThatKeysAreReturnedAsImmutableSet_Remove() {
            assertThrows(UnsupportedOperationException.class, () -> {
                cut.setProperty("property-one", "value");
                cut.setProperty("property-two", "value");
                cut.getKeys().remove("property-three");
            });
        }

    }

    @Nested
    @EnableMocking
    class AddingOfExporters {

        @Mock
        ConfigurationExporter exporter;

        @Test
        void addingExporterReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporter(exporter);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void addingMultipleExportersReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporters(exporter, exporter);
            assertThat(configuration).isSameAs(cut);
        }

        @Test
        void addingListOfExportersReturnsSameConfigurationInstance() {
            BaseConfiguration configuration = cut.addExporters(asList(exporter, exporter));
            assertThat(configuration).isSameAs(cut);
        }

    }

    @Nested
    @EnableMocking
    class BehaviorTests {

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

        @Test
        void testThatSettingPropertiesCallsExportersInOrder() {

            cut.addExporter(firstExporter);
            cut.addExporters(secondExporter, thirdExporter);
            cut.addExporters(asList(fourthExporter, fifthExporter));

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

    File folderInWorkingDirectory(String screenshots) {
        return new File(screenshots);
    }

}
