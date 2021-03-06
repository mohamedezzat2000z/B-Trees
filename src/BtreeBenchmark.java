import java.io.FileWriter;
import java.io.IOException;

public class BtreeBenchmark {

  /**
   * Bench marking method
   * 
   * @param size size of the hashtables to be created
   */

  static String getAlphaNumericString() {

    // chose a Character random from this String
    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "0123456789"
        + "abcdefghijklmnopqrstuvxyz";

    // create StringBuffer size of AlphaNumericString
    int n = (int) Math.floor(Math.random() * (20 - 1 + 1) + 1);
    StringBuilder sb = new StringBuilder(n);
    for (int i = 0; i < n; i++) {

      // generate a random number between
      // 0 to AlphaNumericString variable length
      int index = (int) (AlphaNumericString.length()
          * Math.random());

      // add Character one by one in end of sb
      sb.append(AlphaNumericString
          .charAt(index));
    }

    return sb.toString();
  }

  public static void benchTree(int size, String sent) {
    Integer[] key = new Integer[size];
    String[] value = new String[size];
    long[] time = new long[10];
    long[] time2 = new long[10];
    for (int i = 0; i < 10; i++) {
      BTree<Integer, String> dummy = new BTree<Integer, String>(3);
      for (int j = 0; j < size; j++) {
        key[j] = Integer.valueOf((int) Math.floor(Math.random() * (2147483647 + 1) + 1));
        value[j] = getAlphaNumericString();
      }

      long startTime = System.nanoTime();
      for (int j = 0; j < size; j++) {
        dummy.insert(key[j], value[j]);
      }
      long endtime = System.nanoTime();

      time[i] = endtime - startTime;

      startTime = System.nanoTime();
      for (int j = 0; j < size; j++) {
        dummy.delete(key[j]);
      }
      endtime = System.nanoTime();
      time2[i] = endtime - startTime;
    }

    try {
      FileWriter csvWriter = new FileWriter("./benchTree/" + sent + ".csv");
      csvWriter.append("trial number");
      csvWriter.append(",");
      for (int i = 0; i < 10; i++) {
        csvWriter.append(String.valueOf(i));
        csvWriter.append(",");
      }

      csvWriter.append("Average");
      csvWriter.append("\ninsertion time n =" + size);
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

      csvWriter.append("\ndeletion time n = " + size);
      csvWriter.append(",");
      for (int i = 0; i < 10; i++) {
        csvWriter.append(String.valueOf(time2[i]));
        csvWriter.append(",");

      }

      long sum2 = 0;
      for (int i = 0; i < 10; i++) {
        sum2 = sum2 + time2[i];
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