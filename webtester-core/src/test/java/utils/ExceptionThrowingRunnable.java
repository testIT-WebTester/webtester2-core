package utils;

@FunctionalInterface
public interface ExceptionThrowingRunnable {

    void run() throws Exception;

}
