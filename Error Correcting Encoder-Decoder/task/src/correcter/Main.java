package correcter;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static final String symbolsToMistake =
            symbolRange('a', 'z') + symbolRange('A', 'Z') + symbolRange('0', '9') + ' ';

    static final Random generator = new Random();

    static final int mistakeWindowWidth = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userMessage = scanner.nextLine();
        System.out.println(userMessage);

        String tripledMsg = triplingAllSymbols(userMessage);
        System.out.println(tripledMsg);

        String msgWithMistakes = makeMistakes(tripledMsg);
        System.out.println(msgWithMistakes);

        String decodedMsg = decodeIncorrectMsg(msgWithMistakes);
        System.out.println(decodedMsg);
    }

    static String decodeIncorrectMsg(String msg) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length(); i += mistakeWindowWidth) {
            char baseChar = getBaseSymbol(msg.substring(i, i + mistakeWindowWidth));
            builder.append(baseChar);
        }
        return builder.toString();
    }

    static char getBaseSymbol(String text) {
        char[] charArray = text.toCharArray();
        for (char ch : charArray) {
            if (countSymbol(charArray, ch) > 1) return ch;
        }
        throw new IllegalStateException();
    }

    static int countSymbol(char[] charArray, char symbol) {
        int counter = 0;
        for (char ch : charArray) {
            if (symbol == ch) counter++;
        }
        return counter;
    }

    static String triplingAllSymbols(String text) {
        StringBuilder builder = new StringBuilder();
        for (char ch : text.toCharArray()) {
            builder.append(String.valueOf(ch).repeat(mistakeWindowWidth));
        }
        return builder.toString();
    }

    static String makeMistakes(String source) {
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
