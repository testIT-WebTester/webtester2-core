package info.novatec.testit.webtester.pagefragments.identification;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;


public class CssSelectorUtilsTest {

    @Test
    public void realisticExample() {
        assertThat(CssSelectorUtils.escape("table:form:selection[0]")).isEqualTo("table\\:form\\:selection\\[0\\]");
    }

    @Test
    public void allRelevantSpecialCharactersAreEscaped() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(CssSelectorUtils.escape("!")).isEqualTo("\\!");
        softly.assertThat(CssSelectorUtils.escape("\"")).isEqualTo("\\\"");
        softly.assertThat(CssSelectorUtils.escape("#")).isEqualTo("\\#");
        softly.assertThat(CssSelectorUtils.escape("$")).isEqualTo("\\$");
        softly.assertThat(CssSelectorUtils.escape("%")).isEqualTo("\\%");
        softly.assertThat(CssSelectorUtils.escape("&")).isEqualTo("\\&");
        softly.assertThat(CssSelectorUtils.escape("'")).isEqualTo("\\'");
        softly.assertThat(CssSelectorUtils.escape("(")).isEqualTo("\\(");
        softly.assertThat(CssSelectorUtils.escape(")")).isEqualTo("\\)");
        softly.assertThat(CssSelectorUtils.escape("*")).isEqualTo("\\*");
        softly.assertThat(CssSelectorUtils.escape("+")).isEqualTo("\\+");
        softly.assertThat(CssSelectorUtils.escape(",")).isEqualTo("\\,");
        softly.assertThat(CssSelectorUtils.escape("-")).isEqualTo("\\-");
        softly.assertThat(CssSelectorUtils.escape(".")).isEqualTo("\\.");
        softly.assertThat(CssSelectorUtils.escape("/")).isEqualTo("\\/");
        softly.assertThat(CssSelectorUtils.escape(":")).isEqualTo("\\:");
        softly.assertThat(CssSelectorUtils.escape(";")).isEqualTo("\\;");
        softly.assertThat(CssSelectorUtils.escape("<")).isEqualTo("\\<");
        softly.assertThat(CssSelectorUtils.escape("=")).isEqualTo("\\=");
        softly.assertThat(CssSelectorUtils.escape(">")).isEqualTo("\\>");
        softly.assertThat(CssSelectorUtils.escape("?")).isEqualTo("\\?");
        softly.assertThat(CssSelectorUtils.escape("@")).isEqualTo("\\@");
        softly.assertThat(CssSelectorUtils.escape("[")).isEqualTo("\\[");
        softly.assertThat(CssSelectorUtils.escape("\\")).isEqualTo("\\\\");
        softly.assertThat(CssSelectorUtils.escape("]")).isEqualTo("\\]");
        softly.assertThat(CssSelectorUtils.escape("^")).isEqualTo("\\^");
        softly.assertThat(CssSelectorUtils.escape("`")).isEqualTo("\\`");
        softly.assertThat(CssSelectorUtils.escape("{")).isEqualTo("\\{");
        softly.assertThat(CssSelectorUtils.escape("|")).isEqualTo("\\|");
        softly.assertThat(CssSelectorUtils.escape("}")).isEqualTo("\\}");
        softly.assertThat(CssSelectorUtils.escape("~")).isEqualTo("\\~");
        softly.assertAll();
    }

}
