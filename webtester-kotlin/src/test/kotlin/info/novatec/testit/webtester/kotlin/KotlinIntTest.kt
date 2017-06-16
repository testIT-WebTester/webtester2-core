package info.novatec.testit.webtester.kotlin

import info.novatec.testit.webtester.pagefragments.TextField
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import info.novatec.testit.webtester.kotlin.pages.Page
import utils.integration.BaseIntTest
import javax.annotation.PostConstruct

class KotlinIntTest : BaseIntTest() {

    override fun getHTMLFilePath(): String = "html/testKotlin.html"

    @Test
    fun `basic WebTester functions are working`() {
        val page = create(TestKotlinPage::class.java)
        assertThat(page.getValue()).isEqualTo("did something")
        page.setValue("Foo Bar?")
        assertThat(page.getValue()).isEqualTo("Foo Bar?")
    }

    interface TestKotlinPage : Page {

        @IdentifyUsing("#emptyTextField")
        fun textField(): TextField

        @PostConstruct
        fun doSomething() {
            textField().text = "did something"
        }

        fun setValue(value: String) {
            textField().text = value
        }

        fun getValue(): String {
            return textField().text
        }

    }

}
