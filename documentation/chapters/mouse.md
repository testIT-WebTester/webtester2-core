[Home](../README.md)

# The Mouse Utility Class
The `Mouse` utility class contains all kinds of methods which allow you to use or at least simulate the use
(depending on the `WebDriver` implementation) of mouse actions. These are the currently implemented mouse actions:

- `click(PageFragment)`
- `doubleClick(PageFragment)`
- `contextClick(PageFragment)`
- `moveTo(PageFragment)`
- `moveToEach(PageFragment, PageFragment...)`
- `moveToEach(Collection<PageFragment>)`

## Mouse.click()
Executes a click on the given `PageFragment` by first moving the mouse to the center of it.

## Mouse.doubleClick()
Executes a double click on the given `PageFragment` by first moving the mouse to the center of it.

## Mouse.contextClick()
Executes a context click on the given `PageFragment` by first moving the mouse to the center of it.

## Mouse.moveToEach()
Moves the mouse to each of the given `PageFragment`s in turn. The page fragments have to be visible in order to move
the mouse to it. This method can be used to navigate dynamically displayed menu structures because it waits for
each page fragment to be displayed before moving the mouse to it.

## Mouse.moveTo()
Moves the mouse to the given `PageFragment`. The page fragment has to be visible in order to move the mouse to it.

## Examples
```java
// clicks a button
Mouse.click(button);
 
// double clicks an image
Mouse.doubleClick(image);
 
// moves the mouse to the link
Mouse.moveTo(link);
 
// moves the mouse to each link as they appear
Mouse.moveToEach(fileMenu, fileMenuNew, fileMenuNewPage);
```

# Fluent API for Mouse Actions
In addition to these single actions the `Mouse` utility class provides several methods for execution a number of actions 
with a fluent syntax:

- `on(PageFragment)`
- `to(PageFragment)`
- `sequence()`

## Mouse.on()
Provides methods which can be executed on the given `PageFragment`.

## Mouse.to()
Provides methods which can be executed to the given `PageFragment`.

## Mouse.sequence()
Provides methods which can be executed in sequence.

## Examples
```java
// actions on fragment
Mouse.on(button).click();
Mouse.on(button).doubleClick();
Mouse.on(button).contextClick();
 
// actions to fragments
Mouse.to(image).move();
 
// sequence
Mouse.sequence().moveTo(image).click();
Mouse.sequence().moveTo(image).moveTo(otherImage).click();
Mouse.sequence().click(image).doubleClick(otherImage);
```

# Linked Documentation

- [Page Fragments](page-fragment.md)
