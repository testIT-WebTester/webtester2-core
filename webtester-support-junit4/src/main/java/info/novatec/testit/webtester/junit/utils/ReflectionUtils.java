package info.novatec.testit.webtester.junit.utils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ReflectionUtils {

    private final Map<Class<?>, Set<Field>> fieldsCache = new ConcurrentHashMap<>();

    /**
     * Returns all fields of the given class hierarchy (start class and all its
     * super types). This method uses a cache, so each class hierarchy is only
     * resolved once;
     *
     * @param startClass the class of which all fields in the hierarchy should
     * be returned
     * @return all fields of the given class hierarchy
     */
    public Set<Field> getAllFieldsOfClassHierarchy(Class<?> startClass) {

        Set<Field> fields = fieldsCache.get(startClass);
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

        fieldsCache.put(startClass, fields);
        return fields;

    }

}
