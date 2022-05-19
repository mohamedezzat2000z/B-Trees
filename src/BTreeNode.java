import java.util.ArrayList;
import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K,V> {

    List<K> keys= new ArrayList<K>();
    List<V> value= new ArrayList<V>();
    BTreeNode<K,V> parent;
    List<IBTreeNode<K,V>> childs= new ArrayList<IBTreeNode<K,V>>();
    int numberOfKeys;
    Boolean last; /*to keep track wherther to put the new array at start or last */

    @Override
    public int getNumOfKeys() {
        return this.keys.size();
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {
      this.numberOfKeys=numOfKeys;
    }

    @Override
    public boolean isLeaf() {
        if(childs.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public void setLeaf(boolean isLeaf) {
         // TODO Auto-generated method stub
    }

    @Override
    public List<K> getKeys() {
        return this.keys;
    }

    @Override
    public void setKeys(List<K> keys) {
        if( (this.keys.size() > 0)  &&  ( this.keys.get(0).compareTo(keys.get( keys.size()-1))) > 0 ){
            this.last=false;
            List<K> temp = keys;
            temp.addAll(this.keys);
            this.keys=temp;
        }else{
            this.keys.addAll(keys);
            this.last=true;
        }
        
    }

    @Override
    public List<V> getValues() {
        return this.value;
    }

    @Override
    public void setValues(List<V> values) {
      if(this.last=true){
            this.value.addAll(values);
      }else{
        List<V> temp = values;
        temp.addAll(this.value);
        this.value=temp;
      }
        
    }

    @Override
    public List<IBTreeNode<K, V>> getChildren() {
        return this.childs;
    }

    @Override
    public void setChildren(List<IBTreeNode<K, V>> children) {
        if(this.last=true){
            this.childs.addAll(children);
      }else{
        List<IBTreeNode<K, V>> temp = children;
        temp.addAll(this.childs);
        this.childs=temp;
      }
    }
    
}
