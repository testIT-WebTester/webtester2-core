package info.novatec.testit.webtester.config;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.support.Color;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.builders.BaseConfigurationBuilder;
import info.novatec.testit.webtester.config.exceptions.InvalidValueTypeException;
import info.novatec.testit.webtester.config.exceptions.SetNullValuesException;
import info.novatec.testit.webtester.internal.configuration.NamedProperties;


/**
 * This is the base implementation of a {@link Configuration configuration}.
 * <p>
 * It can be initialized using a {@link BaseConfigurationBuilder base configuration builder}. And provides defaults for all
 * major named properties in case no {@link ConfigurationAdapter adapters} are used to construct the configuration.
 * <p>
 * <b>Examples:</b><br>
 * <code>new BaseConfigurationBuilder().build();</code><br>
 * <code>new BaseConfigurationBuilder().withAdapter(adapter).build();</code>
 *
 * @see Configuration
 * @see ConfigurationBuilder
 * @see ConfigurationAdapter
 * @see BaseConfigurationBuilder
 * @since 2.0
 */
@Slf4j
public class BaseConfiguration implements Configuration {

    private static final Set<Class<?>> ALLOWED_TYPES =
        asSet(String.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class);

    private final Map<String, String> properties = new HashMap<>();
    private final List<ConfigurationExporter> configurationExporters = new LinkedList<>();

    /* named properties */

    @Override
    public long getActionDeceleration() {
        return getLongProperty(key(NamedProperties.ACTIONS_DECELERATION), 0L);
    }

    @Override
    public Configuration setActionDeceleration(long value) {
        return setProperty(key(NamedProperties.ACTIONS_DECELERATION), value);
    }

    @Override
    public boolean isEventSystemEnabled() {
        return getBooleanProperty(key(NamedProperties.EVENTS), Boolean.TRUE);
    }

    @Override
    public Configuration setEventSystemEnabled(boolean enabled) {
        return setProperty(key(NamedProperties.EVENTS), enabled);
    }

    @Override
    public Optional<String> getDefaultEntryPoint() {
        return getStringProperty(key(NamedProperties.DEFAULTS_ENTRY_POINT));
    }

    @Override
    public BaseConfiguration setDefaultEntryPoint(String entryPoint) {
        return setProperty(key(NamedProperties.DEFAULTS_ENTRY_POINT), entryPoint);
    }

    @Override
    public File getScreenshotFolder() {
        return new File(getStringProperty(key(NamedProperties.FOLDERS_SCREENSHOT), "screenshots"));
    }

    @Override
    public BaseConfiguration setScreenshotFolder(File folder) {
        return setProperty(key(NamedProperties.FOLDERS_SCREENSHOT), folder.getAbsolutePath());
    }

    @Override
    public File getPageSourceFolder() {
        return new File(getStringProperty(key(NamedProperties.FOLDERS_PAGE_SOURCE), "sourcecode"));
    }

    @Override
    public BaseConfiguration setPageSourceFolder(File folder) {
        return setProperty(key(NamedProperties.FOLDERS_PAGE_SOURCE), folder.getAbsolutePath());
    }

    @Override
    public File getLogFolder() {
        return new File(getStringProperty(key(NamedProperties.FOLDERS_LOG), "logs"));
    }

    @Override
    public BaseConfiguration setLogFolder(File folder) {
        return setProperty(key(NamedProperties.FOLDERS_LOG), folder.getAbsolutePath());
    }

    @Override
    public boolean isMarkingsEnabled() {
        return getBooleanProperty(key(NamedProperties.MARKINGS), Boolean.FALSE);
    }

    @Override
    public BaseConfiguration setMarkingsEnabled(boolean activated) {
        return setProperty(key(NamedProperties.MARKINGS), activated);
    }

    @Override
    public Color getMarkingsColorUsedBackground() {
        return Color.fromString(getStringProperty(key(NamedProperties.MARKINGS_USED_BACKGROUND), "#ffd2a5"));
    }

    @Override
    public BaseConfiguration setMarkingsColorUsedBackground(Color color) {
        return setProperty(key(NamedProperties.MARKINGS_USED_BACKGROUND), color.asHex());
    }

    @Override
    public Color getMarkingsColorUsedOutline() {
        return Color.fromString(getStringProperty(key(NamedProperties.MARKINGS_USED_OUTLINE), "#916f22"));
    }

    @Override
    public BaseConfiguration setMarkingsColorUsedOutline(Color color) {
        return setProperty(key(NamedProperties.MARKINGS_USED_OUTLINE), color.asHex());
    }

    @Override
    public Color getMarkingsColorReadBackground() {
        return Color.fromString(getStringProperty(key(NamedProperties.MARKINGS_READ_BACKGROUND), "#90ee90"));
    }

    @Override
    public Configuration setMarkingsColorReadBackground(Color color) {
        return setProperty(key(NamedProperties.MARKINGS_READ_BACKGROUND), color.asHex());
    }

    @Override
    public Color getMarkingsColorReadOutline() {
        return Color.fromString(getStringProperty(key(NamedProperties.MARKINGS_READ_OUTLINE), "#008000"));
    }

    @Override
    public Configuration setMarkingsColorReadOutline(Color color) {
        return setProperty(key(NamedProperties.MARKINGS_READ_OUTLINE), color.asHex());
    }

    @Override
    public int getWaitTimeout() {
        return getIntegerProperty(key(NamedProperties.WAIT_TIMEOUT), 2);
    }

    @Override
    public BaseConfiguration setWaitTimeout(int waitTimeout) {
        return setProperty(key(NamedProperties.WAIT_TIMEOUT), waitTimeout);
    }

    @Override
    public long getWaitInterval() {
        return getLongProperty(key(NamedProperties.WAIT_INTERVAL), 100L);
    }

    @Override
    public BaseConfiguration setWaitInterval(long waitInterval) {
        return setProperty(key(NamedProperties.WAIT_INTERVAL), waitInterval);
    }

    private String key(NamedProperties property) {
        return property.getKey();
    }

    /* low-level function */

    @Override
    public BaseConfiguration removeProperty(String key) {
        properties.remove(key);
        log.debug("removed property '{}'", key);
        return this;
    }

    @Override
    public BaseConfiguration setProperty(String key, Object value) {

        assertNotNull(key, value);
        assertAllowedType(value);

        changeValue(key, value);
        exportProperty(key, value);

        return this;

    }

    private void assertNotNull(String key, Object value) {
        if (value == null) {
            throw new SetNullValuesException(key);
        }
    }

    private void assertAllowedType(Object value) {
        if (!ALLOWED_TYPES.contains(value.getClass())) {
            throw new InvalidValueTypeException(value.getClass(), ALLOWED_TYPES);
        }
    }

    private void changeValue(String key, Object value) {
        properties.put(key, String.valueOf(value));
        log.debug("changed value of property '{}' to: {}", key, value);
    }

    private void exportProperty(String key, Object value) {
        if (!configurationExporters.isEmpty()) {
            log.debug("exporting change of property '{}' to exporters", key);
            for (ConfigurationExporter exporter : configurationExporters) {
                log.trace("exporting change of property '{}'='{}' to {}", key, value, exporter);
                exporter.export(key, value);
            }
        }
    }

    @Override
    public Optional<String> getStringProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(String::valueOf);
    }

    @Override
    public String getStringProperty(String key, String defaultValue) {
        return getStringProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Integer> getIntegerProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(Integer::valueOf);
    }

    @Override
    public Integer getIntegerProperty(String key, Integer defaultValue) {
        return getIntegerProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Long> getLongProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(Long::valueOf);
    }

    @Override
    public Long getLongProperty(String key, Long defaultValue) {
        return getLongProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Float> getFloatProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(Float::valueOf);
    }

    @Override
    public Float getFloatProperty(String key, Float defaultValue) {
        return getFloatProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Double> getDoubleProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(Double::valueOf);
    }

    @Override
    public Double getDoubleProperty(String key, Double defaultValue) {
        return getDoubleProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Boolean> getBooleanProperty(String key) {
        return Optional.ofNullable(properties.get(key)).map(Boolean::valueOf);
    }

    @Override
    public Boolean getBooleanProperty(String key, Boolean defaultValue) {
        return getBooleanProperty(key).orElse(defaultValue);
    }

    @Override
    public Optional<Object> getProperty(String key) {
        return Optional.ofNullable(properties.get(key));
    }

    @Override
    public Object getProperty(String key, Object defaultValue) {
        return getProperty(key).orElse(defaultValue);
    }

    @Override
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(properties.keySet());
    }

    @Override
    public BaseConfiguration addExporters(ConfigurationExporter exporter, ConfigurationExporter... additionalExporters) {
        return addExporter(exporter).addExporters(Arrays.asList(additionalExporters));
    }

    @Override
    public BaseConfiguration addExporter(ConfigurationExporter exporter) {
        configurationExporters.add(exporter);
        log.debug("added configuration exporter: {}", exporter);
        return this;
    }

    @Override
    public BaseConfiguration addExporters(Collection<ConfigurationExporter> exportersToAdd) {
        configurationExporters.addAll(exportersToAdd);
        log.debug("added configuration exporters: {}", exportersToAdd);
        return this;
    }

    /* utilities */

    private static Set<Class<?>> asSet(Class<?>... types) {
        return Arrays.stream(types).collect(Collectors.toSet());
    }

}
