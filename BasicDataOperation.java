import java.util.Arrays;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Загальний клас BasicDataOperation координує роботу різних структур даних (тип Character).
 * 
 * <p>Цей клас служить центральною точкою для демонстрації операцій з різними
 * колекціями Java: Vector, PriorityQueue, TreeSet та Map з Canary.</p>
 * 
 * <p>Основні можливості:</p>
 * <ul>
 *   <li>Координація операцій з різними типами колекцій</li>  
 *   <li>Порівняльний аналіз продуктивності структур даних</li>
 *   <li>Централізоване управління обробкою символів</li>
 *   <li>Демонстрація переваг різних колекцій</li>
 * </ul>
 * 
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * java BasicDataOperation "2024-03-16T00:12:38Z" list
 * java BasicDataOperation "2024-03-16T00:12:38Z" queue  
 * java BasicDataOperation "2024-03-16T00:12:38Z" set
 * java BasicDataOperation "2024-03-16T00:12:38Z" all
 * }
 * </pre>
 */
public class BasicDataOperation {
    static final String PATH_TO_DATA_FILE = "list/Character.data";

    Character charValueToSearch;
    Character[] charArray;

    private static final String SEPARATOR = "\n" + "=".repeat(80) + "\n";
    private static final String USAGE_MESSAGE = "Використання: java BasicDataOperation <пошуковий-символ> \n" +
"Приклад:\n" +
"  java BasicDataOperation \"&\"";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(USAGE_MESSAGE);
            return;
        }

        String searchValue = args[0];

        if (searchValue == null || searchValue.length() == 0) {
            System.out.println("Помилка: порожній пошуковий символ.");
            return;
        }

        BasicDataOperation coordinator = new BasicDataOperation();
        coordinator.executeOperations(args);
    }

    /**
     * Координує виконання операцій залежно від обраного типу.
     * 
     * @param args Аргументи командного рядка
     */
    private void executeOperations(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("🚀 РОЗПОЧАТО АНАЛІЗ ДАНИХ типу Character 🚀");
        System.out.println("Пошуковий символ: " + args[0]);
        System.out.println(SEPARATOR);
        
        // Підготовка даних
        this.charValueToSearch = args[0].charAt(0);
        this.charArray = DataFileHandler.loadArrayFromFile(PATH_TO_DATA_FILE);
        
        runAllOperations();

        System.out.println(SEPARATOR);
        System.out.println("✅ АНАЛІЗ ЗАВЕРШЕНО ✅");
        System.out.println(SEPARATOR);
    }

    /**
     * Запускає операції з колекцією List.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runListOperations() {
        System.out.println("📋 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ LIST (Vector<Character>)");
        System.out.println("-".repeat(50));
        
        try {
            BasicDataOperationUsingList listProcessor = new BasicDataOperationUsingList(charValueToSearch, charArray);
            listProcessor.executeDataOperations();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з List: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Queue.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runQueueOperations() {
        System.out.println("🔄 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ QUEUE (PriorityQueue<Character>)");
        System.out.println("-".repeat(50));
        
        try {
            BasicDataOperationUsingQueue queueProcessor = new BasicDataOperationUsingQueue(charValueToSearch, charArray);
            queueProcessor.runDataProcessing();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з колекцією Set.
     * 
     * @param args Аргументи для передачі до класу
     */
    private void runSetOperations() {
        System.out.println("🔍 ОБРОБКА ДАНИХ З ВИКОРИСТАННЯМ SET (TreeSet<Character>)");
        System.out.println("-".repeat(50));
        
        try {         
            BasicDataOperationUsingSet setProcessor = new BasicDataOperationUsingSet(charValueToSearch, charArray);
            setProcessor.executeDataAnalysis();
        } catch (Exception e) {
            System.out.println("❌ Помилка при роботі з Set: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Запускає операції з усіма типами колекцій для порівняння.
     * 
     * @param args Аргументи для передачі до класів
     */
    private void runAllOperations() {
        System.out.println("🎯 КОМПЛЕКСНИЙ АНАЛІЗ ВСІХ СТРУКТУР ДАНИХ");
        System.out.println("=".repeat(60));
        
        // Обробка List
        runListOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");
        
        // Обробка Queue  
        runQueueOperations();
        System.out.println("\n" + "~".repeat(60) + "\n");
        
        // Обробка Set
        runSetOperations();
    }
}
// Файл актуален — дополнительных изменений не требуется.
