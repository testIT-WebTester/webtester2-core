[Home](../README.md)

# Pages

## The Page Object Pattern
The WebTester framework's architecture and design is based around the Page Object Pattern.
For more information about Page Object Pattern see:
- [Martin Fowler on Page Objects](http://martinfowler.com/bliki/PageObject.html)
- [Selenium Wiki Article](https://code.google.com/p/selenium/wiki/PageObjects)

## Page
The `Page` class is the parent for all pages used to implement the Page Object Pattern.
It provides base methods every page will need.

Pages can be initialized using the `create(pageClass)` method of a `Browser` instance or from within another page.

### Example
The following example represents an application with two pages.
On each page the same navigation menu is displayed.
The page's content differs from page to page.
This demonstrates composition of page information.

```java
// the navigation menu widget
public interface NavigationMenu extends PageFragment {
 
    @IdentifyUsing("#firstLink")
    Link firstLink();
 
    @IdentifyUsing("#secondLink")
    Link secondLink();
 
}

// a 'trait' interface declaring the property of having a navigation menu
public interface HasNavigationMenu {
 
    /* The navigation menu is identified by its ID
     * This automatically limits the search scope for the navigation menu's fragments to 
     * everything contained inside the tag with the ID "navMenu" */
    @IdentifyUsing("#navMenu")
    NavigationMenu navigation();
 
}
 
// a page containing a table with the ID "fooTable" and inheriting the navigation menu trait
public interface FooPage extends Page, HasNavigationMenu {
     
    @IdentifyUsing("#fooTable")
    Table fooTable();
 
    ...
 
}
 
// a page containing a table with the ID "barTable" and inheriting the navigation menu trait
public interface BarPage extends Page, HasNavigationMenu {
 
    @IdentifyUsing("#barTable")
    Table fooTable();
 
    ...
 
}
```

## Page Fragments
As can be seen in the previous example a page consists of different `PageFragment`s.
The fragments can be accessed / initialized by invoking a method annotated with `@IdentifyUsing`.
For mor information about page fragments see [Chapter: Page Fragment](page-fragment.md).

## Collections of Page Fragments
Multiple page fragments of a page can be retrieved as a `Set`, `List` or `Stream` by simply declaring any of those 
as the return type of a `@IdentifyUsing` annotated method:

**Example:**
```java
public interface CollectionPage extends Page {

    @IdentifyUsing(".text-field")
    List<TextField> textFieldList();

    @IdentifyUsing(".text-field")
    Set<TextField> textFieldSet();

    @IdentifyUsing(".text-field")
    Stream<TextField> textFieldStream();

}
```

## Relevant Annotations
These annotations can be used within a `Page`.

- [@Action](annotation-action.md)
- [@Cached](annotation-cached.md)
- [@IdentifyUsing](annotation-identify-using.md)
- [@Named](annotation-named.md)
- [@PostConstruct](annotation-post-construct.md)
- [@PostConstructMustBe](chapters/annotation-post-construct-must-be.md)
- [@WaitUntil](annotation-wait-until.md)

# Anatomy of a Page 
Pages generally provide four kinds of methods:

- Actions
- Navigations
- Workflows
- Information Getter

## Actions
Actions are methods which change the state of a page without leaving it. This could be the input of text in a text field or 
the selection of a value in a select menu.
   
Rules:
   
- Method name represents an action: "setUsername", "changeDataOfBirth" etc.
- Method returns the same instance of the Page for fluent API support.
- Method does not change multiple states.

```java
public LoginPage setFirstName(String name){
    firstName.setText(name);
    return this;
}
 
public LoginPage selectBirthMonth(String month){
    birthMonth.selectByText(month);
    return this;
}
```

## Navigations
Navigations are methods which execute an action that leads to a page change. This could be the click of a link or the direct 
opening of an URL. The difference between navigations and actions is, that a navigation has to declare what page comes "next". 
This means that for a single navigation "action" there might me multiple methods. I.g. this is necessary to declare "bad case" 
paths through the application. E.g. if a login fails or a process could not be finished.
   
Rules:
   
- Method name represents an action: "clickLogin", "clickLoginExpectingError" etc.
- Method returns a new instance of the target page's Page for fluent API support.
- Method does not change multiple states.

```java
public MainPage clickLogin(){
    login.click();
    return create(MainPage.class);
}
 
public LoginPage clickLoginExpectingError(){
    login.click();
    return create(LoginPage.class);
}
```

## Workflows
Workflows combine different methods in order to allow for "fast" navigation over pages. I.g. they combine a set of actions 
with a navigation. This could e.g. be a single method to log into a system.
   
Rules:
   
- Method name represents a process: "login", "register" etc.
- Method's return type and value depends on the last command in the workflow: Is it an action or a navigation?
- Method does change multiple states.

```java
public MainPage login(User user){
    return setUsername(user.getUsername())
        .setPassword(user.getPassword())
        .clickLogin();
}
 
public LoginPage loginExpectingError(User user){
    return setUsername(user.getUsername())
        .setPassword(user.getPassword())
        .clickLoginExpectingError();
}
```

## Information Getter
Information getter are methods which retrieve information from a page. This could be the text of a displayed error 
message or the content of a certain text field.
  
Rules:
  
- Method name represents a request: "getErrorMessages", "getNumberOfDisplayedTableEntries" etc.
- Method's return type might be anything but a Page.
- Method does not change any states.

```java
public String getErrorMessage () {
    return errorMessage.getText();
}
 
public int getNumberOfSearchResults () {
    int counter = 0;
    // some logic
    return counter;
}
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
