import java.io.FileWriter;
import java.io.IOException;

public class SearchEngineBenchmark {

    /**
     * Bench marking method
     * 
     * @param size size of the hashtables to be created
     */

    public static void benchSearch(String word, String sentence, String sent) {
        long[] time = new long[10];
        long[] time2 = new long[10];
        for (int i = 0; i < 10; i++) {
            ISearchEngine dummy = new SearchEngine();
            dummy.indexDirectory("./files");
            long startTime = System.nanoTime();
            dummy.searchByWordWithRanking(word);
            long endtime = System.nanoTime();
            time[i] = endtime - startTime;
            startTime = System.nanoTime();
            dummy.searchByMultipleWordWithRanking(sentence);
            endtime = System.nanoTime();
            time2[i] = endtime - startTime;
        }

        try {
            FileWriter csvWriter = new FileWriter("./benchSearch/" + sent + ".csv");
            csvWriter.append("trial number");
            csvWriter.append(",");
            for (int i = 0; i < 10; i++) {
                csvWriter.append(String.valueOf(i));
                csvWriter.append(",");
            }
            csvWriter.append("Average");

            csvWriter.append("\nBy Word time");
            csvWriter.append(",");
            for (int i = 0; i < 10; i++) {
                csvWriter.append(String.valueOf(time[i]));
                csvWriter.append(",");
            }

            long sum1 = 0;
            for (int i = 0; i < 10; i++) {
                sum1 = sum1 + time[i];
            }
            csvWriter.append(String.valueOf(sum1 / 10));
            csvWriter.append("\n");

            csvWriter.append("By Multiple word time");
            csvWriter.append(",");
            for (int i = 0; i < 10; i++) {
                csvWriter.append(String.valueOf(time2[i]));
                csvWriter.append(",");

            }

            long sum2 = 0;
            for (int i = 0; i < 10; i++) {
                sum2 = sum2 + time[i];
            }
            csvWriter.append(String.valueOf(sum2 / 10));
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
