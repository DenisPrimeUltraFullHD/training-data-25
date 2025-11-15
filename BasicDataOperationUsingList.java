import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Клас BasicDataOperationUsingList реалізує операції з колекцією Vector<Character>.
 * 
 * <p>Методи класу:</p>
 * <ul>
 *   <li>{@link #executeDataOperations()} - Виконує комплекс операцій з символами.</li>
 *   <li>{@link #performArraySorting()} - Упорядковує масив символів.</li>
 *   <li>{@link #findInArray()} - Пошук символа в масиві.</li>
 *   <li>{@link #locateMinMaxInArray()} - Визначає мін/макс в масиві.</li>
 *   <li>{@link #sortList()} - Сортує Vector<Character>.</li>
 *   <li>{@link #findInList()} - Пошук в Vector.</li>
 *   <li>{@link #locateMinMaxInList()} - Мін/макс в Vector.</li>
 * </ul>
 */
public class BasicDataOperationUsingList {
    private Character charValueToSearch;
    private Character[] charArray;
    private Vector<Character> charList;

    /**
     * Конструктор, який iнiцiалiзує об'єкт з готовими даними.
     * 
     * @param charValueToSearch Значення для пошуку
     * @param charArray Масив Character
     */
    BasicDataOperationUsingList(Character charValueToSearch, Character[] charArray) {
        this.charValueToSearch = charValueToSearch;
        this.charArray = charArray;
        this.charList = new Vector<>(Arrays.asList(charArray));
        System.out.println("\n✅ BasicDataOperationUsingList ініціалізований");
        System.out.println("   Розмір Vector: " + charList.size());
        System.out.println("   Розмір масиву: " + charArray.length);
        System.out.println("   Символ для пошуку: '" + charValueToSearch + "'");
    }
    
    /**
     * Виконує комплексні операції з структурами даних.
     * 
     * Метод завантажує масив і список об'єктів Character, 
     * здійснює сортування та пошукові операції.
     */
    public void executeDataOperations() {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│    ОПЕРАЦІЇ З VECTOR<CHARACTER>             │");
        System.out.println("└─────────────────────────────────────────────┘");
        
        // робота з Vector
        System.out.println("\n🔎 ЕТАП 1: ПОШУК І ПОШУК МІН/МАКС (до сортування)");
        findInList();
        locateMinMaxInList();
        
        System.out.println("\n📊 ЕТАП 2: СОРТУВАННЯ VECTOR");
        sortList();
        
        System.out.println("\n🔎 ЕТАП 3: ПОШУК І ПОШУК МІН/МАКС (після сортування Vector)");
        findInList();
        locateMinMaxInList();

        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│    ОПЕРАЦІЇ З МАСИВОМ CHARACTER[]           │");
        System.out.println("└─────────────────────────────────────────────┘");

        // потім масив
        System.out.println("\n🔎 ЕТАП 1: ПОШУК І ПОШУК МІН/МАКС (до сортування)");
        findInArray();
        locateMinMaxInArray();

        System.out.println("\n📊 ЕТАП 2: СОРТУВАННЯ МАСИВУ");
        performArraySorting();
        
        System.out.println("\n🔎 ЕТАП 3: ПОШУК І ПОШУК МІН/МАКС (після сортування)");
        findInArray();
        locateMinMaxInArray();

        System.out.println("\n💾 ЕТАП 4: ЗБЕРЕЖЕННЯ ВІДСОРТОВАНОГО МАСИВУ");
        // зберігаємо відсортований масив до окремого файлу
        DataFileHandler.writeArrayToFile(charArray, BasicDataOperation.PATH_TO_DATA_FILE + ".sorted");
        System.out.println("✅ Масив успішно збережено в файл");
    }

    /**
     * Упорядковує масив об'єктів LocalDateTime за зростанням.
     * Фіксує та виводить тривалість операції сортування в наносекундах.
     */
    void performArraySorting() {
        long timeStart = System.nanoTime();

        Arrays.sort(charArray);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування масиву символів");
    }

    /**
     * Здійснює пошук конкретного значення в масиві дати та часу.
     */
    void findInArray() {
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
     * Визначає найменше та найбільше значення в масиві дати та часу.
     */
    void locateMinMaxInArray() {
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
     * Шукає конкретне значення дати та часу в колекції ArrayList.
     */
    void findInList() {
        long timeStart = System.nanoTime();

        int position = Collections.binarySearch(this.charList, charValueToSearch);

        PerformanceTracker.displayOperationTime(timeStart, "пошук символа в Vector");        

        if (position >= 0) {
            System.out.println("Символ '" + charValueToSearch + "' знайдено в Vector за позицією: " + position);
        } else {
            System.out.println("Символ '" + charValueToSearch + "' відсутній в Vector.");
        }
    }

    /**
     * Визначає найменше і найбільше значення в колекції ArrayList з датами.
     */
    void locateMinMaxInList() {
        if (charList == null || charList.isEmpty()) {
            System.out.println("Vector є пустим або не ініціалізованим.");
            return;
        }

        long timeStart = System.nanoTime();

        Character minValue = Collections.min(charList);
        Character maxValue = Collections.max(charList);

        PerformanceTracker.displayOperationTime(timeStart, "визначення мiнiмального i максимального символу в Vector");

        System.out.println("Найменший символ в Vector: " + minValue);
        System.out.println("Найбільший символ в Vector: " + maxValue);
    }

    /**
     * Упорядковує колекцію List з об'єктами LocalDateTime за зростанням.
     * Відстежує та виводить час виконання операції сортування.
     */
    void sortList() {
        long timeStart = System.nanoTime();

        Collections.sort(charList);

        PerformanceTracker.displayOperationTime(timeStart, "упорядкування Vector символiв");
    }
}