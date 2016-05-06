package info.novatec.testit.webtester.pagefragments;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "input", attribute = "type", values = { "password" })
public interface PasswordField extends GenericTextField<PasswordField> {
}
