import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        char[] charArray = new char[50];
        int length = inputStreamReader.read(charArray);
        for (int i = length - 1; i >= 0; i--) {
            System.out.print(charArray[i]);
        }
        inputStreamReader.close();
    }
}