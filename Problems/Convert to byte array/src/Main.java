import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.Arrays;

class Converter {

    public static char[] convert(String[] words) throws IOException {
        CharArrayWriter writer = new CharArrayWriter();
        for (String word : words) {
            writer.write(word);
        }
        return writer.toCharArray();
    }
}