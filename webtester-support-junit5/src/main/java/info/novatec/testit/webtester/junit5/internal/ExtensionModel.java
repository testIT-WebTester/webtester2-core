package info.novatec.testit.webtester.junit5.internal;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;


/**
 * This model stores information about the test class and is used by WebTester's JUnit extensions to exchange data.
 *
 * @since 2.1
 */
@Getter
@Setter
@SuppressWarnings("PMD.UnusedPrivateField")
public class ExtensionModel {

    private List<Field> browserFields;
    private Map<String, Field> namedBrowserFields;
    private List<Field> pageFields;
    private List<Field> configurationValueFields;

    public void setBrowserFields(List<Field> browserFields) {
        this.browserFields = browserFields;
        this.namedBrowserFields = mapNamedBrowserFields(browserFields);
    }

    public Map<String, Field> mapNamedBrowserFields(List<Field> browserFields) {
        Set<String> uniqueNames = new HashSet<>();
        Map<String, Field> nameToFieldMap = new HashMap<>();
        browserFields.forEach(field -> {
            String browserName = field.getAnnotation(Managed.class).value();
            if (uniqueNames.contains(browserName)) {
                throw new IllegalStateException("every browser needs a unique name!");
            }
            uniqueNames.add(browserName);
            nameToFieldMap.put(browserName, field);
        });
        return nameToFieldMap;
    }

}
