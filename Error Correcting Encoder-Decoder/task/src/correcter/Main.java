package correcter;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static final String symbolsToMistake =
            symbolRange('a', 'z') + symbolRange('A', 'Z') + symbolRange('0', '9') + ' ';

    static final Random generator = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String lineWithMistakes = makeMistakes(line, 3);
        System.out.println(lineWithMistakes);
    }

    static String makeMistakes(String source, int mistakeWindowWidth) {
        StringBuilder builder = new StringBuilder(source);
        for (int i = 0; i < builder.length() / mistakeWindowWidth * mistakeWindowWidth; i += mistakeWindowWidth) {
            int randomOffset = generator.nextInt(mistakeWindowWidth);
            char randomSymbol = getRandomSymbolExcept(builder.charAt(i + randomOffset));
            builder.setCharAt(i + randomOffset, randomSymbol);
        }
        return builder.toString();
    }

    static char getRandomSymbolExcept(char exceptSymbol) {
        char randomSymbol;
        do {
            randomSymbol = getRandomSymbol();
        } while (randomSymbol == exceptSymbol);
        return randomSymbol;
    }

    static char getRandomSymbol() {
        int randomIndex = generator.nextInt(symbolsToMistake.length());
        return symbolsToMistake.charAt(randomIndex);
    }

    static String symbolRange(char from, char to) {
        StringBuilder builder = new StringBuilder();
        for (char i = from; i <= to; i++) {
            builder.append(i);
        }
        return builder.toString();
    }
}
