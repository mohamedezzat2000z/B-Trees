import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        // SearchEngine sEngine = new SearchEngine();
        // sEngine.indexWebPage("./files/wiki_00");
        // sEngine.indexDirectory("./files");
        // sEngine.deleteWebPage("./Tests/wiki_00");
        // List<ISearchResult> res = sEngine.searchByWordWithRanking("signed");
        // for (int i = 0; i < res.size(); i++) {
        // ISearchResult r = res.get(i);
        // System.out.println("found in ID : " + r.getId() + " with frequency :" +
        // r.getRank());
        // }

        /*
         * List<ISearchResult> res2 = sEngine.searchByWordWithRanking("at");
         * 
         * for (int i = 0; i < res2.size(); i++) {
         * ISearchResult r = res2.get(i);
         * System.out.println("found in ID : " + r.getId() + " with frequency :" +
         * r.getRank());
         * }
         */
         BtreeBenchmark.benchTree(50, "bench 50");
        //SearchEngineBenchmark.benchSearch("is", "is not a sign", "bench is");

    }
}
