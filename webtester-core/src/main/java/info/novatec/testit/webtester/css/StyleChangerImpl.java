package info.novatec.testit.webtester.css;

import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriverException;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.pagefragments.PageFragment;

@Slf4j
public class StyleChangerImpl implements StyleChanger {

    @Override
    public boolean changeStyleInformation(PageFragment pageFragment, String property, String value) {
        return changeStyleWithScript(pageFragment, scriptCommand(property, value));
    }

    @Override
    public boolean changeStyleInformation(PageFragment pageFragment, Map<String, String> cssStyleProperties) {
        return changeStyleWithScript(pageFragment, buildScriptCommands(cssStyleProperties));
    }

    private boolean changeStyleWithScript(PageFragment fragment, String script) {
        try {
            fragment.getBrowser().javaScript().execute(script, fragment);
            return true;
        } catch (WebDriverException e) {
            log.warn("Exception while changing the style information of an page object: {}", e.getMessage());
            log.debug("Stack trace for previous warning:", e);
        }
        return false;
    }

    private String buildScriptCommands(Map<String, String> cssStyleProperties) {
        return cssStyleProperties.entrySet().stream()
            .map(entry -> scriptCommand(entry.getKey(), entry.getValue()))
            .collect(Collectors.joining());
    }

    private String scriptCommand(String property, String value) {
        return String.format("arguments[0].style.%s='%s';", property, value);
    }

}
