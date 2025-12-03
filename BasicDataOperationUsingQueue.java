import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Клас BasicDataOperationUsingQueue реалізує операції з чергою PriorityQueue для Character.
 */
public class BasicDataOperationUsingQueue {
    private Character charValueToSearch;
    private Character[] charArray;
    private Queue<Character> charQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     */
    BasicDataOperationUsingQueue(Character charValueToSearch, Character[] charArray) {
        this.charValueToSearch = charValueToSearch;
        this.charArray = charArray;
        this.charQueue = new PriorityQueue<>(Arrays.asList(charArray));
        System.out.println("\n✅ BasicDataOperationUsingQueue ініціалізована");
        System.out.println("   Розмір PriorityQueue: " + charQueue.size());
        System.out.println("   Розмір масиву: " + charArray.length);
        System.out.println("   Символ для пошуку: '" + charValueToSearch + "'");
    }

    /**
     * Запускає комплексну обробку даних з використанням черги.
     */
    public void runDataProcessing() {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│    ОПЕРАЦІЇ З PRIORITYQUEUE<CHARACTER>       │");
        System.out.println("└─────────────────────────────────────────────┘");
        
        // спочатку працюємо з чергою
        System.out.println("\n🔎 ЕТАП 1: ПОШУК І ПОШУК МІН/МАКС (до операцій з чергою)");
        findInQueue();
        locateMinMaxInQueue();
        
        System.out.println("\n📋 ЕТАП 2: БАЗОВІ ОПЕРАЦІЇ З PRIORITYQUEUE (peek, poll)");
        performQueueOperations();

        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│    ОПЕРАЦІЇ З МАСИВОМ CHARACTER[]           │");
        System.out.println("└─────────────────────────────────────────────┘");

        // потім з масивом
        System.out.println("\n🔎 ЕТАП 1: ПОШУК І ПОШУК МІН/МАКС (до сортування)");
        findInArray();
        locateMinMaxInArray();

        System.out.println("\n📊 ЕТАП 2: СОРТУВАННЯ МАСИВУ");
        performArraySorting();

        System.out.println("\n🔎 ЕТАП 3: ПОШУК І ПОШУК МІН/МАКС (після сортування)");
        findInArray();
        locateMinMaxInArray();

        System.out.println("\n💾 ЕТАП 4: ЗБЕРЕЖЕННЯ ВІДСОРТОВАНОГО МАСИВУ");
        // зберігаємо відсортований масив
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
        System.out.println("✅ Масив успішно збережено в файл");
    }

    /**
     * Упорядковує масив символів.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();
        charArray = Arrays.stream(charArray)
                .sorted()
                .toArray(Character[]::new);
        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву символів");
    }

    /**
     * Пошук символа в масиві.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();
        int position = Arrays.stream(this.charArray)
                .map(elem -> Arrays.asList(this.charArray).indexOf(elem))
                .filter(i -> charValueToSearch.equals(this.charArray[i]))
                .findFirst()
                .orElse(-1);

        PerformanceTracker.displayOperationTime(timeStart, "пошук символа в масивi");

        if (position >= 0) {
            System.out.println("Символ '" + charValueToSearch + "' знайдено в масивi за позицією: " + position);
        } else {
            System.out.println("Символ '" + charValueToSearch + "' відсутній в масиві.");
        }
    }

    /**
     * Пошук мін/макс значень в масиві.
     */
    private void locateMinMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();
        Character minValue = Arrays.stream(charArray)
                .min(Character::compareTo)
                .orElse(null);

        Character maxValue = Arrays.stream(charArray)
                .max(Character::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в масивi");
        System.out.println("Найменший символ в масивi: " + minValue);
        System.out.println("Найбільший символ в масивi: " + maxValue);
    }

    /**
     * Пошук символа в черзі.
     */
    private void findInQueue() {
        long timeStart = System.nanoTime();
        boolean found = charQueue.stream().anyMatch(ch -> ch.equals(charValueToSearch));
        PerformanceTracker.displayOperationTime(timeStart, "пошук символа в PriorityQueue");

        if (found) {
            System.out.println("Символ '" + charValueToSearch + "' знайдено в PriorityQueue");
        } else {
            System.out.println("Символ '" + charValueToSearch + "' відсутній в PriorityQueue");
        }
    }

    /**
     * Пошук мін/макс значень в черзі.
     */
    private void locateMinMaxInQueue() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("PriorityQueue є пустою або не ініціалізованою.");
            return;
        }

        long timeStart = System.nanoTime();
        Character minValue = charQueue.stream()
                .min(Character::compareTo)
                .orElse(null);

        Character maxValue = charQueue.stream()
                .max(Character::compareTo)
                .orElse(null);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в PriorityQueue");
        System.out.println("Найменший символ в PriorityQueue: " + minValue);
        System.out.println("Найбільший символ в PriorityQueue: " + maxValue);
    }

    /**
     * Демонстрація операцій peek і poll з чергою.
     */
    private void performQueueOperations() {
        // Рахуємо розмір перед операціями
        int initialSize = charQueue.size();
        System.out.println("Розмір PriorityQueue перед операціями: " + initialSize);
        
        // Перегляд першого елемента (без видалення)
        long timeStart = System.nanoTime();
        Character head = charQueue.peek();
        PerformanceTracker.displayOperationTime(timeStart, "операція peek() на PriorityQueue");
        System.out.println("→ Перший елемент (peek): '" + head + "'");

        // Отримання і видалення першого елемента
        timeStart = System.nanoTime();
        Character removed = charQueue.poll();
        PerformanceTracker.displayOperationTime(timeStart, "операція poll() на PriorityQueue");
        System.out.println("→ Видалений елемент (poll): '" + removed + "'");
        System.out.println("   Розмір після poll(): " + charQueue.size());

        // Перевірка нового першого елемента
        if (!charQueue.isEmpty()) {
            head = charQueue.peek();
            System.out.println("→ Новий перший елемент: '" + head + "'");
        } else {
            System.out.println("→ PriorityQueue порожня після операцій");
        }
    }
}