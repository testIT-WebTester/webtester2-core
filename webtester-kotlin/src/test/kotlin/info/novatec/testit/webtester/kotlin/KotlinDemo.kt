package info.novatec.testit.webtester.kotlin

import info.novatec.testit.webtester.browser.factories.ChromeFactory
import info.novatec.testit.webtester.conditions.Condition
import info.novatec.testit.webtester.conditions.pagefragments.Visible
import info.novatec.testit.webtester.kotlin.pagefragments.PageFragment
import info.novatec.testit.webtester.kotlin.pages.Page
import info.novatec.testit.webtester.pagefragments.Link
import info.novatec.testit.webtester.pagefragments.TextField
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil
import info.novatec.testit.webtester.pagefragments.identification.ByProducers.name
import info.novatec.testit.webtester.waiting.Wait
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class KotlinDemo {

    companion object {

        private var browser = ChromeFactory().createBrowser()

        @JvmStatic
        @AfterAll
        fun closeBrowser() = browser.close()

    }

    @Test
    @Disabled
    fun allKindsOfStuff() {
        browser.open("http://github.com")
                .create(StartPage::class.java)
                .search("webtester2-core")
                .selectRepositoryWithName("testit-webtester/webtester2-core")
                .openReleases()
        Wait.exactly(2, TimeUnit.SECONDS)
    }

}

interface HasSearchWidget : Page {

    fun search(query: String): SearchResultPage {
        find(TextField::class)
                .by(name("q"))
                .setText(query)
                .pressEnter()
        return create(SearchResultPage::class)
    }

}

interface GitHubPage : info.novatec.testit.webtester.pages.Page, HasSearchWidget

interface StartPage : GitHubPage

interface SearchResultPage : GitHubPage {

    @WaitUntil(Visible::class)
    @IdentifyUsing(".repo-list")
    fun repositoryList(): RepositoryList

    fun selectRepositoryWithName(repositoryName: String): RepositoryCodePage {
        return repositoryList()
                .selectRepositoryWithName("testit-webtester/webtester2-core")
    }

}

interface RepositoryList : PageFragment {

    @IdentifyUsing("h3 a")
    fun repositories(): Stream<Link>

    fun selectRepositoryWithName(name: String): RepositoryCodePage {
        findRepositoryWithName(name)
                .click()
        return create(RepositoryCodePage::class)
    }

    fun findRepositoryWithName(name: String): Link {
        return repositories()
                .filter { name.equals(it.visibleText, ignoreCase = true) }
                .findFirst()
                .orElseThrow { IllegalStateException("repository $name not found") }
    }

}

interface RepositoryCodePage : GitHubPage {

    @WaitUntil(Visible::class)
    @IdentifyUsing(".numbers-summary")
    fun summaryBar(): SummaryBar

    fun openReleases(): GitHubPage {
        summaryBar()
                .clickReleases()
        return create(GitHubPage::class)
    }

    interface SummaryBar : PageFragment {

        @IdentifyUsing("li a")
        fun summaryEntries(): List<Link>

        fun clickReleases(): GitHubPage {
            summaryEntries()[2].click()
            return create(GitHubPage::class)
        }

    }

}
