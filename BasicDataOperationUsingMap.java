import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Характеристики:</p>
 * <ul>
 *   <li>Класс Canary (канарка) з полями: nickname (кличка), singing (співочість)</li>
 *   <li>Comparator для сортування за nickname та singing (обидва за зростанням)</li>
 *   <li>Операції з LinkedHashMap та TreeMap</li>
 *   <li>Аналіз продуктивності обох типів Map</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    /**
     * Java Record еквівалент для зберігання інформації про домашню тварину Canary (канарка).
     * Еквівалент: public record Canary(String nickname, String singing) {}
     * Реалізований як data class, сумісний з Java 11.
     * 
     * Характеристики:
     * - nickname: кличка канарки
     * - singing: співочість (рівень співу)
     */
    public static final class Canary {
        private final String nickname;
        private final String singing;

        public Canary(String nickname, String singing) {
            this.nickname = nickname;
            this.singing = singing;
        }

        public String nickname() {
            return nickname;
        }

        public String singing() {
            return singing;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Canary canary = (Canary) o;
            return nickname.equals(canary.nickname) && singing.equals(canary.singing);
        }

        @Override
        public int hashCode() {
            return 31 * nickname.hashCode() + singing.hashCode();
        }

        @Override
        public String toString() {
            return "Canary{" + "nickname='" + nickname + '\'' + ", singing='" + singing + '\'' + '}';
        }
    }

    /**
     * Компаратор для сортування Canary за nickname та singing.
     * Спочатку сортує за nickname за зростанням (A->Z),
     * потім за singing за зростанням (за номерами/буквами) для однакових nickname.
     */
    private static final Comparator<Canary> CANARY_COMPARATOR = 
        Comparator.comparing(Canary::nickname).thenComparing(Canary::singing);

    private final Canary KEY_TO_SEARCH_AND_DELETE = new Canary("Ажур", "1");
    private final Canary KEY_TO_ADD = new Canary("Іскра", "3");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Ганна";
    private final String VALUE_TO_ADD = "Павло";

    private LinkedHashMap<Canary, String> linkedHashMap;
    private TreeMap<Canary, String> treeMap;

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param linkedHashMap LinkedHashMap з початковими даними (ключ: Canary, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Canary, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(LinkedHashMap<Canary, String> linkedHashMap, TreeMap<Canary, String> treeMap) {
        this.linkedHashMap = linkedHashMap;
        this.treeMap = treeMap;
    }
    
    /**
     * Виконує комплексні операції з Map.
     * 
     * Метод виконує різноманітні операції з Map: пошук, додавання, видалення та сортування.
     */
    public void executeDataOperations() {
        // Спочатку працюємо з LinkedHashMap
        System.out.println("========= Операції з LinkedHashMap =========");
        System.out.println("Початковий розмір LinkedHashMap: " + linkedHashMap.size());
        
        // Пошук до сортування
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        printLinkedHashMap();
        sortLinkedHashMap();
        printLinkedHashMap();

        // Пошук після сортування
        findByKeyInLinkedHashMap();
        findByValueInLinkedHashMap();

        addEntryToLinkedHashMap();
        
        removeByKeyFromLinkedHashMap();
        removeByValueFromLinkedHashMap();
               
        System.out.println("Кінцевий розмір LinkedHashMap: " + linkedHashMap.size());

        // Потім обробляємо TreeMap
        System.out.println("\n\n========= Операції з TreeMap =========");
        System.out.println("Початковий розмір TreeMap: " + treeMap.size());
        
        findByKeyInTreeMap();
        findByValueInTreeMap();

        printTreeMap();

        addEntryToTreeMap();
        
        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();
        
        System.out.println("Кінцевий розмір TreeMap: " + treeMap.size());
        
        // Додаємо порівняння продуктивності в кінці
        compareMapPerformance();
    }

    // ===== Методи для LinkedHashMap =====

    /**
     * Виводить вміст LinkedHashMap без сортування.
     * LinkedHashMap зберігає порядок додавання елементів.
     */
    private void printLinkedHashMap() {
        System.out.println("\n=== Пари ключ-значення в LinkedHashMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Canary, String> entry : linkedHashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в LinkedHashMap");
    }

    /**
     * Сортує LinkedHashMap за ключами.
     * Використовує CANARY_COMPARATOR для сортування ключів Canary за nickname та singing.
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Використовуємо CANARY_COMPARATOR для сортування за nickname та singing
        linkedHashMap = linkedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(CANARY_COMPARATOR))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        PerformanceTracker.displayOperationTime(timeStart, "сортування LinkedHashMap за ключами");
    }

    /**
     * Здійснює пошук елемента за ключем в LinkedHashMap.
     * Використовує Stream API для пошуку за ключем.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = linkedHashMap.keySet().stream()
                .anyMatch(key -> key.equals(KEY_TO_SEARCH_AND_DELETE));

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в LinkedHashMap");

        if (found) {
            String value = linkedHashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в LinkedHashMap.
     * Використовує Stream API для пошуку за значенням.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();

        Map.Entry<Canary, String> foundEntry = linkedHashMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в LinkedHashMap");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Canary: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в LinkedHashMap.");
        }
    }

    /**
     * Додає новий запис до LinkedHashMap.
     */
    void addEntryToLinkedHashMap() {
        long timeStart = System.nanoTime();

        linkedHashMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до LinkedHashMap");

        System.out.println("Додано новий запис: Canary='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з LinkedHashMap за ключем.
     */
    void removeByKeyFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        String removedValue = linkedHashMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з LinkedHashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з LinkedHashMap за значенням.
     * Використовує Stream API для фільтрування та видалення.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Canary> keysToRemove = linkedHashMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(linkedHashMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Canary nickname та singing за зростанням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Canary, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    /**
     * Здійснює пошук елемента за ключем в TreeMap.
     * Використовує Stream API для пошуку за ключем.
     */
    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.keySet().stream()
                .anyMatch(key -> key.equals(KEY_TO_SEARCH_AND_DELETE));

        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");

        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Здійснює пошук елемента за значенням в TreeMap.
     * Використовує Stream API для пошуку за значенням.
     */
    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();

        Map.Entry<Canary, String> foundEntry = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в TreeMap");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Canary: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    /**
     * Додає новий запис до TreeMap.
     */
    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();

        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);

        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");

        System.out.println("Додано новий запис: Canary='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    /**
     * Видаляє запис з TreeMap за ключем.
     */
    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();

        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    /**
     * Видаляє записи з TreeMap за значенням.
     * Використовує Stream API для фільтрування та видалення.
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Canary> keysToRemove = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(treeMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Додатковий метод для порівняння продуктивності LinkedHashMap та TreeMap.
     * Аналізує швидкість обробки даних для операцій додавання та пошуку.
     */
    private void compareMapPerformance() {
        System.out.println("\n\n=== АНАЛІЗ ШВИДКОСТІ ОБРОБКИ ДАНИХ ===\n");
        
        int testSize = 10000;
        
        // Підготовка тестових даних
        System.out.println("📊 Тестування з " + testSize + " елементами\n");
        
        // ===== LinkedHashMap =====
        System.out.println("1️⃣  LinkedHashMap:");
        System.out.println("-".repeat(50));
        LinkedHashMap<Canary, String> linkedMap = new LinkedHashMap<>();
        
        long startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            linkedMap.put(new Canary("Canary" + i, String.valueOf(i % 5)), "Owner" + i);
        }
        long linkedMapAddTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - linkedMapAddTime, "додавання " + testSize + " елементів");
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize / 2; i++) {
            linkedMap.get(new Canary("Canary" + i, String.valueOf(i % 5)));
        }
        long linkedMapSearchTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - linkedMapSearchTime, "пошук " + (testSize / 2) + " елементів");
        
        // ===== TreeMap з CANARY_COMPARATOR =====
        System.out.println("\n2️⃣  TreeMap (з Comparator):");
        System.out.println("-".repeat(50));
        TreeMap<Canary, String> treeMapTest = new TreeMap<>(CANARY_COMPARATOR);
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            treeMapTest.put(new Canary("Canary" + i, String.valueOf(i % 5)), "Owner" + i);
        }
        long treeMapAddTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - treeMapAddTime, "додавання " + testSize + " елементів");
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize / 2; i++) {
            treeMapTest.get(new Canary("Canary" + i, String.valueOf(i % 5)));
        }
        long treeMapSearchTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - treeMapSearchTime, "пошук " + (testSize / 2) + " елементів");
        
        // ===== Підсумок =====
        System.out.println("\n\n📈 ПОРІВНЯЛЬНА ТАБЛИЦЯ:\n");
        System.out.println(String.format("%-25s | %-20s | %-20s", "Операція", "LinkedHashMap", "TreeMap"));
        System.out.println("-".repeat(70));
        System.out.println(String.format("%-25s | %-20d | %-20d", "Додавання (мкс)", linkedMapAddTime / 1000, treeMapAddTime / 1000));
        System.out.println(String.format("%-25s | %-20d | %-20d", "Пошук (мкс)", linkedMapSearchTime / 1000, treeMapSearchTime / 1000));
        System.out.println("-".repeat(70));
        
        System.out.println("\n\n📌 ВИСНОВКИ:");
        System.out.println("• LinkedHashMap: збереження порядку вставки, швидкий пошук O(1)");
        System.out.println("• TreeMap: автоматичне сортування за Comparator, але повільніше O(log n)");
        System.out.println("• TreeMap завжди відсортована, тоді як LinkedHashMap потребує явного сортування");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані для LinkedHashMap
        LinkedHashMap<Canary, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new Canary("Ажур", "1"), "Василь");
        linkedHashMap.put(new Canary("Балакун", "2"), "Ганна");
        linkedHashMap.put(new Canary("Весна", "3"), "Денис");
        linkedHashMap.put(new Canary("Грація", "4"), "Євгенія");
        linkedHashMap.put(new Canary("Ажур", "5"), "Ганна");
        linkedHashMap.put(new Canary("Дзвіночок", "1"), "Інна");
        linkedHashMap.put(new Canary("Еол", "2"), "Костянтин");
        linkedHashMap.put(new Canary("Жайворонок", "3"), "Люба");
        linkedHashMap.put(new Canary("Балакун", "4"), "Костянтин");
        linkedHashMap.put(new Canary("Зоренька", "5"), "Оксана");

        // Створюємо такі ж дані для TreeMap з CANARY_COMPARATOR
        TreeMap<Canary, String> treeMap = new TreeMap<>(CANARY_COMPARATOR);
        treeMap.put(new Canary("Ажур", "1"), "Василь");
        treeMap.put(new Canary("Балакун", "2"), "Ганна");
        treeMap.put(new Canary("Весна", "3"), "Денис");
        treeMap.put(new Canary("Грація", "4"), "Євгенія");
        treeMap.put(new Canary("Ажур", "5"), "Ганна");
        treeMap.put(new Canary("Дзвіночок", "1"), "Інна");
        treeMap.put(new Canary("Еол", "2"), "Костянтин");
        treeMap.put(new Canary("Жайворонок", "3"), "Люба");
        treeMap.put(new Canary("Балакун", "4"), "Костянтин");
        treeMap.put(new Canary("Зоренька", "5"), "Оксана");

        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(linkedHashMap, treeMap);
        operations.executeDataOperations();
    }
}
