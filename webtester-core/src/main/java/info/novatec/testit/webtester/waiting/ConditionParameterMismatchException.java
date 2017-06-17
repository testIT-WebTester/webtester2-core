package info.novatec.testit.webtester.waiting;

import info.novatec.testit.webtester.WebTesterException;
import info.novatec.testit.webtester.conditions.Condition;


public class ConditionParameterMismatchException extends WebTesterException {

    private static final String MESSAGE = "Condition '%s' can't handle parameter of type '%s'!";

    protected ConditionParameterMismatchException(Class<? extends Condition> conditionType, Class<?> parameterType,
        Throwable cause) {
        super(String.format(MESSAGE, conditionType, parameterType), cause);
    }

}
