import java.util.ArrayList;
import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V> {

    List<K> keys = new ArrayList<K>();
    List<V> value = new ArrayList<V>();
    List<IBTreeNode<K, V>> childs = new ArrayList<IBTreeNode<K, V>>();
    int numberOfKeys;
    int lastindex;
    Boolean last; /* to keep track wherther to put the new array at start or last */

    @Override
    public int getNumOfKeys() {
        return this.keys.size();
    }

    @Override
    public void setNumOfKeys(int numOfKeys) {
        this.numberOfKeys = numOfKeys;
    }

    @Override
    public boolean isLeaf() {
        if (childs.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void setLeaf(boolean isLeaf) {
    }

    @Override
    public List<K> getKeys() {
        return this.keys;
    }

    @Override
    public void setKeys(List<K> keys) {
        if (keys.size() == 1) {
            Boolean put = false;
            int i = 0;
            for (i = 0; i < this.keys.size(); i++) {
                if (keys.get(0).compareTo(this.keys.get(i)) < 0) {
                    this.lastindex = i;
                    this.keys.add(i, keys.get(0));
                    put = true;
                    break;
                }
            }
            if (put == false) {
                this.lastindex = this.keys.size();
                this.keys.add(keys.get(0));
                this.last=true;
            }
            if(i==0){
                this.last = false;
            }
        } else if ((this.keys.size() > 0) && (this.keys.get(0).compareTo(keys.get(keys.size() - 1))) > 0) {
            this.last = false;
            List<K> temp = keys;
            temp.addAll(this.keys);
            this.keys = temp;
        } else {
            this.keys.addAll(keys);
            this.last = true;
        }
        

    }

    @Override
    public List<V> getValues() {
        return this.value;
    }

    @Override
    public void setValues(List<V> values) {
        if (values.size() == 1) {
            this.value.add(this.lastindex, values.get(0));
        } else if (this.last == true) {
            this.value.addAll(values);
        } else {
            List<V> temp = values;
            temp.addAll(this.value);
            this.value = temp;
        }


    }

    @Override
    public List<IBTreeNode<K, V>> getChildren() {
        return this.childs;
    }

    @Override
    public void setChildren(List<IBTreeNode<K, V>> children) {
        if (children.size() == 1) {
            if (children.get(0).getKeys().get(0).compareTo(this.keys.get(this.lastindex)) > 0 && childs.size() > 0) {
                this.childs.add(this.lastindex + 1, children.get(0));
            }
            else if(this.childs.size()==0){
                this.childs.add(children.get(0));
            } 
            else {
                if (this.childs.size() > 0 &&  this.keys.size()<this.childs.size()) {
                    this.childs.remove(this.lastindex);
                }
                this.childs.add(this.lastindex, children.get(0));
            }
        } else if (this.last == true) {
            this.childs.addAll(children);
        } else {
            List<IBTreeNode<K, V>> temp = children;
            temp.addAll(this.childs);
            this.childs = temp;
        }

    }

}
