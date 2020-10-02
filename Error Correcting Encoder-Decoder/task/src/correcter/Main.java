package correcter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main {

    static final String ENCODE = "encode";
    static final String SEND = "send";
    static final String DECODE = "decode";

    static final Random RANDOM = new Random();

    public static void main(String[] args) throws IOException {
        final String sourceFileName = "send.txt";
        final String encodedFileName = "encoded.txt";

        FileEncoder.encode(sourceFileName, encodedFileName);
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
        int power = RANDOM.nextInt(8);
        int mask = (int) Math.pow(2, power);
        return ((byte) (b ^ mask));
    }
}

class FileEncoder {

    public static void encode(String sourceFileName, String destinationFileName) throws IOException {
        encode(new File(sourceFileName), new File(destinationFileName));
    }

    public static void encode(File sourceFile, File destinationFile) throws IOException {
        byte[] content = readBytesFrom(sourceFile);
        byte[] encoded = encodeBytes(content);
        writeBytesTo(destinationFile, encoded);
    }

    private static byte[] readBytesFrom(File sourceFile) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(sourceFile)) {
            return fileInputStream.readAllBytes();
        }
    }

    private static byte[] encodeBytes(byte[] bytes) {
        byte[] bits = splitToBits(bytes);
        int encodedByteArraySize = (int) Math.ceil(bytes.length * 8.0 / 3);
        byte[] encodedBytes = new byte[encodedByteArraySize];
        fillEncodedByteArray(bits, encodedBytes);
        return encodedBytes;
    }

    private static void writeBytesTo(File destinationFile, byte[] bytes) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
            fileOutputStream.write(bytes);
        }
    }

    private static byte[] splitToBits(byte[] bytes) {
        byte[] bites = new byte[bytes.length * 8];
        int index = 0;
        for (byte b : bytes) {
            for (int i = 7; i >= 0; i--) {
                byte mask = (byte) Math.pow(2, i);
                bites[index++] = (mask & b) == 0 ? (byte) 0 : 1;
            }
        }
        return bites;
    }

    private static void fillEncodedByteArray(byte[] bits, byte[] encodedBytes) {
        int index = 0;
        for (; index < encodedBytes.length - 1; index++) {
            encodedBytes[index] = encodeByteFrom3Bits(bits[index * 3], bits[index * 3 + 1], bits[index * 3 + 2]);
        }
        byte bitA = 0, bitB = 0, bitC = 0;
        if (index * 3 < bits.length) bitA = bits[index * 3];
        if (index * 3 + 1 < bits.length) bitB = bits[index * 3 + 1];
        if (index * 3 + 2 < bits.length) {
            bitC = bits[index * 3 + 2];
            assert index * 3 + 3 == bits.length;
        }
        encodedBytes[index] = encodeByteFrom3Bits(bitA, bitB, bitC);
    }

    private static byte encodeByteFrom3Bits(byte biteA, byte biteB, byte biteC) {
        assert biteA == 0 || biteA == 1;
        assert biteB == 0 || biteB == 1;
        assert biteC == 0 || biteC == 1;

        byte result = 0;
        result |= biteA << 7;
        result |= biteA << 6;
        result |= biteB << 5;
        result |= biteB << 4;
        result |= biteC << 3;
        result |= biteC << 2;

        byte parity = ((byte) (biteA ^ biteB ^ biteC));
        result |= parity << 1;
        result |= parity;

        return result;
    }

}