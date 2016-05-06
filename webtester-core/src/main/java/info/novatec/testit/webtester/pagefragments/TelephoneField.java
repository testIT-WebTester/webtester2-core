package info.novatec.testit.webtester.pagefragments;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "input", attribute = "type", values = { "tel" })
public interface TelephoneField extends GenericTextField<TelephoneField> {
}
