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
    private final Pet KEY_TO_SEARCH_AND_DELETE = new Pet("Ажур", "1");
    private final Pet KEY_TO_ADD = new Pet("Іскра", "3");

    private final String VALUE_TO_SEARCH_AND_DELETE = "Ганна";
    private final String VALUE_TO_ADD = "Павло";

    private LinkedHashMap<Pet, String> linkedHashMap;
    private TreeMap<Pet, String> treeMap;

    /**
     * Клас Pet для зберігання інформації про домашню тварину.
     * Реалізований як простий data class з автоматичним генеруванням методів equals(), hashCode(), та toString().
     * Еквівалент Java 14+ record, але сумісний з Java 11.
     */
    public static class Pet {
        private final String nickname;
        private final String species;

        public Pet(String nickname, String species) {
            this.nickname = nickname;
            this.species = species;
        }

        public String nickname() {
            return nickname;
        }

        public String species() {
            return species;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pet pet = (Pet) o;
            return nickname.equals(pet.nickname) && species.equals(pet.species);
        }

        @Override
        public int hashCode() {
            return 31 * nickname.hashCode() + species.hashCode();
        }

        @Override
        public String toString() {
            return "Pet{" + "nickname='" + nickname + '\'' + ", species='" + species + '\'' + '}';
        }
    }

    /**
     * Компаратор для сортування Pet за nickname та species.
     * Спочатку сортує за nickname за зростанням, потім за species у зворотному порядку.
     */
    private static final Comparator<Pet> PET_COMPARATOR = 
        Comparator.comparing(Pet::nickname).thenComparing(Pet::species, Comparator.reverseOrder());

    /**
     * Конструктор, який ініціалізує об'єкт з готовими даними.
     * 
     * @param linkedHashMap LinkedHashMap з початковими даними (ключ: Pet, значення: ім'я власника)
     * @param treeMap TreeMap з початковими даними (ключ: Pet, значення: ім'я власника)
     */
    BasicDataOperationUsingMap(LinkedHashMap<Pet, String> linkedHashMap, TreeMap<Pet, String> treeMap) {
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

        for (Map.Entry<Pet, String> entry : linkedHashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в LinkedHashMap");
    }

    /**
     * Сортує LinkedHashMap за ключами.
     * Використовує PET_COMPARATOR для сортування ключів Pet за nickname та species.
     * Перезаписує linkedHashMap відсортованими даними.
     */
    private void sortLinkedHashMap() {
        long timeStart = System.nanoTime();

        // Використовуємо PET_COMPARATOR для сортування за nickname та species
        linkedHashMap = linkedHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(PET_COMPARATOR))
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

        Map.Entry<Pet, String> foundEntry = linkedHashMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в LinkedHashMap");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
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
     * Використовує Stream API для фільтрування та видалення.
     */
    void removeByValueFromLinkedHashMap() {
        long timeStart = System.nanoTime();

        List<Pet> keysToRemove = linkedHashMap.entrySet().stream()
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
     * TreeMap автоматично відсортована за ключами (Pet nickname за зростанням, species за зростанням).
     */
    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");

        long timeStart = System.nanoTime();
        for (Map.Entry<Pet, String> entry : treeMap.entrySet()) {
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

        Map.Entry<Pet, String> foundEntry = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .findFirst()
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "пошук за значенням в TreeMap");

        if (foundEntry != null) {
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Pet: " + foundEntry.getKey());
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
     * Використовує Stream API для фільтрування та видалення.
     */
    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();

        List<Pet> keysToRemove = treeMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        keysToRemove.forEach(treeMap::remove);

        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");

        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Додатковий метод для порівняння продуктивності різних типів Map.
     * Аналізує швидкість обробки даних для операцій додавання, пошуку та видалення.
     */
    private void compareMapPerformance() {
        System.out.println("\n\n=== АНАЛІЗ ШВИДКОСТІ ОБРОБКИ ДАНИХ ===\n");
        
        int testSize = 10000;
        
        // Підготовка тестових даних
        System.out.println("📊 Тестування з " + testSize + " елементами\n");
        
        // ===== HashMap =====
        System.out.println("1️⃣  HashMap:");
        System.out.println("-".repeat(50));
        HashMap<Pet, String> hashMap = new HashMap<>();
        
        long startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            hashMap.put(new Pet("Pet" + i, "Species" + (i % 5)), "Owner" + i);
        }
        long hashMapAddTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - hashMapAddTime, "додавання " + testSize + " елементів");
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize / 2; i++) {
            hashMap.get(new Pet("Pet" + i, "Species" + (i % 5)));
        }
        long hashMapSearchTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - hashMapSearchTime, "пошук " + (testSize / 2) + " елементів");
        
        // ===== LinkedHashMap =====
        System.out.println("\n2️⃣  LinkedHashMap:");
        System.out.println("-".repeat(50));
        LinkedHashMap<Pet, String> linkedMap = new LinkedHashMap<>();
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            linkedMap.put(new Pet("Pet" + i, "Species" + (i % 5)), "Owner" + i);
        }
        long linkedMapAddTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - linkedMapAddTime, "додавання " + testSize + " елементів");
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize / 2; i++) {
            linkedMap.get(new Pet("Pet" + i, "Species" + (i % 5)));
        }
        long linkedMapSearchTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - linkedMapSearchTime, "пошук " + (testSize / 2) + " елементів");
        
        // ===== TreeMap =====
        System.out.println("\n3️⃣  TreeMap:");
        System.out.println("-".repeat(50));
        TreeMap<Pet, String> treeMapTest = new TreeMap<>(PET_COMPARATOR);
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            treeMapTest.put(new Pet("Pet" + i, "Species" + (i % 5)), "Owner" + i);
        }
        long treeMapAddTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - treeMapAddTime, "додавання " + testSize + " елементів");
        
        startTime = System.nanoTime();
        for (int i = 0; i < testSize / 2; i++) {
            treeMapTest.get(new Pet("Pet" + i, "Species" + (i % 5)));
        }
        long treeMapSearchTime = System.nanoTime() - startTime;
        PerformanceTracker.displayOperationTime(System.nanoTime() - treeMapSearchTime, "пошук " + (testSize / 2) + " елементів");
        
        // ===== Підсумок =====
        System.out.println("\n\n📈 ПОРІВНЯЛЬНА ТАБЛИЦЯ:\n");
        System.out.println(String.format("%-20s | %-20s | %-20s", "Операція", "HashMap", "LinkedHashMap"));
        System.out.println("-".repeat(65));
        System.out.println(String.format("%-20s | %-20d | %-20d", "Додавання (мкс)", hashMapAddTime / 1000, linkedMapAddTime / 1000));
        System.out.println(String.format("%-20s | %-20d | %-20d", "Пошук (мкс)", hashMapSearchTime / 1000, linkedMapSearchTime / 1000));
        System.out.println("-".repeat(65));
        System.out.println(String.format("%-20s | %-20d", "TreeMap - Додавання (мкс)", treeMapAddTime / 1000));
        System.out.println(String.format("%-20s | %-20d", "TreeMap - Пошук (мкс)", treeMapSearchTime / 1000));
        
        System.out.println("\n\n📌 ВИСНОВКИ:");
        System.out.println("• HashMap: найшвидший для додавання та пошуку O(1)");
        System.out.println("• LinkedHashMap: збереження порядку вставки з незначним уповільненням");
        System.out.println("• TreeMap: автоматичне сортування, але повільніше O(log n)");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Створюємо початкові дані для LinkedHashMap
        LinkedHashMap<Pet, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new Pet("Ажур", "1"), "Василь");
        linkedHashMap.put(new Pet("Балакун", "2"), "Ганна");
        linkedHashMap.put(new Pet("Весна", "3"), "Денис");
        linkedHashMap.put(new Pet("Грація", "4"), "Євгенія");
        linkedHashMap.put(new Pet("Ажур", "5"), "Ганна");
        linkedHashMap.put(new Pet("Дзвіночок", "1"), "Інна");
        linkedHashMap.put(new Pet("Еол", "2"), "Костянтин");
        linkedHashMap.put(new Pet("Жайворонок", "3"), "Люба");
        linkedHashMap.put(new Pet("Балакун", "4"), "Костянтин");
        linkedHashMap.put(new Pet("Зоренька", "5"), "Оксана");

        // Створюємо такі ж дані для TreeMap
        TreeMap<Pet, String> treeMap = new TreeMap<>(PET_COMPARATOR);
        treeMap.put(new Pet("Ажур", "1"), "Василь");
        treeMap.put(new Pet("Балакун", "2"), "Ганна");
        treeMap.put(new Pet("Весна", "3"), "Денис");
        treeMap.put(new Pet("Грація", "4"), "Євгенія");
        treeMap.put(new Pet("Ажур", "5"), "Ганна");
        treeMap.put(new Pet("Дзвіночок", "1"), "Інна");
        treeMap.put(new Pet("Еол", "2"), "Костянтин");
        treeMap.put(new Pet("Жайворонок", "3"), "Люба");
        treeMap.put(new Pet("Балакун", "4"), "Костянтин");
        treeMap.put(new Pet("Зоренька", "5"), "Оксана");

        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(linkedHashMap, treeMap);
        operations.executeDataOperations();
    }
}