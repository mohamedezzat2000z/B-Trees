public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {

    IBTreeNode<K, V> root= new BTreeNode<K, V>();
    int degree;
    BTree(int degree){
        this.degree=degree;
    }
    @Override
    public int getMinimumDegree() {

        return this.degree;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return root;
    }

    @Override
    public void insert(K key, V value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public V search(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(K key) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
