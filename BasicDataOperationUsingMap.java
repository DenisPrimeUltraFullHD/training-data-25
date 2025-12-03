import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map для зберігання пар ключ-значення.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з даними Map.</li>
 *   <li>{@link #findByKey()} - Здійснює пошук елемента за ключем в Map.</li>
 *   <li>{@link #findByValue()} - Здійснює пошук елемента за значенням в Map.</li>
 *   <li>{@link #addEntry()} - Додає новий запис до Map.</li>
 *   <li>{@link #removeByKey()} - Видаляє запис з Map за ключем.</li>
 *   <li>{@link #removeByValue()} - Видаляє записи з Map за значенням.</li>
 *   <li>{@link #sortByKey()} - Сортує Map за ключами.</li>
 *   <li>{@link #sortByValue()} - Сортує Map за значеннями.</li>
 * </ul>
 */
public class BasicDataOperationUsingMap {
    private final Canary KEY_TO_SEARCH_AND_DELETE = new Canary("Ажур", "1");
    private final Canary KEY_TO_ADD = new Canary("Іскра", "3");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Ганна";
    private final String VALUE_TO_ADD = "Павло";

    private LinkedHashMap<Canary, String> linkedHashMap;
    private TreeMap<Canary, String> treeMap;

    // OwnerValueComparator removed: use Stream API for value-based operations

    /**
     * Внутрішній клас Canary для зберігання інформації про канарку.
     */
    public static class Canary implements Comparable<Canary> {
        private final String nickname;
        private final String singing;

        public Canary(String nickname, String singing) {
            this.nickname = nickname;
            this.singing = singing;
        }

        public String getNickname() { 
            return nickname; 
        }

        public String getSinging() {
            return singing;
        }

        /**
         * Порівнює цей об'єкт Canary з іншим для визначення порядку сортування.
         * Природний порядок: спочатку за кличкою (nickname) за зростанням, потім за співочістю (singing) за зростанням.
         * 
         * @param other Canary об'єкт для порівняння
         * @return негативне число, якщо цей Canary < other; 
         *         0, якщо цей Canary == other; 
         *         позитивне число, якщо цей Canary > other
         * 
         * Критерій порівняння: поля nickname (кличка) та singing (співочість) за зростанням.
         * 
         * Цей метод використовується:
         * - TreeMap для автоматичного сортування ключів Canary за nickname та singing
         * - Collections.sort() для сортування Map.Entry за ключами Canary
         * - Collections.binarySearch() для пошуку в відсортованих колекціях
         */
        @Override
        public int compareTo(Canary other) {
            if (other == null) return 1;
            
            // Спочатку порівнюємо за кличкою (за зростанням)
            int nicknameComparison = this.nickname.compareTo(other.nickname);
            if (nicknameComparison != 0) {
                return nicknameComparison;
            }
            
            // Якщо клички однакові, порівнюємо за співочістю (за зростанням)
            return this.singing.compareTo(other.singing);
        }

        /**
         * Перевіряє рівність цього Canary з іншим об'єктом.
         * Два Canary вважаються рівними, якщо їх клички (nickname) та співочості (singing) однакові.
         * 
         * @param obj об'єкт для порівняння
         * @return true, якщо об'єкти рівні; false в іншому випадку
         * 
         * Критерій рівності: поля nickname (кличка) та singing (співочість).
         * 
         * Важливо: метод узгоджений з compareTo() - якщо equals() повертає true,
         * то compareTo() повертає 0, оскільки обидва методи порівнюють за nickname та singing.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Canary canary = (Canary) obj;
            
            boolean nicknameEquals = nickname != null ? nickname.equals(canary.nickname) : canary.nickname == null;
            boolean singingEquals = singing != null ? singing.equals(canary.singing) : canary.singing == null;
            
            return nicknameEquals && singingEquals;
        }

        /**
         * Повертає хеш-код для цього Canary.
         * 
         * @return хеш-код, обчислений на основі nickname та singing
         * 
         * Базується на полях nickname та singing для узгодженості з equals().
         * 
         * Важливо: узгоджений з equals() - якщо два Canary рівні за equals()
         * (мають однакові nickname та singing), вони матимуть однаковий hashCode().
         */
        @Override
        public int hashCode() {
            return Objects.hash(nickname, singing);
        }

        /**
         * Повертає строкове представлення Canary.
         * 
         * @return кличка канарки (nickname), співочість (singing) та hashCode
         */
        @Override
        public String toString() {
            if (singing != null) {
                return "Canary{nickname='" + nickname + "', singing='" + singing + "', hashCode=" + hashCode() + "}";
            }
            return "Canary{nickname='" + nickname + "', hashCode=" + hashCode() + "}";
        }
    }

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
        linkedHashMap.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в LinkedHashMap");
    }

    /**
     * Сортує LinkedHashMap за ключами.
     * Використовує Collections.sort() з природним порядком Canary (Canary.compareTo()).
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Використовуємо Stream API для сортування по ключу (Canary.compareTo())
        linkedHashMap = linkedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
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
     * Використовує Canary.hashCode() та Canary.equals() для пошуку.
     */
    void findByKeyInLinkedHashMap() {
        long timeStart = System.nanoTime();

        boolean found = linkedHashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

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
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInLinkedHashMap() {
        long timeStart = System.nanoTime();
        // Використовуємо Stream API для пошуку за значенням
        List<Map.Entry<Canary, String>> matches = linkedHashMap.entrySet().stream()
                .filter(e -> VALUE_TO_SEARCH_AND_DELETE.equals(e.getValue()))
                .collect(Collectors.toList());

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в LinkedHashMap (stream)");

        if (!matches.isEmpty()) {
            matches.forEach(me -> System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + me.getKey()));
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

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
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
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Canary> keysToRemove = linkedHashMap.entrySet().stream()
                .filter(entry -> VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        keysToRemove.forEach(linkedHashMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з LinkedHashMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    /**
     * Виводить вміст TreeMap.
     * TreeMap автоматично відсортована за ключами (Canary nickname за зростанням, singing за зростанням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        treeMap.entrySet().forEach(entry ->
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue())
        );

        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    /**
     * Здійснює пошук елемента за ключем в TreeMap.
     * Використовує Canary.compareTo() для навігації по дереву.
     */
    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();

        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);

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
     * Сортує список Map.Entry за значеннями та використовує бінарний пошук.
     */
    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();
        // Використовуємо Stream API для пошуку за значенням
        List<Map.Entry<Canary, String>> matches = treeMap.entrySet().stream()
                .filter(e -> VALUE_TO_SEARCH_AND_DELETE.equals(e.getValue()))
                .collect(Collectors.toList());

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в TreeMap (stream)");

        if (!matches.isEmpty()) {
            matches.forEach(me -> System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + me.getKey()));
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

        System.out.println("Додано новий запис: Pet='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
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
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Canary> keysToRemove = treeMap.entrySet().stream()
                .filter(entry -> VALUE_TO_SEARCH_AND_DELETE.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        keysToRemove.forEach(treeMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Додатковий метод для порівняння продуктивності різних типів Map.
     */
    private void compareMapPerformance() {
        System.out.println("\n=== Порівняння продуктивності Map ===");
        
        // Створюємо тестові дані
        Canary testCanary = new Canary("Тест", "Спів");
        String testValue = "Тестовий власник";
        
        // HashMap тести
        HashMap<Canary, String> hashMap = new HashMap<>();
        long startTime = System.nanoTime();
        hashMap.put(testCanary, testValue);
        PerformanceTracker.displayOperationTime(startTime, "додавання в HashMap");

        // LinkedHashMap тести
        LinkedHashMap<Canary, String> linkedMap = new LinkedHashMap<>();
        startTime = System.nanoTime();
        linkedMap.put(testCanary, testValue);
        PerformanceTracker.displayOperationTime(startTime, "додавання в LinkedHashMap");

        // TreeMap тести (вже існує в класі як treeMap)
        startTime = System.nanoTime();
        treeMap.put(testCanary, testValue);
        PerformanceTracker.displayOperationTime(startTime, "додавання в TreeMap");

        // Пошук елементів
        startTime = System.nanoTime();
        hashMap.get(testCanary);
        PerformanceTracker.displayOperationTime(startTime, "пошук в HashMap");

        startTime = System.nanoTime();
        linkedMap.get(testCanary);
        PerformanceTracker.displayOperationTime(startTime, "пошук в LinkedHashMap");

        startTime = System.nanoTime();
        treeMap.get(testCanary);
        PerformanceTracker.displayOperationTime(startTime, "пошук в TreeMap");
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

        // Створюємо такі ж дані для TreeMap
        TreeMap<Canary, String> treeMap = new TreeMap<>();
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