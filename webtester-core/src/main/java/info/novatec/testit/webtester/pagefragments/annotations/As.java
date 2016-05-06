package info.novatec.testit.webtester.pagefragments.annotations;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.markings.Marker;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public enum As {

    /**
     * Marks the {@link PageFragment} as 'read'.
     * This is generally used for methods which represent human actions that would qualify as reading.
     * I.e. <code>getText()</code>, <code>isSelected()</code> etc.
     *
     * @see Mark
     * @see Marker
     * @see Configuration#getMarkingsColorReadBackground()
     * @see Configuration#getMarkingsColorReadOutline()
     * @since 2.0
     */
    READ,

    /**
     * Marks the {@link PageFragment} as 'used'.
     * This is generally used for methods which represent human actions that would change the state of a fragment.
     * I.e. <code>setText(...)</code>, <code>select()</code> etc.
     *
     * @see Mark
     * @see Marker
     * @see Configuration#getMarkingsColorUsedBackground()
     * @see Configuration#getMarkingsColorUsedOutline()
     * @since 2.0
     */
    USED

}
