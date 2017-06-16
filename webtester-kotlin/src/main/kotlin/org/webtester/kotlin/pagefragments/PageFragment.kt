package org.webtester.kotlin.pagefragments

import info.novatec.testit.webtester.adhoc.TypeFinder
import info.novatec.testit.webtester.internal.OffersAdHocFinding
import info.novatec.testit.webtester.internal.OffersPageCreation
import info.novatec.testit.webtester.pagefragments.PageFragment
import info.novatec.testit.webtester.pages.Page
import kotlin.reflect.KClass

/**
 * Kotlin alias for [PageFragment] interface.
 *
 * Provides additional method for Kotlin compatibility.
 * @since 2.3
 */
interface PageFragment : PageFragment {

    /**
     * Alias for [find(Class fragmentClass)][OffersAdHocFinding.find] in [PageFragment] changing the signature to accept
     * [KClass] instances.
     * @see OffersAdHocFinding.find
     * @since 2.3
     */
    fun <T : PageFragment> find(fragmentClass: KClass<T>): TypeFinder<T> {
        return finder().find(fragmentClass.java)
    }

    /**
     * Alias for [create(Class pageClass)][OffersPageCreation.create] in [PageFragment] changing the signature to accept
     * [KClass] instances.
     * @see OffersPageCreation.create
     * @since 2.3
     */
    fun <T : Page> create(pageClass: KClass<T>): T {
        return browser().create(pageClass.java)
    }

}