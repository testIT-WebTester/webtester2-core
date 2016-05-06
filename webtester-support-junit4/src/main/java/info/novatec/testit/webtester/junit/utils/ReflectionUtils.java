package info.novatec.testit.webtester.junit.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public final class ReflectionUtils {

    private static final Map<Class<?>, Set<Field>> FIELDS_CACHE = new HashMap<>();

    /* getting fields */

    /**
     * Returns all fields of the given class hierarchy (start class and all its
     * super types). This method uses a cache, so each class hierarchy is only
     * resolved once;
     *
     * @param startClass the class of which all fields in the hierarchy should
     * be returned
     * @return all fields of the given class hierarchy
     */
    public static Set<Field> getAllFieldsOfClassHierarchy(Class<?> startClass) {

        Set<Field> fields = FIELDS_CACHE.get(startClass);
        if (fields != null) {
            return fields;
        }

        Field[] declaredFields = startClass.getDeclaredFields();

        fields = new HashSet<>(declaredFields.length);
        Collections.addAll(fields, declaredFields);

        Class<?> superclass = startClass.getSuperclass();
        if (superclass != null) {
            fields.addAll(getAllFieldsOfClassHierarchy(superclass));
        }

        FIELDS_CACHE.put(startClass, fields);
        return fields;

    }

    private ReflectionUtils() {
    }

}
