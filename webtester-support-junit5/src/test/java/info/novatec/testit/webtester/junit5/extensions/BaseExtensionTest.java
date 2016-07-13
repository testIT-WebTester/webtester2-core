package info.novatec.testit.webtester.junit5.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class BaseExtensionTest {

    BaseExtension cut = new BaseExtension() {
    };

    @Test
    @DisplayName("allFieldsOfClassHierarchy(Class) returns all fields of class hierarchy in order of inheritance")
    void allFieldsOfClassHierarchyForClass() {

        List<String> fieldNames =
            cut.allFieldsOfClassHierarchy(ChildClass.class).map(Field::getName).collect(Collectors.toList());

        List<String> expectedNames = new LinkedList<>();

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

        assertThat(fieldNames).isEqualTo(expectedNames);

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
