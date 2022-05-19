public class App {
    public static void main(String[] args) throws Exception {
        SearchEngine sEngine = new SearchEngine();
        // sEngine.indexWebPage("./files/wiki_00");
        sEngine.indexDirectory("./files");
    }
}
