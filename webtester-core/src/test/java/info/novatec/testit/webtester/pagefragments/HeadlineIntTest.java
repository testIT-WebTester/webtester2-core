package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.mapping.MappingException;


class HeadlineIntTest extends BaseIntTest {

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/headline.html";
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = { "h1", "h2", "h3", "h4", "h5", "h6" })
    void validMappingTypes(String cssSelector) {
        Headline headline = browser().find(Headline.class).by("#" + cssSelector);
        assertThat(headline.isPresent()).isTrue();
    }

    @Test
    void nonHeadlineElementIsInvalidMapping() {
        Headline headline = browser().find(Headline.class).by("#span");
        assertThrows(MappingException.class, headline::webElement);
    }

}
