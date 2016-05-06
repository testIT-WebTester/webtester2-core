[Home](../README.md)

# Event System

## EventSystem
The `EventSystem` is a service providing methods for firing and listening for `Events`.
`EventListener` instances can be registered at the system as well as deregistered once they are no longer needed.
Each `Browser` instance has it's own instance of `EventSystem`

## Event
An `Event` contains all the information needed to understand what happened.
Since it is an interface implementing a custom event is very easy.

In general it is recommended to treat events as data objects.
They should not contain references to services or larger parts of the system.

The `webtester-core` module provides events for all it's actions:

**Events from Browser:**

- `AcceptedAlertEvent`
- `ClosedBrowserEvent`
- `ClosedWindowEvent`
- `DeclinedAlertEvent`
- `MaximizedWindowEvent`
- `NavigatedBackwardsEvent`
- `NavigatedForwardsEvent`
- `OpenedUrlEvent`
- `RefreshedPageEvent`
- `SavedSourceCodeEvent`
- `SetWindowPositionEvent`
- `SetWindowSizeEvent`
- `SwitchedToDefaultContentEvent`
- `SwitchedToFrameEvent`
- `SwitchedToWindowEvent`
- `TookScreenshotEvent`

**Events from Page Fragments:**

- `ClearedEvent`
- `ClickedEvent`
- `ContextClickedEvent`
- `DeselectedAllEvent`
- `DeselectedByIndicesEvent`
- `DeselectedByTextsEvent`
- `DeselectedByValuesEvent`
- `DoubleClickedEvent`
- `DraggedAndDroppedEvent`
- `EnterPressedEvent`
- `FormSubmittedEvent`
- `NumberSetEvent`
- `SelectedByIndexEvent`
- `SelectedByIndicesEvent`
- `SelectedByTextEvent`
- `SelectedByTextsEvent`
- `SelectedByValueEvent`
- `SelectedByValuesEvent`
- `SelectionChangedEvent`
- `TextAppendedEvent`
- `TextSetEvent`

## EventListener
An `EventListener` is a simple interface providing a single method `void eventOccurred(Event event);`.
Instances of this interface can be registered at the `EventSystem` in order to be called every time an `Event` is fired.

## Configuration
The firing of events can be disabled by setting the `events.enabled` property to `false`. This will disable the firing of 
all events except `ExceptionEvent` instances fired by using 'EventSystem#fireExceptionEvent(e)'.

## Examples
```java
private static EventListener customListener;
 
@BeforeClass
public static void registerEventListener () {
    customListener = (event) -> System.out.println(event); 
    browser.events().register(customListener);
}
 
@AfterClass
public static void deregisterEventListener () {
    browser.events().deregister(customListener);
}
```
