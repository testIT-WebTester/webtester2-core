package info.novatec.testit.webtester.config;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.support.Color;

import info.novatec.testit.webtester.config.exceptions.SetNullValuesException;
import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.waiting.Wait;


/**
 * Classes implementing this interface allow access to a variety of configuration properties for the framework. These
 * properties can be retrieved and changed at runtime.
 * <p>
 * Instances should be constructed using a {@link ConfigurationBuilder builder}. Since they provide for a comfortable way to
 * use specific {@link ConfigurationAdapter adapters} and {@link ConfigurationExporter exporters} to adapt (change
 * configuration based on different sources) and export the configuration's properties and values.
 * <p>
 * Besides a number of 'named' properties with their own setter- and getter-methods custom properties can be set and read as
 * well.
 * <p>
 * In order to allow the export of set properties (and changes to them) any number of {@link ConfigurationExporter exporters}
 * can be defined.
 *
 * @see ConfigurationBuilder
 * @see ConfigurationAdapter
 * @see ConfigurationExporter
 * @since 2.0
 */
public interface Configuration {

    /**
     * Returns the number of milliseconds actions should be decelerated. Actions are methods annotated with
     * {@link Action @Action}.
     * <p>
     * A value of <code>0</code> will be interpreted as "no deceleration".
     *
     * @return true if caching is enabled by default
     * @see Action
     * @since 2.0
     */

    long getActionDeceleration();

    /**
     * Set the number of milliseconds actions should be decelerated. Actions are methods annotated with
     * {@link Action @Action}.
     * <p>
     * A value of <code>0</code> will be interpreted as "no deceleration".
     *
     * @param value the amount of deceleration in milliseconds
     * @return the same instance for fluent API use
     * @see Action
     * @since 2.0
     */
    Configuration setActionDeceleration(long value);

    /**
     * Returns whether or not the firing of events is enabled. Disabling this may improve performance significantly but at
     * the cost of traceability.
     *
     * @return true id the firing of events is enabled
     * @see Event
     * @see EventSystem
     * @since 2.0
     */
    boolean isEventSystemEnabled();

    /**
     * Returns whether or not the firing of events is enabled. Disabling this may improve performance significantly but at
     * the cost of traceability.
     *
     * @param active true if the firing of events should be enabled
     * @return the same instance for fluent API use
     * @see Event
     * @see EventSystem
     * @since 2.0
     */
    Configuration setEventSystemEnabled(boolean active);

    /**
     * Returns the default entry point of the application under test if set.
     * <p>
     * This can be used to navigate to a specific URL without having to specify it in code.
     *
     * @return the optional URL
     * @since 2.0
     */
    Optional<String> getDefaultEntryPoint();

    /**
     * Sets the default entry point of the application under test.
     * <p>
     * This can be used to navigate to a specific URL without having to specify it in code.
     *
     * @param entryPoint the entry point to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setDefaultEntryPoint(String entryPoint);

    /**
     * Returns the folder were screenshots files should be saved if no specific folder is provided.
     *
     * @return the folder
     * @since 2.0
     */
    File getScreenshotFolder();

    /**
     * Sets the folder were screenshots files should be saved if no specific folder is provided.
     *
     * @param folder the folder to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setScreenshotFolder(File folder);

    /**
     * Returns the folder were source code files should be saved if no specific folder is provided.
     *
     * @return the folder
     * @since 2.0
     */
    File getPageSourceFolder();

    /**
     * Sets the folder were source code files should be saved if no specific folder is provided.
     *
     * @param folder the folder to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setPageSourceFolder(File folder);

    /**
     * Returns the folder were logs files should be saved if no specific folder is provided.
     *
     * @return the folder
     * @since 2.0
     */
    File getLogFolder();

    /**
     * Sets the folder were logs files should be saved if no specific folder is provided.
     *
     * @param folder the folder to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setLogFolder(File folder);

    /**
     * Returns whether or not markings are activated. If activated most interactions with elements on a page will mark them
     * as 'used' with the configured {@link #getMarkingsColorUsedBackground()} and {@link #getMarkingsColorUsedOutline()}
     * colors.
     *
     * @return true if markings are active, otherwise false
     * @since 2.0
     */
    boolean isMarkingsEnabled();

    /**
     * Sets whether or not markings are activated. If activated most interactions with elements on a page will mark them as
     * 'used' with the configured {@link #getMarkingsColorUsedBackground()} and {@link #getMarkingsColorUsedOutline()}
     * colors.
     *
     * @param activated whether or not markings should be activated
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setMarkingsEnabled(boolean activated);

    /**
     * Returns the color to use for the background of elements marked as 'used' on a page.
     *
     * @return the color
     * @since 2.0
     */
    Color getMarkingsColorUsedBackground();

    /**
     * Sets the color to use for the background of elements marked as 'used' on a page.
     *
     * @param color the color to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setMarkingsColorUsedBackground(Color color);

    /**
     * Returns the color to use for the outline of elements marked as 'used' on a page.
     *
     * @return the color
     * @since 2.0
     */
    Color getMarkingsColorUsedOutline();

    /**
     * Sets the color to use for the outline of elements marked as 'used' on a page.
     *
     * @param color the color to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setMarkingsColorUsedOutline(Color color);

    /**
     * Returns the color to use for the background of elements marked as 'read' on a page.
     *
     * @return the color
     * @since 2.0
     */
    Color getMarkingsColorReadBackground();

    /**
     * Sets the color to use for the background of elements marked as 'read' on a page.
     *
     * @param color the color to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setMarkingsColorReadBackground(Color color);

    /**
     * Returns the color to use for the outline of elements marked as 'read' on a page.
     *
     * @return the color
     * @since 2.0
     */
    Color getMarkingsColorReadOutline();

    /**
     * Sets the color to use for the outline of elements marked as 'read' on a page.
     *
     * @param color the color to set
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setMarkingsColorReadOutline(Color color);

    /**
     * Returns the default maximum number of seconds to wait when executing wait operations on page objects using the
     * {@link Wait} API.
     *
     * @return the number of seconds
     * @since 2.0
     */
    int getWaitTimeout();

    /**
     * Sets the default maximum number of seconds to wait when executing wait operations on page objects using the
     * {@link Wait} API.
     *
     * @param waitTimeout timeout in seconds
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setWaitTimeout(int waitTimeout);

    /**
     * Returns the default number of milliseconds to wait between checks when executing wait operations on page objects
     * using the {@link Wait} API.
     *
     * @return the number of milliseconds
     * @since 2.0
     */
    long getWaitInterval();

    /**
     * Sets the default number of milliseconds to wait between checks when executing wait operations on page objects
     * using the {@link Wait} API.
     *
     * @param waitInterval the interval in milliseconds
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration setWaitInterval(long waitInterval);

    /**
     * Removes the property with the given key from this {@link Configuration configuration}.
     *
     * @param key the key of the property to removed
     * @return the same configuration for fluent API
     * @since 2.0
     */
    Configuration removeProperty(String key);

    /**
     * Sets the given property key to the given value. Also calls all {@link ConfigurationExporter configuration exporters}
     * of this {@link Configuration configuration} to inform them about the change to the property.
     * <p>
     * <b>Note:</b> NULL values are not allowed - use {@link #removeProperty(String)} for removing properties.
     * <p>
     * <b>Valid value types:</b>
     * <ul>
     * <li>String</li>
     * <li>Integer</li>
     * <li>Long</li>
     * <li>Float</li>
     * <li>Double</li>
     * <li>Boolean</li>
     * </ul>
     *
     * @param key the key
     * @param value the value
     * @return the same configuration instance for fluent API
     * @throws SetNullValuesException in case a NULL value is given
     * @since 2.0
     */
    Configuration setProperty(String key, Object value);

    /**
     * Returns a property value as a string.
     *
     * @param key the property key to use
     * @return the property for the given key as a String
     * @since 2.0
     */
    Optional<String> getStringProperty(String key);

    /**
     * Returns a property value as a string. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as a String
     * @since 2.0
     */
    String getStringProperty(String key, String defaultValue);

    /**
     * Returns a property value as an integer.
     *
     * @param key the property key to use
     * @return the property for the given key as an integer
     * @since 2.0
     */
    Optional<Integer> getIntegerProperty(String key);

    /**
     * Returns a property value as an integer. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as an integer
     * @since 2.0
     */
    Integer getIntegerProperty(String key, Integer defaultValue);

    /**
     * Returns a property value as a long.
     *
     * @param key the property key to use
     * @return the property for the given key as a long
     * @since 2.0
     */
    Optional<Long> getLongProperty(String key);

    /**
     * Returns a property value as a long. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as a long
     * @since 2.0
     */
    Long getLongProperty(String key, Long defaultValue);

    /**
     * Returns a property value as a float.
     *
     * @param key the property key to use
     * @return the property for the given key as a float
     * @since 2.0
     */
    Optional<Float> getFloatProperty(String key);

    /**
     * Returns a property value as a float. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as a float
     * @since 2.0
     */
    Float getFloatProperty(String key, Float defaultValue);

    /**
     * Returns a property value as a double.
     *
     * @param key the property key to use
     * @return the property for the given key as a double
     * @since 2.0
     */
    Optional<Double> getDoubleProperty(String key);

    /**
     * Returns a property value as a double. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as a double
     * @since 2.0
     */
    Double getDoubleProperty(String key, Double defaultValue);

    /**
     * Returns a property value as a boolean.
     *
     * @param key the property key to use
     * @return the property for the given key as a boolean
     * @since 2.0
     */
    Optional<Boolean> getBooleanProperty(String key);

    /**
     * Returns a property value as a boolean. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as a boolean
     * @since 2.0
     */
    Boolean getBooleanProperty(String key, Boolean defaultValue);

    /**
     * Returns a property value as an object.
     *
     * @param key the property key to use
     * @return the property for the given key as an object
     * @since 2.0
     */
    Optional<Object> getProperty(String key);

    /**
     * Returns a property value as an object. If the property value is <code>null</code> the given default is returned.
     *
     * @param key the property key to use
     * @param defaultValue the default value to return if no property for the
     * given key was found
     * @return the property for the given key as an object
     * @since 2.0
     */
    Object getProperty(String key, Object defaultValue);

    /**
     * Returns all property keys as a set.
     *
     * @return the property keys
     * @since 2.0
     */
    Set<String> getKeys();

    /**
     * Add the given {@link ConfigurationExporter exporter} to the pool of exporters to be informed about changes to
     * properties.
     *
     * @param exporterToAdd the exporter to add
     * @return the same configuration instance for fluent API
     * @since 2.0
     */
    Configuration addExporter(ConfigurationExporter exporterToAdd);

    /**
     * Add the given {@link ConfigurationExporter exporters} to the pool of exporters to be informed about changes to
     * properties.
     *
     * @param exporter the first exporter to add
     * @param additionalExporters additional exporters to add
     * @return the same configuration instance for fluent API
     * @since 2.0
     */
    Configuration addExporters(ConfigurationExporter exporter, ConfigurationExporter... additionalExporters);

    /**
     * Add the given {@link ConfigurationExporter exporters} to the pool of exporters to be informed about changes to
     * properties.
     *
     * @param exportersToAdd the exporters to add
     * @return the same configuration instance for fluent API
     * @since 2.0
     */
    Configuration addExporters(Collection<ConfigurationExporter> exportersToAdd);

}
