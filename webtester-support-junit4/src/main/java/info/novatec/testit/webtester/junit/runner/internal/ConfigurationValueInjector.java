package info.novatec.testit.webtester.junit.runner.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.IllegalClassException;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;


public class ConfigurationValueInjector {

    private static final String UNINJECTABLE_FIELD_TYPE = "cannot inject configuration values into fields of type ";
    private static final Object STATIC_TARGET = null;

    private final Map<Class<?>, Injector> injectorMap = new HashMap<>();

    public ConfigurationValueInjector() {
        injectorMap.put(String.class,
            (config, key, field, target) -> field.set(target, config.getStringProperty(key).get()));
        injectorMap.put(Integer.class,
            (config, key, field, target) -> field.set(target, config.getIntegerProperty(key).get()));
        injectorMap.put(Long.class, //
            (config, key, field, target) -> field.set(target, config.getLongProperty(key).get()));
        injectorMap.put(Float.class, //
            (config, key, field, target) -> field.set(target, config.getFloatProperty(key).get()));
        injectorMap.put(Double.class,
            (config, key, field, target) -> field.set(target, config.getDoubleProperty(key).get()));
        injectorMap.put(Boolean.class,
            (config, key, field, target) -> field.set(target, config.getBooleanProperty(key).get()));
    }

    public void injectStatics(Configuration config, Class<?> targetClass) {
        for (Field field : targetClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                injectStaticField(config, field);
            }
        }
    }

    public void inject(Configuration config, Object target) {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                injectField(config, field, target);
            }
        }
    }

    private void injectStaticField(Configuration config, Field field) {
        injectField(config, field, STATIC_TARGET);
    }

    private void injectField(Configuration config, Field field, Object target) {

        ConfigurationValue configurationValue = field.getAnnotation(ConfigurationValue.class);
        if (configurationValue == null) {
            // field should not be injected
            return;
        }

        field.setAccessible(true);

        Injector injector = injectorMap.get(field.getType());
        if (injector == null) {
            throw new IllegalClassException(UNINJECTABLE_FIELD_TYPE + field.getType());
        }

        try {
            injector.injectInto(config, configurationValue.value(), field, target);
        } catch (IllegalAccessException e) {
            /* since fields are set accessible at the beginning of this method  IllegalAccessExceptions should not occur.
             * That makes it ok to throw an UndeclaredThrowableException */
            throw new UndeclaredThrowableException(e);
        }

    }

    public boolean canInjectValue(Field field) {
        return injectorMap.containsKey(field.getType());
    }

    private interface Injector {
        void injectInto(Configuration config, String key, Field field, Object target) throws IllegalAccessException;
    }

}
