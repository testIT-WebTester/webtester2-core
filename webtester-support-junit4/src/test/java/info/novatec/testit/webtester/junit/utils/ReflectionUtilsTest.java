package info.novatec.testit.webtester.junit.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;


public class ReflectionUtilsTest {

    ReflectionUtils cut = new ReflectionUtils();

    @Test
    public final void testGetAllFieldsOfClassHierarchy() throws Exception {

        Field childField = Child.class.getDeclaredField("childField");
        Field fatherField = Father.class.getDeclaredField("fatherField");
        Field grandfatherField = Grandfather.class.getDeclaredField("grandfatherField");

        Set<Field> fields = cut.getAllFieldsOfClassHierarchy(Child.class);

        assertThat(fields).contains(childField, fatherField, grandfatherField);

    }

    public static class Child extends Father {

        String childField;

        public Child() {
        }

    }

    public static class Father extends Grandfather {

        String fatherField;

        public Father() {
        }

    }

    public static class Grandfather {

        String grandfatherField;

        public Grandfather() {
        }

    }

}
