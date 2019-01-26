package info.novatec.testit.webtester.waiting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaitingActionTest {

    @Test
    void doesntAllowNullForActionCondition() {
        assertThrows(NullPointerException.class, () -> new WaitingAction(null, null));
    }

    @Test
    void doNothingReturnsFalseAndNull() {
        WaitingAction result = WaitingAction.doNothing();
        assertAll(
                () -> assertFalse(result.getActionCondition().get()),
                () -> assertNull(result.getAction())
        );
    }
}
