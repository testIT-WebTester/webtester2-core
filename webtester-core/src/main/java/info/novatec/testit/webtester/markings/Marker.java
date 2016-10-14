package info.novatec.testit.webtester.markings;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.Color;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.css.CssProperties;
import info.novatec.testit.webtester.css.StyleChanger;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This class is used to mark {@link PageFragment page fragment} instances with the colors defined in the page object's
 * browser's {@link Configuration configuration}.
 *
 * @see StyleChanger
 * @see PageFragment
 * @see CssProperties
 * @since 2.0
 */
@Slf4j
@AllArgsConstructor
public class Marker {

    private final StyleChanger styleChanger;

    /**
     * Marks the given {@link PageFragment page fragment} as 'read' using the configured colors from the page object's
     * browser's {@link Configuration configuration}.
     *
     * @param fragment the page fragment to mark.
     * @see StyleChanger
     * @see PageFragment
     * @see CssProperties
     * @since 2.0
     */
    public void markAsRead(PageFragment fragment) {
        Configuration configuration = fragment.browser().configuration();
        if (configuration.isMarkingsEnabled()) {
            Color backgroundColor = configuration.getMarkingsColorReadBackground();
            Color outlineColor = configuration.getMarkingsColorReadOutline();
            markElement(fragment, backgroundColor, outlineColor);
            log.debug("marked {} as read", fragment);
        }
    }

    /**
     * Marks the given {@link PageFragment page fragment} as 'used' using the configured colors from the page object's
     * browser's {@link Configuration configuration}.
     *
     * @param fragment the page fragment to mark.
     * @see StyleChanger
     * @see PageFragment
     * @see CssProperties
     * @since 2.0
     */
    public void markAsUsed(PageFragment fragment) {
        Configuration configuration = fragment.browser().configuration();
        if (configuration.isMarkingsEnabled()) {
            Color backgroundColor = configuration.getMarkingsColorUsedBackground();
            Color outlineColor = configuration.getMarkingsColorUsedOutline();
            markElement(fragment, backgroundColor, outlineColor);
            log.debug("marked {} as used", fragment);
        }
    }

    private void markElement(PageFragment fragment, Color backgroundColor, Color outlineColor) {

        Map<String, String> cssStyleAttributes = new HashMap<>();
        cssStyleAttributes.put(CssProperties.OUTLINE_STYLE, "solid");
        cssStyleAttributes.put(CssProperties.OUTLINE_WIDTH, "2px");
        cssStyleAttributes.put(CssProperties.OUTLINE_COLOR, outlineColor.asHex());
        cssStyleAttributes.put(CssProperties.BACKGROUND_COLOR, backgroundColor.asHex());

        styleChanger.changeStyleInformation(fragment, cssStyleAttributes);

    }

}
