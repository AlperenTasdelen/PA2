import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations, if necessary.
        type = CengNodeType.Internal;
        setParent(parent);
        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    public void addKey(Integer key)
    {
        this.keys.add(key);
    }

    public void addKeyAndChildOrdered(Integer key, CengTreeNode child)
    {
        int properIndex = keyCount();
        for(int i = 0; i < keyCount(); i++){
            if(keyAtIndex(i) > key){
                properIndex = i;
                break;
            }
        }
        this.keys.add(properIndex, key);
        this.children.add(properIndex + 1, child);
    }

    public void addChild(CengTreeNode child)
    {
        this.children.add(child);
    }

    public void removeKeyAtIndex(Integer index)
    {
        this.keys.remove(index.intValue());
    }

    public void removeChildAtIndex(Integer index)
    {
        this.children.remove(index.intValue());
    }

    public void addChildAtIndex(CengTreeNode child, Integer index)
    {
        this.children.add(index, child);
    }

    public void addBookInternal(CengBook book)
    {
        int bookID = book.getBookID();
        if(getAllChildren().get(0).type == CengNodeType.Leaf){
            int properIndex = 0;
            for(int i = 0; i < keyCount(); i++){
                if(keyAtIndex(i) > bookID){
                    break;
                }
                properIndex++;
            }
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) getAllChildren().get(properIndex);
            leaf.addBookLeaf(book);
            if(leaf.bookCount() > 2 * CengTreeNode.order){
                CengTreeNodeLeaf newLeaf = new CengTreeNodeLeaf(this);
                newLeaf.level = leaf.level;
                for(int i = 0; i < leaf.bookCount(); i++){
                    if(i < leaf.bookCount() / 2){
                        
                    }
                    else{
                        newLeaf.addBookLeaf(leaf.bookAtIndex(i));
                        leaf.removeBookAtIndex(i);
                        i--;
                    }
                }
                addKeyAndChildOrdered(newLeaf.bookKeyAtIndex(0), newLeaf);
                if(keys.size() > 2 * CengTreeNode.order){
                    // overflow
                    pushUp();
                }
            }
        }
        else{
            int properIndex = 0;
            for(int i = 0; i < keyCount(); i++){
                if(keyAtIndex(i) > bookID){
                    break;
                }
                properIndex++;
            }
            CengTreeNodeInternal internal = (CengTreeNodeInternal) getAllChildren().get(properIndex);
            internal.addBookInternal(book);
        }
    }

    private void pushUp(){
        if(this.keyCount() <= 2 * CengTreeNode.order){
            return;
        }
        
        //TODO: add keys and children to new internal node
        if(getParent() == null){
            //System.out.println("root pushed up");
            CengTreeNodeInternal left = new CengTreeNodeInternal(this);
            CengTreeNodeInternal right = new CengTreeNodeInternal(this);

            for(int i = 0; i < keyCount(); i++){
                if(i < (keyCount() / 2)){
                    left.addKey(keyAtIndex(i));
                    //removeKeyAtIndex(i);
                    //i--;
                }
                else if(i == (keyCount() / 2)){
                    
                }
                else{
                    right.addKey(keyAtIndex(i));
                    //removeKeyAtIndex(i);
                    //i--;
                }
            }

            for(int i = 0; i < children.size(); i++){
                if(i < children.size() / 2){
                    left.addChild(children.get(i));
                    children.get(i).setParent(left);
                }
                else{
                    right.addChild(children.get(i));
                    children.get(i).setParent(right);
                }
                //removeChildAtIndex(i);
                //i--;
            }
            
            int key = keyAtIndex(keyCount() / 2);

            keys.clear();
            children.clear();

            addKey(key);

            children.add(left);
            children.add(right);

            updateLevels(this, 0);
        }
        else{
            System.out.println("internal pushed up");
            CengTreeNodeInternal parent = (CengTreeNodeInternal) getParent();
            CengTreeNodeInternal newInternal = new CengTreeNodeInternal(parent);
            for(int i = 0; i < keyCount(); i++){
                if(i < keyCount() / 2){
                    
                }
                else if(i == keyCount() / 2){
                    //newInternal.addKey(keyAtIndex(i));
                    //removeKeyAtIndex(i);
                    //i--;
                    parent.addKey(keyAtIndex(i));
                }
                else{
                    newInternal.addKey(keyAtIndex(i));
                    //removeKeyAtIndex(i);
                    //i--;
                }
            }

            for(int i = keyCount(); i > keyCount() / 2; i--){
                removeKeyAtIndex(i);
            }

            for(int i = 0; i < children.size(); i++){
                if(i < children.size() / 2){
                    
                }
                else{
                    newInternal.addChild(children.get(i));
                    children.get(i).setParent(newInternal);
                    //removeChildAtIndex(i);
                    //i--;
                }
            }

            for(int i = children.size() / 2; i < children.size(); i++){
                removeChildAtIndex(i);
                i--;
            }

            //parent.addKey(keyAtIndex(keyCount() / 2));
            parent.addChild(newInternal);

            parent.pushUp();
        }
    }

    private void updateLevels(CengTreeNodeInternal internal, int level){
        internal.level = level;
        for(int i = 0; i < internal.getAllChildren().size(); i++){
            if(internal.getAllChildren().get(i).type == CengNodeType.Internal){
                updateLevels((CengTreeNodeInternal) internal.getAllChildren().get(i), level + 1);
            }
            else{
                internal.getAllChildren().get(i).level = level + 1;
            }
        }
    }

    // Extra Functions
}
