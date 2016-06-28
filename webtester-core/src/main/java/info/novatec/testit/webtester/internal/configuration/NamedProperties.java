package info.novatec.testit.webtester.internal.configuration;

import java.util.HashSet;
import java.util.Set;


public enum NamedProperties {

    @Documentation("Whether or not the events should be fired.")
    @TypeDefinition(Constants.BOOLEAN)
    @DefaultValue("true")
    EVENTS("events.enabled"),

    @Documentation("The amount of time actions should be decelerated (i.e. for demonstrations).")
    @TypeDefinition(Constants.MILLISECONDS_AS_INT)
    @DefaultValue("0")
    ACTIONS_DECELERATION("actions.deceleration"),

    @Documentation("URL of the default entry point for the application under test.")
    @TypeDefinition(Constants.RESOURCE_URL)
    DEFAULTS_ENTRY_POINT("defaults.entry-point"),

    @Documentation("Folder in which to save screenshots if not otherwise specified.")
    @TypeDefinition(Constants.FOLDER_PATH)
    @DefaultValue("screenshots")
    FOLDERS_SCREENSHOT("folders.screenshots"),

    @Documentation("Folder in which to save source code of pages if not otherwise specified.")
    @TypeDefinition(Constants.FOLDER_PATH)
    @DefaultValue("sourcecode")
    FOLDERS_PAGE_SOURCE("folders.page-sources"),

    @Documentation("Folder in which to save log files if not otherwise specified.")
    @TypeDefinition(Constants.FOLDER_PATH)
    @DefaultValue("logs")
    FOLDERS_LOG("folders.logs"),

    @Documentation("Whether or not color highlighting of used elements should be active or not.")
    @TypeDefinition(Constants.BOOLEAN)
    @DefaultValue("false")
    MARKINGS("markings.enabled"),

    @Documentation("Color to use for the background of used elements if color highlighting is active.")
    @TypeDefinition(Constants.HEX_COLOR)
    @DefaultValue("#ffd2a5")
    MARKINGS_USED_BACKGROUND("markings.used.background"),

    @Documentation("Color to use for the outline of used elements if color highlighting is active.")
    @TypeDefinition(Constants.HEX_COLOR)
    @DefaultValue("#916f22")
    MARKINGS_USED_OUTLINE("markings.used.outline"),

    @Documentation("Color to use for the background of read elements if color highlighting is active.")
    @TypeDefinition(Constants.HEX_COLOR)
    @DefaultValue("#90ee90")
    MARKINGS_READ_BACKGROUND("markings.read.background"),

    @Documentation("Color to use for the outline of read elements if color highlighting is active.")
    @TypeDefinition(Constants.HEX_COLOR)
    @DefaultValue("#008000")
    MARKINGS_READ_OUTLINE("markings.read.outline"),

    @Documentation("Default timeout for wait operations.")
    @TypeDefinition(Constants.SECONDS_AS_INT)
    @DefaultValue("2")
    WAIT_TIMEOUT("wait.timeout"),

    @Documentation("Default interval in which to check a condition for wait operations.")
    @TypeDefinition(Constants.MILLISECONDS_AS_INT)
    @DefaultValue("100")
    WAIT_INTERVAL("wait.interval");

    private final String key;

    NamedProperties(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Set<String> getKeys() {
        Set<String> keys = new HashSet<>();
        for (NamedProperties configurationKey : values()) {
            keys.add(configurationKey.getKey());
        }
        return keys;
    }

    private interface Constants {
        String BOOLEAN = "boolean [true, false]";
        String FOLDER_PATH = "String [absolute or relative path to be initialized as a java.io.File instance]";
        String HEX_COLOR = "String [HEX RGB code starting with'#']";
        String SECONDS_AS_INT = "int [seconds]";
        String MILLISECONDS_AS_INT = "int [milliseconds]";
        String RESOURCE_URL = "String [Resource URL]";
    }

}
