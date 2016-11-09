package info.novatec.testit.webtester.junit5.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class ReflectionUtilsTest {

    ReflectionUtils cut = new ReflectionUtils();

    @Test
    @DisplayName("allFieldsOfClassLineage(Class) returns all fields of class hierarchy in order of inheritance")
    void allFieldsOfClassLineageForClass() {

        List<String> fieldNames =
            cut.allFieldsOfClassLineage(ChildClass.class).map(Field::getName).collect(Collectors.toList());

        List<String> expectedNames = new ArrayList<>();

        expectedNames.add("privateGrandParentField");
        expectedNames.add("protectedGrandParentField");
        expectedNames.add("publicGrandParentField");
        expectedNames.add("defaultGrandParentField");

        expectedNames.add("privateParentField");
        expectedNames.add("protectedParentField");
        expectedNames.add("publicParentField");
        expectedNames.add("defaultParentField");

        expectedNames.add("privateChildField");
        expectedNames.add("protectedChildField");
        expectedNames.add("publicChildField");
        expectedNames.add("defaultChildField");

        assertThat(fieldNames).containsSubsequence(toVararg(expectedNames));

    }

    private String[] toVararg(List<String> expectedNames) {
        return expectedNames.toArray(new String[expectedNames.size()]);
    }

    @Test
    @DisplayName("setValue(..) can change value of private fields")
    void instanceValuesCanBeSetForPrivateFields() throws NoSuchFieldException {
        ChildClass instance = new ChildClass();
        Field field = instance.getClass().getDeclaredField("privateChildField");
        cut.setValue(field, instance, "String");
        assertThat(instance.privateChildField).isEqualTo("String");
    }

    @Test
    @DisplayName("getValue(..) can get value of private fields")
    void instanceValuesCanBeGotFromPrivateFields() throws NoSuchFieldException {
        ChildClass instance = new ChildClass();
        instance.privateChildField = "String";
        Field field = instance.getClass().getDeclaredField("privateChildField");
        String value = cut.getValue(field, instance);
        assertThat(value).isEqualTo(instance.privateChildField);
    }

    private static class ChildClass extends ParentClass {

        private Object privateChildField;
        protected Object protectedChildField;
        public Object publicChildField;
        Object defaultChildField;

    }

    private static class ParentClass extends GrandParentClass {

        private Object privateParentField;
        protected Object protectedParentField;
        public Object publicParentField;
        Object defaultParentField;

    }

    private static class GrandParentClass {

        private Object privateGrandParentField;
        protected Object protectedGrandParentField;
        public Object publicGrandParentField;
        Object defaultGrandParentField;

    }

}
