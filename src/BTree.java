import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        Stack<IBTreeNode<K, V>> transver= new Stack<IBTreeNode<K, V>>();
        IBTreeNode<K, V> temp=root;
        while(!temp.isLeaf()){
            int i=0;
            List<K> tempkeys= temp.getKeys();
          for(i=0;i< tempkeys.size();i++){
            if(key.compareTo(tempkeys.get(i))<0){
                break;
            }
          }
          transver.push(temp);
          temp=temp.getChildren().get(i);
        }
        List<K> addkey=new ArrayList<K>();
        addkey.add(key);
        List<V> addValue=new ArrayList<V>();
        addValue.add(value);
        temp.setKeys(addkey);
        temp.setValues(addValue);
        
        while(temp.getNumOfKeys() == 2*degree-1){
            List<K> tempkeys=temp.getKeys();
            List<V> tempValue=temp.getValues();
            List<IBTreeNode<K,V>> tempchilds= new ArrayList<IBTreeNode<K,V>>();

            List<K> firstkey=new ArrayList<K>();
            List<V> firstvalue=new ArrayList<V>();
            List<IBTreeNode<K,V>> firstchilds= new ArrayList<IBTreeNode<K,V>>();

            List<K> midkey=new ArrayList<K>();
            List<V> midvalue=new ArrayList<V>();

            List<K> lastkey=new ArrayList<K>();
            List<V> lastvalue=new ArrayList<V>();
            List<IBTreeNode<K,V>> secondchilds= new ArrayList<IBTreeNode<K,V>>();

            int mid=tempkeys.size();
            for(int i=0;i< tempkeys.size();i++){
                if(i<mid){
                    firstkey.add(tempkeys.get(i));
                    firstvalue.add(tempValue.get(i));
                    if(!temp.isLeaf()){
                        firstchilds.add(tempchilds.get(i));
                    }
                }else if(i==mid){
                    midkey.add(tempkeys.get(i));
                    midvalue.add(tempValue.get(i));
                    if(!temp.isLeaf()){
                        firstchilds.add(tempchilds.get(i));
                    }
                }else{
                    lastkey.add(tempkeys.get(i));
                    lastvalue.add(tempValue.get(i));
                    if(!temp.isLeaf()){
                        secondchilds.add(tempchilds.get(i));
                    }
                }
            }
            IBTreeNode<K, V> left= new BTreeNode<K, V>();
            IBTreeNode<K, V> right= new BTreeNode<K, V>();
            left.setKeys(firstkey);
            left.setValues(firstvalue);
            left.setChildren(firstchilds);
            right.setKeys(lastkey);
            right.setValues(lastvalue);
            right.setChildren(secondchilds);

            if(!transver.isEmpty()){
                IBTreeNode<K, V> temp2=transver.peek();
                temp2.setKeys(midkey);
                temp2.setValues(midvalue);
                List<IBTreeNode<K,V>> small= new ArrayList<IBTreeNode<K,V>>();
                small.add(left);
                List<IBTreeNode<K,V>> big= new ArrayList<IBTreeNode<K,V>>();
                big.add(right);
                temp2.setChildren(small);
                temp2.setChildren(big);
                temp=transver.pop();
            }
            else{
                IBTreeNode<K, V> root= new BTreeNode<K, V>();
                root.setKeys(midkey);
                root.setValues(midvalue);
                List<IBTreeNode<K,V>> small= new ArrayList<IBTreeNode<K,V>>();
                small.add(left);
                List<IBTreeNode<K,V>> big= new ArrayList<IBTreeNode<K,V>>();
                big.add(right);
                root.setChildren(small);
                root.setChildren(big);
            }

        }
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
