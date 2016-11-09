package info.novatec.testit.webtester.internal.configuration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;


public final class PropertiesFileGenerator {

    private static final String TYPE_LABEL = "TYPE: ";
    private static final String EQUALS = " = ";
    private static final String OUTCOMMENTED = "# ";

    private static List<String> lines;

    public static void main(String[] args) throws NoSuchFieldException, IOException {

        lines = new ArrayList<>();

        for (NamedProperties properties : NamedProperties.values()) {

            Field field = NamedProperties.class.getField(properties.name());

            Documentation documentationAnnotation = field.getAnnotation(Documentation.class);
            TypeDefinition typeDefinitionAnnotation = field.getAnnotation(TypeDefinition.class);
            DefaultValue defaultValueAnnotation = field.getAnnotation(DefaultValue.class);

            if (documentationAnnotation != null) {
                outcommentedLine(documentationAnnotation.value());
            }

            if (typeDefinitionAnnotation != null) {
                outcommentedLine(TYPE_LABEL + typeDefinitionAnnotation.value());
            }

            if (defaultValueAnnotation == null) {
                outcommentedLine(properties.getKey() + EQUALS);
            } else {
                for (String defaultValue : defaultValueAnnotation.value()) {
                    String line = properties.getKey() + EQUALS + defaultValue;
                    if (defaultValueAnnotation.disabled()) {
                        outcommentedLine(line);
                    } else {
                        line(line);
                    }
                }
            }

            emptyLine();

        }

        File outputFolder = new File("src/main/resources");
        File propertiesFile = new File(outputFolder, "testit-webtester-default.properties");
        FileUtils.writeLines(propertiesFile, lines);

    }

    private static void outcommentedLine(String line) {
        String replacedLine = StringUtils.replace(line, "\n", "\n" + OUTCOMMENTED);
        lines.add(OUTCOMMENTED + replacedLine);
    }

    private static void line(String line) {
        lines.add(line);
    }

    private static void emptyLine() {
        lines.add(StringUtils.EMPTY);
    }

    private PropertiesFileGenerator() {
        // utility class constructor
    }

}
