package info.novatec.testit.webtester.pagefragments.identification;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


/**
 * Implementations of this interface are used to create {@link By} instances from Strings.
 * <p>
 * These Bys are used to identify WebElements with Selenium. The main usage of sub classes of this interface is in
 * combination with the {@link IdentifyUsing @IdentifyUsing} annotation where they are used to define the method of how a
 * {@link PageFragment page fragment} is identified.
 *
 * @see By
 * @see IdentifyUsing
 * @see PageFragment
 * @since 2.0
 */
public interface ByProducer {

    /**
     * Create a new {@link By} from the given String.
     *
     * @param value the string to interpret
     * @return the created By
     * @see By
     * @see IdentifyUsing
     * @see PageFragment
     * @since 2.0
     */
    By createBy(String value);

}
