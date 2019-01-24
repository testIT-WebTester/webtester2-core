package info.novatec.testit.webtester.waiting;

/**
 * Action interface (SAM) based on the {@link Runnable} interface.
 * It is not intended to be directly implemented but instead used to run a lambda provieded method.
 *
 * <b>Example usage:</b>
 * <pre>
 *
 * public void preparingMethod() {
 *     executingMethod(() -> doSomething());
 * }
 *
 * public void executingMethod(UIAction action) {
 *     action.perform();
 * }
 *
 * </pre>
 *
 * @see WaitingAction
 * @see Waiter
 * @since 2.8
 */
@FunctionalInterface
public interface WaitAction {

    /**
     * SAM for execution.
     *
     * @since 2.8
     */
    void perform();
}
