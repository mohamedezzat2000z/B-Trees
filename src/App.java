public class App {
    public static void main(String[] args) throws Exception {
       IBTree<Integer,String> T=new BTree<Integer,String>(3);
        T.insert(5,"hi");
    }
}
