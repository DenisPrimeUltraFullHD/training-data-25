import java.util.Queue;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Клас BasicDataOperationUsingQueue реалізує роботу з колекціями типу Queue для Character.
 * 
 * <p>Основні функції класу:</p>
 * <ul>
 *   <li>{@link #runDataProcessing()} - Запускає комплекс операцій з даними.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив Character.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві Character.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить мінімальне і максимальне значення в масиві.</li>
 *   <li>{@link #findInQueue()} - Пошук значення в черзі Character.</li>
 *   <li>{@link #locateMinMaxInQueue()} - Знаходить граничні значення в черзі.</li>
 *   <li>{@link #performQueueOperations()} - Виконує операції peek і poll з чергою.</li>
 * </ul>
 * 
 */
public class BasicDataOperationUsingQueue {
    private Character charValueToSearch;
    private Character[] charArray;
    private Queue<Character> charQueue;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param charValueToSearch Значення для пошуку
     * @param charArray Масив Character
     */
    BasicDataOperationUsingQueue(Character charValueToSearch, Character[] charArray) {
        this.charValueToSearch = charValueToSearch;
        this.charArray = charArray;
        this.charQueue = new PriorityQueue<>(Arrays.asList(charArray));
    }
    
    /**
     * Запускає комплексну обробку даних з використанням черги.
     * 
     * Метод завантажує дані, виконує операції з чергою та масивом Character.
     */
    public void runDataProcessing() {
        // спочатку обробляємо чергу символів
        findInQueue();
        locateMinMaxInQueue();
        performQueueOperations();

        // потім працюємо з масивом
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Сортує масив об'єктiв Character та виводить початковий i вiдсортований масиви.
     * Вимiрює та виводить час, витрачений на сортування масиву в наносекундах.
     */
    private void performArraySorting() {
        // вимірюємо тривалість упорядкування масиву символів
        long timeStart = System.nanoTime();

        Arrays.sort(charArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву символів");
    }

    /**
     * Здійснює пошук конкретного значення в масиві символів.
     */
    private void findInArray() {
        // відстежуємо час виконання пошуку в масиві
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
     * Визначає найменше та найбільше значення в масиві Character.
     */
    private void locateMinMaxInArray() {
        if (charArray == null || charArray.length == 0) {
            System.out.println("Масив є пустим або не ініціалізованим.");
            return;
        }

        // відстежуємо час на визначення граничних значень
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
     * Здійснює пошук конкретного значення в черзі символів.
     */
    private void findInQueue() {
        // вимірюємо час пошуку в черзі
        long timeStart = System.nanoTime();

        boolean elementExists = this.charQueue.contains(charValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук символа в Queue");

        if (elementExists) {
            System.out.println("Символ '" + charValueToSearch + "' знайдено в Queue");
        } else {
            System.out.println("Символ '" + charValueToSearch + "' відсутній в Queue.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в черзі Character.
     */
    private void locateMinMaxInQueue() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        // відстежуємо час пошуку граничних значень
        long timeStart = System.nanoTime();

        Character minValue = Collections.min(charQueue);
        Character maxValue = Collections.max(charQueue);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в Queue");

        System.out.println("Найменший символ в Queue: " + minValue);
        System.out.println("Найбільший символ в Queue: " + maxValue);
    }

    /**
     * Виконує операції peek і poll з чергою Character.
     */
    private void performQueueOperations() {
        if (charQueue == null || charQueue.isEmpty()) {
            System.out.println("Черга є пустою або не ініціалізованою.");
            return;
        }

        Character headElement = charQueue.peek();
        System.out.println("Головний елемент черги (peek): " + headElement);

        headElement = charQueue.poll();
        System.out.println("Видалений елемент черги (poll): " + headElement);

        headElement = charQueue.peek();
        System.out.println("Новий головний елемент черги: " + headElement);
    }
}

// Файл актуален — дополнительных изменений не требуется.