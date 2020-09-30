import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        reader.close();
    }

    static int methodWithReadLineSplit(BufferedReader reader) throws IOException {
        String[] words = reader.readLine().split("\\s+");
        return words.length;
    }

    static int methodWithLoop(BufferedReader reader) throws IOException {
        boolean wasLastSpace = true;
        int symbol;
        int wordCount = 0;
        while ((symbol = reader.read()) != -1) {
            if (((char) symbol) == ' ') {
                wasLastSpace = true;
            } else if (wasLastSpace) {
                wordCount++;
                wasLastSpace = false;
            }
        }
        return wordCount;
    }
}