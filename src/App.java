public class App {
    public static void main(String[] args) throws Exception {
       BTree<Integer,String> T=new BTree<Integer,String>(3);
        T.insert(2,"hi");
        T.insert(99,"fine");
        T.insert(9,"jest");
        T.insert(80,"insure");
        T.insert(16,"excess");
        T.insert(43,"depressed");
        T.insert(81,"compose");
        T.insert(37,"machinery");
        T.insert(82,"normal");
        T.insert(85,"clue");
        T.insert(91,"resign");
        T.insert(84,"crash");
        T.insert(55,"reporter");
        T.insert(105,"interrupt");
        T.insert(61,"jealous");
        T.insert(51,"era");
        T.insert(95,"administration");
        T.insert(58,"gap");
        T.insert(101,"fuel");
        T.insert(41,"spell");
        T.insert(71,"reputation");
        T.delete(99);
        T.delete(105);
        T.delete(80);
        T.delete(61);
        T.transver(T.root);
    }
}
