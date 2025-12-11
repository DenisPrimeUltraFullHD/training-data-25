import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Клас DataFileHandler управляє роботою з файлами даних Character.
 */
public class DataFileHandler {
    /**
     * Завантажує масив Character з файлу (кожен рядок — символ).
     */
    public static Character[] loadArrayFromFile(String filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            return fileReader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.charAt(0))
                    .collect(Collectors.toList())
                    .toArray(new Character[0]);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return new Character[0];
        }
    }

    /**
     * Збереження масиву Character у файл.
     */
    public static void writeArrayToFile(Character[] charArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            String content = Arrays.stream(charArray)
                    .map(String::valueOf)
                    .collect(Collectors.joining(System.lineSeparator()));
            
            fileWriter.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
