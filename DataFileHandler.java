import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас DataFileHandler управляє роботою з файлами даних Character.
 */
public class DataFileHandler {
    /**
     * Завантажує масив Character з файлу (кожен рядок — символ).
     */
    public static Character[] loadArrayFromFile(String filePath) {
        Character[] temporaryArray = new Character[10000];
        int currentIndex = 0;

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = fileReader.readLine()) != null) {
                currentLine = currentLine.trim();
                if (!currentLine.isEmpty()) {
                    // беремо перший кодовий символ рядка
                    temporaryArray[currentIndex++] = currentLine.charAt(0);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Character[] resultArray = new Character[currentIndex];
        System.arraycopy(temporaryArray, 0, resultArray, 0, currentIndex);

        return resultArray;
    }

    /**
     * Збереження масиву Character у файл.
     */
    public static void writeArrayToFile(Character[] charArray, String filePath) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Character ch : charArray) {
                fileWriter.write(ch.toString());
                fileWriter.newLine();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
