import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class DateTimeExample {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final String TARGET_DATE = "2025-03-13T00:00:00Z";
    
    public static void main(String[] args) throws IOException {
        // Создаем тестовые данные
        LocalDateTime[] dateArray = generateTestData();
        
        // Сохраняем в файл
        saveToFile(dateArray, "LocalDateTime.data");
        
        // Работа с ArrayList
        System.out.println("\n=== ArrayList Operations ===");
        ArrayList<LocalDateTime> arrayList = new ArrayList<>(Arrays.asList(dateArray));
        Collections.sort(arrayList);
        System.out.println("Searching in ArrayList for: " + TARGET_DATE);
        int pos = Collections.binarySearch(arrayList, LocalDateTime.parse(TARGET_DATE, formatter));
        System.out.println("Position in ArrayList: " + pos);
        
        // Работа с LinkedHashSet
        System.out.println("\n=== LinkedHashSet Operations ===");
        LinkedHashSet<LocalDateTime> linkedSet = new LinkedHashSet<>(Arrays.asList(dateArray));
        System.out.println("Searching in LinkedHashSet for: " + TARGET_DATE);
        boolean found = linkedSet.contains(LocalDateTime.parse(TARGET_DATE, formatter));
        System.out.println("Found in LinkedHashSet: " + found);
        
        // Работа с PriorityQueue
        System.out.println("\n=== PriorityQueue Operations ===");
        PriorityQueue<LocalDateTime> priorityQueue = new PriorityQueue<>(Arrays.asList(dateArray));
        System.out.println("Searching in PriorityQueue for: " + TARGET_DATE);
        found = priorityQueue.contains(LocalDateTime.parse(TARGET_DATE, formatter));
        System.out.println("Found in PriorityQueue: " + found);
    }
    
    private static LocalDateTime[] generateTestData() {
        LocalDateTime[] dates = new LocalDateTime[5];
        dates[0] = LocalDateTime.parse("2025-03-13T00:00:00Z", formatter);
        dates[1] = LocalDateTime.parse("2024-01-01T00:00:00Z", formatter);
        dates[2] = LocalDateTime.parse("2025-12-31T23:59:59Z", formatter);
        dates[3] = LocalDateTime.parse("2025-03-14T00:00:00Z", formatter);
        dates[4] = LocalDateTime.parse("2025-03-12T00:00:00Z", formatter);
        return dates;
    }
    
    private static void saveToFile(LocalDateTime[] dates, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (LocalDateTime date : dates) {
                writer.println(date.format(formatter));
            }
        }
    }
}
