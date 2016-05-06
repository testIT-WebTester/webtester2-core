package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "iframe")
public interface IFrame extends PageFragment {

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

}
