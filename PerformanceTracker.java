/**
 * Клас PerformanceTracker відстежує продуктивність операцій з даними.
 */
public final class PerformanceTracker {
    /**
     * Відображає тривалість виконання операції в наносекундах.
     * 
     * @param startTime Початковий час операції в наносекундах.
     * @param operationName Назва операції.
     */
    public static void displayOperationTime(long startTime, String operationName) {
        long elapsedNs = System.nanoTime() - startTime;
        double elapsedMs = elapsedNs / 1_000_000.0;
        System.out.printf("  [Performance] %s: %d ns (%.3f ms)%n", operationName, elapsedNs, elapsedMs);
    }
}