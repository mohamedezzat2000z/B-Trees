import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {

    IBTreeNode<K, V> root = new BTreeNode<K, V>();
    int degree;

    BTree(int degree) {
        this.degree = degree;
    }

    @Override
    public int getMinimumDegree() {
        return this.degree;
    }

    @Override
    public IBTreeNode<K, V> getRoot() {
        return this.root;
    }

    @Override
    public void insert(K key, V value) {
        if (search(key) != null)
            return;
        Stack<IBTreeNode<K, V>> transver = new Stack<IBTreeNode<K, V>>();
        IBTreeNode<K, V> temp = this.root;
        while (!temp.isLeaf()) {
            int i = 0;
            List<K> tempkeys = temp.getKeys();
            for (i = 0; i < tempkeys.size(); i++) {
                if (key.compareTo(tempkeys.get(i)) < 0) {
                    break;
                }
            }
            transver.push(temp);
            temp = temp.getChildren().get(i);
        }
        List<K> addkey = new ArrayList<K>();
        addkey.add(key);
        List<V> addValue = new ArrayList<V>();
        addValue.add(value);
        temp.setKeys(addkey);
        temp.setValues(addValue);
        temp.setNumOfKeys(temp.getNumOfKeys());
        while (temp.getNumOfKeys() > 2 * degree - 1) {

            List<K> tempkeys = temp.getKeys();
            List<V> tempValue = temp.getValues();
            List<IBTreeNode<K, V>> tempchilds = temp.getChildren();

            List<K> firstkey = new ArrayList<K>();
            List<V> firstvalue = new ArrayList<V>();
            List<IBTreeNode<K, V>> firstchilds = new ArrayList<IBTreeNode<K, V>>();

            List<K> midkey = new ArrayList<K>();
            List<V> midvalue = new ArrayList<V>();

            List<K> lastkey = new ArrayList<K>();
            List<V> lastvalue = new ArrayList<V>();
            List<IBTreeNode<K, V>> secondchilds = new ArrayList<IBTreeNode<K, V>>();

            int mid = tempkeys.size() / 2;
            int i;
            for (i = 0; i < tempkeys.size(); i++) {
                if (i < mid) {
                    firstkey.add(tempkeys.get(i));
                    firstvalue.add(tempValue.get(i));
                    if (!temp.isLeaf()) {
                        firstchilds.add(tempchilds.get(i));
                    }
                } else if (i == mid) {
                    midkey.add(tempkeys.get(i));
                    midvalue.add(tempValue.get(i));
                    if (!temp.isLeaf()) {
                        firstchilds.add(tempchilds.get(i));
                    }
                } else {
                    lastkey.add(tempkeys.get(i));
                    lastvalue.add(tempValue.get(i));
                    if (!temp.isLeaf()) {
                        secondchilds.add(tempchilds.get(i));
                    }
                }
            }
            if (!temp.isLeaf()) {
                secondchilds.add(tempchilds.get(i));
            }
            IBTreeNode<K, V> left = new BTreeNode<K, V>();
            IBTreeNode<K, V> right = new BTreeNode<K, V>();
            left.setKeys(firstkey);
            left.setValues(firstvalue);
            left.setChildren(firstchilds);
            right.setKeys(lastkey);
            right.setValues(lastvalue);
            right.setChildren(secondchilds);
            left.setNumOfKeys(left.getNumOfKeys());
            right.setNumOfKeys(right.getNumOfKeys());
            if (!transver.isEmpty()) {
                IBTreeNode<K, V> temp2 = transver.peek();
                temp2.setKeys(midkey);
                temp2.setValues(midvalue);
                List<IBTreeNode<K, V>> small = new ArrayList<IBTreeNode<K, V>>();
                small.add(left);
                List<IBTreeNode<K, V>> big = new ArrayList<IBTreeNode<K, V>>();
                big.add(right);
                temp2.setChildren(small);
                temp2.setChildren(big);
                temp2.setNumOfKeys(temp2.getNumOfKeys());
                temp = transver.pop();
            } else {
                this.root = new BTreeNode<K, V>();
                this.root.setKeys(midkey);
                this.root.setValues(midvalue);

                List<IBTreeNode<K, V>> small = new ArrayList<IBTreeNode<K, V>>();
                small.add(left);
                List<IBTreeNode<K, V>> big = new ArrayList<IBTreeNode<K, V>>();
                big.add(right);

                this.root.setChildren(small);
                this.root.setChildren(big);
                this.root.setNumOfKeys(this.root.getNumOfKeys());
                temp = this.root;

            }

        }
    }

    @Override
    public V search(K key) {
        IBTreeNode<K, V> temp = this.root;
        while (temp != null) {
            int i = 0;
            List<K> tempkeys = temp.getKeys();
            for (i = 0; i < tempkeys.size(); i++) {
                if (key.compareTo(tempkeys.get(i)) == 0) {
                    return temp.getValues().get(i);
                }
                if (key.compareTo(tempkeys.get(i)) < 0) {
                    break;
                }
            }
            if (temp.getChildren().size() > 0) {
                temp = temp.getChildren().get(i);
            } else {
                temp = null;
            }
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        Stack<IBTreeNode<K, V>> transver = new Stack<IBTreeNode<K, V>>();
        IBTreeNode<K, V> temp = this.root;
        int i = 0;
        Boolean found = false;
        System.out.println(key + " start");
        while (temp != null && !found) {
            List<K> tempkeys = temp.getKeys();
            for (i = 0; i < tempkeys.size(); i++) {
                if (key.compareTo(tempkeys.get(i)) == 0) {
                    found = true;
                    break;
                }
                if (key.compareTo(tempkeys.get(i)) < 0) {
                    break;
                }
            }
            if (temp.getChildren().size() > 0 && found == false) {
                transver.push(temp);
                System.out.println(temp.isLeaf());
                temp = temp.getChildren().get(i);
            } else if (found == false) {
                temp = null;
            }
        }

        if (!found) {
            return false;
        }

        Boolean removed = false;
        while (temp != null) {
            // case 1 in the leaf

            if (temp.isLeaf()) {
                if (!removed) {
                    List<K> tempkeys = temp.getKeys();
                    tempkeys.remove(i);
                    List<V> tempval = temp.getValues();
                    tempval.remove(i);
                    removed = true;
                }
                // enought keys in the node
                if (temp.getNumOfKeys() >= degree - 1 || temp == this.root) {
                    return true;
                } else {
                    // finding the postion of the node in the parent pointer list
                    IBTreeNode<K, V> temp2 = transver.peek();
                    List<IBTreeNode<K, V>> holder = temp2.getChildren();
                    List<K> tempkeys2 = temp2.getKeys();
                    List<V> tempval2 = temp2.getValues();
                    int x;
                    for (x = 0; x < holder.size(); x++) {
                        if (holder.get(x) == temp) {
                            break;
                        }
                    }
                    // borrow from the left sibling
                    if (x > 0 && holder.get(x - 1).getNumOfKeys() > degree - 1) {
                        List<K> put = new ArrayList<>();
                        put.add(tempkeys2.get(x - 1));
                        List<V> putV = new ArrayList<>();
                        putV.add(tempval2.get(x - 1));
                        temp.setKeys(put);
                        temp.setValues(putV);
                        tempkeys2.remove(x - 1);
                        tempval2.remove(x - 1);
                        put = new ArrayList<>();
                        putV = new ArrayList<>();
                        List<K> tempkeys3 = holder.get(x - 1).getKeys();
                        List<V> tempval3 = holder.get(x - 1).getValues();
                        put.add(tempkeys3.get(tempkeys3.size() - 1));
                        putV.add(tempval3.get(tempkeys3.size() - 1));
                        tempkeys3.remove(tempkeys3.size() - 1);
                        tempval3.remove(tempval3.size() - 1);
                        temp2.setKeys(put);
                        temp2.setValues(putV);
                    }
                    // borrow from the right sibling
                    else if (x < holder.size() - 1 && holder.get(x + 1).getNumOfKeys() > degree - 1) {
                        List<K> put = new ArrayList<>();
                        put.add(tempkeys2.get(x));
                        List<V> putV = new ArrayList<>();
                        putV.add(tempval2.get(x));
                        temp.setKeys(put);
                        temp.setValues(putV);
                        tempkeys2.remove(x);
                        tempval2.remove(x);
                        put = new ArrayList<>();
                        putV = new ArrayList<>();
                        List<K> tempkeys3 = holder.get(x + 1).getKeys();
                        List<V> tempval3 = holder.get(x + 1).getValues();
                        put.add(tempkeys3.get(0));
                        putV.add(tempval3.get(0));
                        tempkeys3.remove(0);
                        tempval3.remove(0);
                        temp2.setKeys(put);
                        temp2.setValues(putV);
                    }
                    // merge case
                    else {
                        // merge with left siblling
                        if (x > 0) {
                            List<K> put = new ArrayList<>();
                            put.add(tempkeys2.get(x - 1));
                            List<V> putV = new ArrayList<>();
                            putV.add(tempval2.get(x - 1));
                            temp.setKeys(put);
                            temp.setValues(putV);
                            tempkeys2.remove(x - 1);
                            tempval2.remove(x - 1);
                            temp.setKeys(holder.get(x - 1).getKeys());
                            temp.setValues(holder.get(x - 1).getValues());
                            temp.setChildren(holder.get(x - 1).getChildren());
                            holder.remove(x - 1);
                        }
                        // merge with left siblling
                        else if (x < holder.size() - 1) {
                            List<K> put = new ArrayList<>();
                            put.add(tempkeys2.get(x));
                            List<V> putV = new ArrayList<>();
                            putV.add(tempval2.get(x));
                            temp.setKeys(put);
                            temp.setValues(putV);
                            tempkeys2.remove(x);
                            tempval2.remove(x);
                            temp.setKeys(holder.get(x).getKeys());
                            temp.setValues(holder.get(x).getValues());
                            temp.setChildren(holder.get(x).getChildren());
                            holder.remove(x + 1);
                        }
                    }

                    if (!transver.isEmpty()) {
                        temp = transver.pop();
                    } else {
                        temp = null;
                    }
                }

            } else if (temp == this.root && removed) {
                return true;
            } else {

                if (!removed) {
                    transver.add(temp);
                    IBTreeNode<K, V> temp2 = temp.getChildren().get(i);
                    while (!temp2.isLeaf()) {
                        transver.add(temp2);
                        List<IBTreeNode<K, V>> holder = temp2.getChildren();
                        temp2 = holder.get(holder.size() - 1);
                    }
                    List<K> tempkeys = temp.getKeys();
                    tempkeys.remove(i);
                    List<V> tempval = temp.getValues();
                    tempval.remove(i);
                    List<K> put = new ArrayList<>();
                    List<V> putV = new ArrayList<>();
                    List<K> tempkeys2 = temp2.getKeys();
                    List<V> tempval2 = temp2.getValues();
                    put.add(tempkeys2.get(tempkeys2.size() - 1));
                    putV.add(tempval2.get(tempval2.size() - 1));
                    tempkeys2.remove(tempkeys2.size() - 1);
                    tempval2.remove(tempval2.size() - 1);
                    temp.setKeys(put);
                    temp.setValues(putV);
                    removed = true;
                    temp = temp2;
                } else if (temp.getNumOfKeys() < degree - 1) {
                    // finding the postion of the node in the parent pointer list

                    IBTreeNode<K, V> temp2 = transver.peek();
                    System.out
                            .println(key + "special" + temp.getNumOfKeys() + " number of" + temp.getChildren().size());
                    System.out.println(temp2.getNumOfKeys() + " number of" + temp2.getChildren().size());
                    List<IBTreeNode<K, V>> holder = temp2.getChildren();
                    List<K> tempkeys2 = temp2.getKeys();
                    List<V> tempval2 = temp2.getValues();
                    int x;
                    for (x = 0; x < holder.size(); x++) {
                        if (holder.get(x) == temp) {
                            break;
                        }
                    }
                    // borrow from the left sibling
                    if (x > 0 && holder.get(x - 1).getNumOfKeys() > degree - 1) {
                        System.out.println(key + "boorw left");
                        List<K> put = new ArrayList<>();
                        put.add(tempkeys2.get(x - 1));
                        List<V> putV = new ArrayList<>();
                        putV.add(tempval2.get(x - 1));
                        temp.setKeys(put);
                        temp.setValues(putV);
                        tempkeys2.remove(x - 1);
                        tempval2.remove(x - 1);
                        put = new ArrayList<>();
                        putV = new ArrayList<>();
                        IBTreeNode<K, V> temp3 = holder.get(x - 1);
                        List<IBTreeNode<K, V>> holder2 = temp3.getChildren();
                        List<IBTreeNode<K, V>> putC = new ArrayList<IBTreeNode<K, V>>();
                        List<K> tempkeys3 = temp3.getKeys();
                        List<V> tempval3 = temp3.getValues();
                        put.add(tempkeys3.get(tempkeys3.size() - 1));
                        putV.add(tempval3.get(tempkeys3.size() - 1));
                        tempkeys3.remove(tempkeys3.size() - 1);
                        tempval3.remove(tempval3.size() - 1);
                        holder2.remove(holder2.size() - 1);
                        temp2.setKeys(put);
                        temp2.setValues(putV);
                        putC.add(holder2.get(holder2.size() - 1));
                        temp.setChildren(putC);
                        holder2.remove(holder2.size() - 1);

                    }
                    // borrow from the right sibling
                    else if (x < holder.size() - 1 && holder.get(x + 1).getNumOfKeys() > degree - 1) {
                        System.out.println(key + "boorw right");
                        List<K> put = new ArrayList<>();
                        put.add(tempkeys2.get(x));
                        List<V> putV = new ArrayList<>();
                        putV.add(tempval2.get(x));
                        temp.setKeys(put);
                        temp.setValues(putV);
                        tempkeys2.remove(x);
                        tempval2.remove(x);
                        put = new ArrayList<>();
                        putV = new ArrayList<>();
                        IBTreeNode<K, V> temp3 = holder.get(x + 1);
                        List<IBTreeNode<K, V>> holder2 = temp3.getChildren();
                        List<IBTreeNode<K, V>> putC = new ArrayList<IBTreeNode<K, V>>();
                        List<K> tempkeys3 = temp3.getKeys();
                        List<V> tempval3 = temp3.getValues();
                        put.add(tempkeys3.get(0));
                        putV.add(tempval3.get(0));
                        tempkeys3.remove(0);
                        tempval3.remove(0);
                        temp2.setKeys(put);
                        temp2.setValues(putV);
                        putC.add(holder2.get(0));
                        temp.setChildren(putC);
                        holder2.remove(0);

                    }
                    // merge case
                    else {
                        System.out.println(key + "mergeing");
                        // temp = null;
                        // merge with left siblling
                        if (x > 0) {
                            List<K> put = new ArrayList<>();
                            put.add(tempkeys2.get(x - 1));
                            List<V> putV = new ArrayList<>();
                            putV.add(tempval2.get(x - 1));
                            temp.setKeys(put);
                            temp.setValues(putV);
                            tempkeys2.remove(x - 1);
                            tempval2.remove(x - 1);
                            temp.setKeys(holder.get(x - 1).getKeys());
                            temp.setValues(holder.get(x - 1).getValues());
                            temp.setChildren(holder.get(x - 1).getChildren());
                            holder.remove(x - 1);
                        }
                        // merge with left siblling
                        else if (x < holder.size() - 1) {
                            List<K> put = new ArrayList<>();
                            put.add(tempkeys2.get(x));
                            List<V> putV = new ArrayList<>();
                            putV.add(tempval2.get(x));
                            temp.setKeys(put);
                            temp.setValues(putV);
                            tempkeys2.remove(x);
                            tempval2.remove(x);
                            temp.setKeys(holder.get(x).getKeys());
                            temp.setValues(holder.get(x).getValues());
                            temp.setChildren(holder.get(x).getChildren());
                            holder.remove(x + 1);
                        }

                    }

                    if (!transver.isEmpty()) {
                        temp = transver.pop();
                    } else {
                        temp = null;
                    }

                    System.out
                            .println(key + "special2" + temp.getNumOfKeys() + " number of" + temp.getChildren().size());
                    System.out.println(temp2.getNumOfKeys() + " number of" + temp2.getChildren().size());
                } else {
                    temp = null;
                }
            }
        }
        return true;
    }

    public void transver(IBTreeNode<K, V> root) {
        List<K> tempkeys = root.getKeys();
        List<V> tempValue = root.getValues();
        for (int i = 0; i < tempkeys.size(); i++) {
            System.out.print("-(" + tempkeys.get(i) + "," + tempValue.get(i) + ") --");
        }
        System.out.print(root.getNumOfKeys());
        List<IBTreeNode<K, V>> tempchilds = root.getChildren();
        System.out.println("  number of childes" + tempchilds.size());
        for (int i = 0; i < tempchilds.size(); i++) {
            transver(tempchilds.get(i));
        }

    }
}
