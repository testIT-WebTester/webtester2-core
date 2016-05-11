[Home](../README.md)

# Ad-Hoc Finding API

It is not always the best solution to declare page fragments via public `@IdentifyUsing` method.
Sometimes it is necessary to find a certain fragment programmatically:

- Maybe the fragment is only used in very special cases and should therefore not be public...
- Maybe you need a list of fragments fitting certain parameters which can not be expressed with CSS or XPath..
- Or maybe are just prototyping your approach and don't want to implement page objects, yet...

For these cases the Ad-Hoc finding API was developed.
This API can be accessed through a `Browser`, `Page` or a `PageFragment`.
Depending on where the API is accessed the search context for fragments might differ:

- `Browser`: The whole HTML page is searched for the fragment.
- `Page`: The whole HTML page is searched for the fragment.
- `PageFragment`: The area between the page fragment's open and close tags is searched for the fragment. 

There are several ways to start finding fragments, here are a few examples:

```java
// find an element by it's ID 'fooId' as a generic element 
GenericElement element = getBrowser()
    .find("#fooId");

// find many elements by their shared CSS class 'foo' as a stream of generic elements
Stream<GenericElement> elements = getBrowser()
    .findMany(".foo");
    
// find an element by it's ID 'textField' as a text field (identifier first)
TextField textField = getBrowser()
    .findBy(id("textField"))
    .as(TextField.class);
    
// find an element by it's ID 'textField' as a text field (class first)
TextField textField = getBrowser()
    .find(TextField.class)
    .by(id("textField"));

// find all all elements with the CSS class 'foo'
// within an element with ID 'group' as a stream of text fields
Stream<TextField> textFields = getBrowser()
    .find("#group")
    .findBy(css(".foo"))
    .asMany(TextField.class);
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
- [Generic Elements](generic-element.md)
- [ByProducers](by-producers.md)
- [Conditions](conditions.md)
