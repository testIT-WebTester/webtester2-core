package info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter;

import java.util.Optional;

import info.novatec.testit.webtester.config.Configuration;


public class NoOpConfigurationValueGetter implements TypedConfigurationValueGetter<Object> {

    @Override
    public boolean canHandle(Class<?> type) {
        return false;
    }

    @Override
    public Optional<Object> get(Configuration config, String key) {
        return Optional.empty();
    }

}
