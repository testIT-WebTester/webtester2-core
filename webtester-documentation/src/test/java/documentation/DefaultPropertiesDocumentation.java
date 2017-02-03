package documentation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.internal.configuration.DefaultValue;
import info.novatec.testit.webtester.internal.configuration.Documentation;
import info.novatec.testit.webtester.internal.configuration.NamedProperties;
import info.novatec.testit.webtester.internal.configuration.TypeDefinition;


@Slf4j
public class DefaultPropertiesDocumentation {

    private static final String TYPE_LABEL = "TYPE: ";
    private static final String EQUALS = " = ";
    private static final String OUTCOMMENTED = "# ";

    private List<String> lines = new ArrayList<>();

    @Test
    public void generateDefaultPropertiesSnippet() throws URISyntaxException, NoSuchFieldException, IOException {
        addEmptyLine();
        addLine("[source, properties]");
        addLine("----");
        addPropertiesContent();
        addLine("----");
        addEmptyLine();
        createSnippetFile();
    }

    private void addPropertiesContent() throws NoSuchFieldException {
        for (NamedProperties properties : NamedProperties.values()) {
            addPropertyDefinition(properties);
            addEmptyLine();
        }
    }

    private void addPropertyDefinition(NamedProperties properties) throws NoSuchFieldException {
        Field field = NamedProperties.class.getField(properties.name());
        Documentation documentationAnnotation = field.getAnnotation(Documentation.class);
        TypeDefinition typeDefinitionAnnotation = field.getAnnotation(TypeDefinition.class);
        DefaultValue defaultValueAnnotation = field.getAnnotation(DefaultValue.class);
        if (documentationAnnotation != null) {
            commentLine(documentationAnnotation.value());
        }
        if (typeDefinitionAnnotation != null) {
            commentLine(TYPE_LABEL + typeDefinitionAnnotation.value());
        }
        if (defaultValueAnnotation == null) {
            commentLine(properties.getKey() + EQUALS);
        } else {
            String defaultValue = defaultValueAnnotation.value();
            String line = properties.getKey() + EQUALS + defaultValue;
            addLine(line);
        }
    }

    private void commentLine(String line) {
        String replacedLine = StringUtils.replace(line, "\n", "\n" + OUTCOMMENTED);
        lines.add(OUTCOMMENTED + replacedLine);
    }

    private void addLine(String line) {
        lines.add(line);
    }

    private void addEmptyLine() {
        lines.add(StringUtils.EMPTY);
    }

    private void createSnippetFile() throws URISyntaxException, IOException {
        File configSnippedFolder = getSnippetFolder();
        File propertiesFile = new File(configSnippedFolder, "default-configuration.snippet");
        FileUtils.writeLines(propertiesFile, lines);
    }

    private File getSnippetFolder() throws URISyntaxException {
        URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        File targetFolder = new File(location.toURI()).getParentFile();
        File generatedSnippetsFolder = new File(targetFolder, "generated-snippets");
        File configSnippedFolder = new File(generatedSnippetsFolder, "config");
        if (configSnippedFolder.mkdirs()) {
            log.info("Created directory: {}", configSnippedFolder);
        }
        return configSnippedFolder;
    }

}
