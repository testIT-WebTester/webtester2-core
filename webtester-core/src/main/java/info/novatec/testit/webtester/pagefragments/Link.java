package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.traits.Clickable;


@Mapping(tag = "a")
public interface Link extends PageFragment, Clickable<Link> {

    /**
     * Returns the <code>href</code> attribute's value as an optional string.
     *
     * @return the optional value of the <code>href</code> attribute
     * @see Attribute
     * @see Optional
     * @since 2.0
     */
    @Attribute("href")
    Optional<String> getDestination();

    /**
     * Returns the <code>target</code> attribute's value as an optional string.
     *
     * @return the optional value of the <code>target</code> attribute
     * @see Attribute
     * @see Optional
     * @since 2.0
     */
    @Attribute("target")
    Optional<String> getTarget();

}
