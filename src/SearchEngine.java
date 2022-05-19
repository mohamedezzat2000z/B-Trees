import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class SearchEngine implements ISearchEngine {
    private IBTree<Integer, String> webPageTree = new BTree<Integer, String>(2);
    private DocumentBuilder dBuilder;

    public SearchEngine() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            this.dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void indexWebPage(String filePath) {
        File webPage = new File(filePath);
        try {
            Document doc = dBuilder.parse(webPage);
            // IBTreeNode node =
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("doc");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                Element e = (Element) node;
                Integer articleID = Integer.valueOf(Integer.parseInt(e.getAttribute("id")));
                // System.out.println(e.getTextContent());
                webPageTree.insert(articleID, e.getTextContent());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void indexDirectory(String directoryPath) {
        // TODO Auto-generated method stub
        File directory = new File(directoryPath);
        File[] filesArray = directory.listFiles();
        for (File f : filesArray) {
            if (f.isDirectory()) {
                indexDirectory(f.getAbsolutePath());
            } else {
                this.indexWebPage(f.getAbsolutePath());
            }
        }

    }

    @Override
    public void deleteWebPage(String filePath) {
        // TODO Auto-generated method stub
        File webPage = new File(filePath);
        try {
            Document doc = dBuilder.parse(webPage);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("doc");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                Element e = (Element) node;
                Integer articleID = Integer.valueOf(Integer.parseInt(e.getAttribute("id")));
                // System.out.println(e.getTextContent());
                webPageTree.delete(articleID);

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
        IBTreeNode<Integer, String> root = webPageTree.getRoot();
        appendToResultsList(searchResults, root, word);
        return searchResults;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        String[] words = sentence.split(" ");
        List<List<ISearchResult>> searchresultsPerWord = new ArrayList<List<ISearchResult>>();
        for (int i = 0; i < words.length; i++) {
            searchresultsPerWord.add(searchByWordWithRanking(words[i]));
        }
        for (int i = 0; i < searchresultsPerWord.get(0).size(); i++) {
            int[] frequencies = new int[searchresultsPerWord.get(0).size()];
            for (int j = 0; j < searchresultsPerWord.size(); j++) {

                searchresultsPerWord.get(j).get(i);
            }
        }
        return null;
    }

    private void appendToResultsList(List<ISearchResult> list, IBTreeNode<Integer, String> root, String word) {

        List<IBTreeNode<Integer, String>> rootchildren = root.getChildren();
        List<String> values = root.getValues();
        List<Integer> keys = root.getKeys();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i).contains(word)) {
                Pattern p = Pattern.compile(word);
                Matcher m = p.matcher(values.get(i));
                int count = 0;
                while (m.find())
                    count += 1;
                ISearchResult res = new SearchResult();
                res.setId(keys.get(i).toString());
                res.setRank(count);
                list.add(res);
            }
        }
        for (int i = 0; i < rootchildren.size(); i++) {
            appendToResultsList(list, rootchildren.get(i), word);
        }

    }
}
