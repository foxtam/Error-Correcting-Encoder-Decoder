package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {

    static final Random generator = new Random();

    public static void main(String[] args) throws IOException {
        byte[] bytes = readBytesFromFile("send.txt");
        byte[] spoiledBytes = spoilByteArray(bytes);
        writeBytesToFile("received.txt", spoiledBytes);
    }

    static byte[] readBytesFromFile(String fileName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            return fileInputStream.readAllBytes();
        }
    }

    static void writeBytesToFile(String fileName, byte[] bytes) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(bytes);
        }
    }

    static byte[] spoilByteArray(byte[] array) {
        byte[] spoiledArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            spoiledArray[i] = spoilByte(array[i]);
        }
        return spoiledArray;
    }

    static byte spoilByte(byte b) {
        return changeOneRandomBite(b);
    }

    static byte changeOneRandomBite(byte b) {
        int power = generator.nextInt(8);
        int mask = (int) Math.pow(2, power);
        return ((byte) (b ^ mask));
    }
}
