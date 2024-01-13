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
        CengTreeNodeInternal internal = new CengTreeNodeInternal(getParent());
        internal.level = level;
        //TODO: add keys and children to new internal node
        for(int i = 0; i < keyCount(); i++){
            if(i < keyCount() / 2){
                
            }
            else if (i > keyCount() / 2){
                internal.addKeyAndChildOrdered(keyAtIndex(i), getAllChildren().get(i + 1));
                removeKeyAtIndex(i);
                removeChildAtIndex(i + 1);
                i--;
            }
            else{
                // parent might be null
                CengTreeNodeInternal parentInternal;
                boolean rootParsedUp = false;
                if (getParent() == null){
                    //root parsed up and level increased
                    rootParsedUp = true;
                    parentInternal = new CengTreeNodeInternal(null);
                }
                else{
                    parentInternal = (CengTreeNodeInternal) getParent();
                }
                parentInternal.addKeyAndChildOrdered(keyAtIndex(i), internal);
                internal.setParent(parentInternal);
                removeKeyAtIndex(i);
                removeChildAtIndex(i + 1);
                i--;
                if(parentInternal.keyCount() > 2 * CengTreeNode.order){
                    // recursive push up
                    parentInternal.pushUp();
                }
                if(rootParsedUp){
                    updateLevels(parentInternal, 0);
                }
            }
        }
        internal.addChild(getAllChildren().get(keyCount() + 1));
        removeChildAtIndex(keyCount() + 1);
        
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
