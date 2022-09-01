package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class EfficientDocumentGrader {
    public static void main(final String[] args) {
        try {
            System.out.println("Sentences, words, and syllables:");
            final BufferedReader br = new BufferedReader(new FileReader("test_cases/mod2TestCases.txt"));
            String line;
            final PrintWriter out = new PrintWriter("grader_output/module2.part1.out", "utf-8");
            while ((line = br.readLine()) != null) {
                final EfficientDocument doc = new EfficientDocument(line);
                final String result = doc.getNumSentences() + " " + doc.getNumWords() + " " + doc.getNumSyllables() + " ";
                System.out.print(result);
                out.print(result);
            }
            out.print("\n");
            out.close();
            br.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
