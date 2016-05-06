package info.novatec.testit.webtester.pagefragments;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "input", attribute = "type", values = { "email" })
public interface EmailField extends GenericTextField<EmailField> {
}
