package info.novatec.testit.webtester.pagefragments;

import java.io.File;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;

import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.traits.Clickable;


@Mapping(tag = "img")
public interface Image extends PageFragment, Clickable<Image> {

    /**
     * Returns the <code>src</code> attribute's value as an optional string.
     *
     * @return the optional value of the <code>src</code> attribute
     * @see Attribute
     * @see Optional
     * @since 2.0
     */
    @Attribute("src")
    Optional<String> getSourcePath();

    /**
     * Returns the <code>alt</code> attribute's value as an optional string.
     *
     * @return the optional value of the <code>alt</code> attribute
     * @see Attribute
     * @see Optional
     * @since 2.0
     */
    @Attribute("alt")
    Optional<String> getAlternateText();

    /**
     * Returns the <code>height</code> attribute's value as an integer.
     * The integer's unit is pixels.
     *
     * @return the value of the <code>height</code> attribute
     * @see Attribute
     * @since 2.0
     */
    @Attribute("height")
    Integer getHeight();

    /**
     * Returns the <code>width</code> attribute's value as an integer.
     * The integer's unit is pixels.
     *
     * @return the value of the <code>width</code> attribute
     * @see Attribute
     * @since 2.0
     */
    @Attribute("width")
    Integer getWidth();

    /**
     * Returns the optional file name of this image.
     * This might return an empty optional in case the image does not reference a file.
     *
     * @return the name of the image's file
     * @see #getSourcePath()
     * @see File#getName()
     * @since 2.0
     */
    default Optional<String> getFileName() {
        return getSourcePath().filter(StringUtils::isNotBlank).map(File::new).map(File::getName);
    }

}
