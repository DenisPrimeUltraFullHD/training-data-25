import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Клас BasicDataOperationUsingSet реалізує операції з множиною TreeSet для Character.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataAnalysis()} - Запускає аналіз даних.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив Character.</li>
 *   <li>{@link #findInArray()} - Пошук значення в масиві Character.</li>
 *   <li>{@link #locateMinMaxInArray()} - Знаходить граничні значення в масиві.</li>
 *   <li>{@link #findInSet()} - Пошук значення в множині Character.</li>
 *   <li>{@link #locateMinMaxInSet()} - Знаходить мінімальне і максимальне значення в множині.</li>
 *   <li>{@link #analyzeArrayAndSet()} - Аналізує елементи масиву та множини.</li>
 * </ul>
 */
public class BasicDataOperationUsingSet {
    Character charValueToSearch;
    Character[] charArray;
    Set<Character> charSet = new TreeSet<>();

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param charValueToSearch Значення для пошуку
     * @param charArray Масив Character
     */
    BasicDataOperationUsingSet(Character charValueToSearch, Character[] charArray) {
        this.charValueToSearch = charValueToSearch;
        this.charArray = charArray;
        this.charSet = new TreeSet<>(Arrays.asList(charArray));
    }
    
    /**
     * Запускає комплексний аналіз даних з використанням множини TreeSet.
     * 
     * Метод завантажує дані, виконує операції з множиною та масивом Character.
     */
    public void executeDataAnalysis() {
        // спочатку аналізуємо множину символів
        findInSet();
        locateMinMaxInSet();
        analyzeArrayAndSet();

        // потім обробляємо масив
        findInArray();
        locateMinMaxInArray();

        performArraySorting();

        findInArray();
        locateMinMaxInArray();

        // зберігаємо відсортований масив до файлу
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
    }

    /**
     * Упорядковує масив об'єктів Character за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    private void performArraySorting() {
        long timeStart = System.nanoTime();
        Arrays.sort(charArray);
        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву символів");
    }

    /**
     * Здійснює пошук конкретного значення в масиві символів.
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
     * Визначає найменше та найбільше значення в масиві Character.
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
     * Здійснює пошук конкретного значення в множині символів.
     */
    private void findInSet() {
        long timeStart = System.nanoTime();
        boolean elementExists = this.charSet.contains(charValueToSearch);
        PerformanceTracker.displayOperationTime(timeStart, "пошук символа в TreeSet");
        if (elementExists) {
            System.out.println("Символ '" + charValueToSearch + "' знайдено в TreeSet");
        } else {
            System.out.println("Символ '" + charValueToSearch + "' відсутній в TreeSet.");
        }
    }

    /**
     * Визначає найменше та найбільше значення в множині Character.
     */
    private void locateMinMaxInSet() {
        if (charSet == null || charSet.isEmpty()) {
            System.out.println("TreeSet є пустим або не ініціалізованим.");
            return;
        }
        long timeStart = System.nanoTime();
        Character minValue = Collections.min(charSet);
        Character maxValue = Collections.max(charSet);
        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в TreeSet");
        System.out.println("Найменший символ в TreeSet: " + minValue);
        System.out.println("Найбільший символ в TreeSet: " + maxValue);
    }

    /**
     * Аналізує та порівнює елементи масиву та множини.
     */
    private void analyzeArrayAndSet() {
        System.out.println("Кiлькiсть елементiв в масивi: " + (charArray == null ? 0 : charArray.length));
        System.out.println("Кiлькiсть елементiв в TreeSet: " + (charSet == null ? 0 : charSet.size()));

        boolean allElementsPresent = true;
        for (Character ch : charArray) {
            if (!charSet.contains(ch)) {
                allElementsPresent = false;
                break;
            }
        }

        if (allElementsPresent) {
            System.out.println("Всi елементи масиву наявні в TreeSet.");
        } else {
            System.out.println("Не всi елементи масиву наявні в TreeSet.");
        }
    }
}