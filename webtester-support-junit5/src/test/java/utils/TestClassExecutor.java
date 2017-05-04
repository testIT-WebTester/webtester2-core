package utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.DiscoveryFilter;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.reporting.ReportEntry;


public class TestClassExecutor {

    public static void execute(Class<?> testClass) throws Exception {
        try {

            JupiterTestEngine engine = new JupiterTestEngine();

            TestClassEngineDiscoveryRequest discoveryRequest = new TestClassEngineDiscoveryRequest(testClass);
            TestDescriptor testDescriptor = engine.discover(discoveryRequest, UniqueId.forEngine("foo-bar"));

            EngineExecutionListener listener = new NoOpEngineExecutionListener();
            ConfigurationParameters parameters = new NoConfigurationParameters();
            engine.execute(new ExecutionRequest(testDescriptor, listener, parameters));

        } catch (UndeclaredThrowableException e) {
            Throwable cause = getFirstNonUndeclaredThrowableCause(e);
            if (cause instanceof Error) {
                throw ( Error ) cause;
            } else if (cause instanceof RuntimeException) {
                throw ( RuntimeException ) cause;
            } else if (cause instanceof Exception) {
                throw ( Exception ) cause;
            } else {
                throw e;
            }
        }
    }

    public static Throwable getFirstNonUndeclaredThrowableCause(UndeclaredThrowableException e) {
        Throwable cause = e.getCause();
        while (cause instanceof UndeclaredThrowableException) {
            cause = cause.getCause();
        }
        return cause;
    }

    public static class NoConfigurationParameters implements ConfigurationParameters {

        @Override
        public Optional<String> get(String key) {
            return Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public int size() {
            return 0;
        }

    }

    public static class TestClassEngineDiscoveryRequest implements EngineDiscoveryRequest {

        private final Class<?> testClass;

        public TestClassEngineDiscoveryRequest(Class<?> testClass) {
            this.testClass = testClass;
        }

        @Override
        public <T extends DiscoverySelector> List<T> getSelectorsByType(Class<T> selectorType) {
            if (ClassSelector.class.equals(selectorType)) {
                List<T> list = new ArrayList<>();
                list.add(( T ) DiscoverySelectors.selectClass(testClass));
                return list;
            }
            return Collections.emptyList();
        }

        @Override
        public <T extends DiscoveryFilter<?>> List<T> getDiscoveryFiltersByType(Class<T> filterType) {
            return Collections.emptyList();
        }

        @Override
        public ConfigurationParameters getConfigurationParameters() {
            return new NoConfigurationParameters();
        }

    }

    public static class NoOpEngineExecutionListener implements EngineExecutionListener {

        @Override
        public void dynamicTestRegistered(TestDescriptor testDescriptor) {

        }

        @Override
        public void executionSkipped(TestDescriptor testDescriptor, String reason) {

        }

        @Override
        public void executionStarted(TestDescriptor testDescriptor) {

        }

        @Override
        public void executionFinished(TestDescriptor testDescriptor, TestExecutionResult testExecutionResult) {
            Optional<Throwable> throwable = testExecutionResult.getThrowable();
            if (throwable.isPresent()) {
                throw new UndeclaredThrowableException(throwable.get());
            }
        }

        @Override
        public void reportingEntryPublished(TestDescriptor testDescriptor, ReportEntry entry) {

        }

    }

}
