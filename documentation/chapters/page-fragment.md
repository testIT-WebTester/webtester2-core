[Home](../README.md)

# Page Fragments
Page fragments are parts of a `Page`. They extend the `PageFragment` interface and represent any number of thing. From a 
single text field to a widget. WebTester provides a number of functional page fragments out of the box. These map more or 
less to HTML elements:

- `Button`
- `Checkbox`
- `Div`
- `EmailField`
- `Form`
- `Headline`
- `IFrame`
- `Image`
- `Link`
- `ListItem`
- `MultiSelect`
- `NumberField`
- `OrderedList`
- `Paragraph`
- `PasswordField`
- `RadioButton`
- `SearchField`
- `SingleSelect`
- `Span`
- `Table`
- `TableField`
- `TableRow`
- `TelephoneField`
- `TextArea`
- `TextField`
- `UnorderedList`
- `UrlField`
- `GenericElement`
- `GenericList`
- `GenericSelect`
- `GenericTextField`

The `Button` for example is mapped to `<button/>`, `<input type="reset">`, `<input type="submit">`
and `<input type="button">` while the `SingleSelect` maps to a very specific type of `<select>` (where the `multiple` 
attribute is not set).

In contrast to the generic interactions offered by Selenium's `WebElement` interface these _functional_ classes provide
only those methods which are useful for the given context / their type. A `SingleSelect` does not provide methods to 
change its text, but it will have methods to change selection based on index, value or text.

## Validation of Page Fragments
By default a `PageFragment` will match any HTML tag in form of a WebElement. Functional page fragments on the other hand are 
limited to a certain amount of valid HTML tags and attribute combinations. This is done by annotating them with
```@Mapping``` for a single mapping. This annotation can be used multiple times in case there is more then one valid 
combination.

Annotating any page fragment with ```@Mapping``` will trigger a validation logic anytime the underlying web element is 
resolved. In case the validation fails a ```MappingException``` is thrown.

### @Mapping
The ```@Mapping``` annotation is used to define a valid combination of tag, attribute and attribute values of a web 
element to be used with a page fragment class.

**There are a number of different ways to use this:**

* ```@Mapping(tag="div")``` Will be evaluated as 'valid' in case the web element has the tag 'div'.
* ```@Mapping(tag="select", attribute="multiple")``` Will be evaluated as 'valid' in case the web element has the tag 
'select' and the 'multiple' attribute is
present.
* ```@Mapping(tag="select", attribute="!multiple")``` Will be evaluated as 'valid' in case the web element has the tag 
'select' and the 'multiple' attribute is not present.
* ```@Mapping(tag="input", attribute="type", values={"text", "password"})``` Will be evaluated as 'valid' in case the web 
element has the tag 'input' and the 'type' attribute has either the 'text' oder 'password' value.
* ```@Mapping(validator=FooValidator.class)``` Will create a new instance of the given validator class and use it to 
evaluate the web element.

## Example

```java
public interface SearchWidget extends PageFragment {

    @IdentifyUsing("#query")
    SearchField query();
     
    @IdentifyUsing("#submit")
    Button submit();

}
```

## Relevant Annotations
These annotations can be used within a `PageFragment`.

- [@Action](annotation-action.md)
- [@Attribute](annotation-attribute.md)
- [@IdentifyUsing](annotation-identify-using.md)
- [@Mark](annotation-mark.md)
- [@Named](annotation-named.md)
- [@PostConstruct](annotation-post-construct.md)
- [@PostConstructMustBe](annotation-post-construct-must-be.md)
- [@WaitUntil](annotation-wait-until.md)

# Linked Documentation

- [Pages](page.md)
