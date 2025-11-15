import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

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
    }

    /**
     * Запускає комплексну обробку даних з використанням черги.
     */
    public void runDataProcessing() {
        // спочатку працюємо з чергою
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

        // потім з масивом
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив символів.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();
        Arrays.sort(charArray);
        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву символів");
    }

    /**
     * Пошук символа в масиві.
     */
    private void findInArray() {
        long timeStart = System.nanoTime();
        int position = Arrays.binarySearch(this.charArray, charValueToSearch);
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
        Character minValue = charArray[0];
        Character maxValue = charArray[0];

        for (Character c : charArray) {
            if (c < minValue) minValue = c;
            if (c > maxValue) maxValue = c;
        }

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в масивi");
        System.out.println("Найменший символ в масивi: " + minValue);
        System.out.println("Найбільший символ в масивi: " + maxValue);
    }

    /**
     * Пошук символа в черзі.
     */
    private void findInQueue() {
        long timeStart = System.nanoTime();
        boolean found = charQueue.contains(charValueToSearch);
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
        Character minValue = Collections.min(charQueue);
        Character maxValue = Collections.max(charQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в PriorityQueue");
        System.out.println("Найменший символ в PriorityQueue: " + minValue);
        System.out.println("Найбільший символ в PriorityQueue: " + maxValue);
    }

    /**
     * Демонстрація операцій peek і poll з чергою.
     */
    private void performQueueOperations() {
        System.out.println("\nДемонстрація операцій з PriorityQueue:");
        
        // Перегляд першого елемента (без видалення)
        Character head = charQueue.peek();
        System.out.println("Перший елемент (peek): " + head);

        // Отримання і видалення першого елемента
        Character removed = charQueue.poll();
        System.out.println("Видалений елемент (poll): " + removed);

        // Перевірка нового першого елемента
        head = charQueue.peek();
        System.out.println("Новий перший елемент: " + head);
    }
}