import java.util.ArrayList;

//implementing B+ tree


public class CengTree
{
    public CengTreeNode root;
    // Any extra attributes...

    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);
        
        root.level = 0;
    }

    public void addBook(CengBook book)
    {
        // TODO: Insert Book to Tree
        
        if(root.getType() == CengNodeType.Leaf){
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) root;
            leaf.addBookLeaf(book);
            if(leaf.bookCount() > 2 * CengTreeNode.order){
                CengTreeNodeInternal internal = new CengTreeNodeInternal(null);
                internal.level = leaf.level;
                leaf.setParent(internal);
                leaf.level++;
                CengTreeNodeLeaf newLeaf = new CengTreeNodeLeaf(internal);
                newLeaf.level = leaf.level;
                for(int i = leaf.bookCount() / 2; i < leaf.bookCount(); i++){
                    newLeaf.addBookLeaf(leaf.bookAtIndex(i));
                    leaf.removeBookAtIndex(i);
                    i--;
                }
                // Leaf to internal push up
                internal.addKey(newLeaf.bookKeyAtIndex(0));
                internal.addChild(leaf);
                internal.addChild(newLeaf);
                root = internal;
            }
        }
        else{
            CengTreeNodeInternal internal = (CengTreeNodeInternal) root;
            internal.addBookInternal(book);
        }
    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.
        if(root == null){
            return null;
        }
        
        CengTreeNode node = root;
        ArrayList<CengTreeNode> visited = new ArrayList<CengTreeNode>();
        CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
        
        if(node.getType() == CengNodeType.Internal){
            boolean leafFound = false;
            while(leafFound == false){
                visited.add(internal);
                int i = 0;
                for(; i < internal.keyCount(); i++){
                    if(bookID < internal.keyAtIndex(i)){
                        break;
                    }
                }
                if(internal.getChildAtIndex(0).getType() == CengNodeType.Leaf){
                    leafFound = true;
                }
                else{
                    internal = (CengTreeNodeInternal) internal.getChildAtIndex(i);
                }
            }
        }
        
        boolean found = false;
        CengTreeNodeLeaf leaf = null;
        int m = 0;
        for(m = 0; m < internal.keyCount(); m++){
            if(internal.keyAtIndex(m) > bookID){
                break;
            }
            else if(m == internal.keyCount() - 1){
                m++;
                break;
            }
        }
        leaf = (CengTreeNodeLeaf) internal.getChildAtIndex(m);
        for(int i = 0; i < leaf.bookCount(); i++){
            if(leaf.bookAtIndex(i).getBookID() == bookID){
                visited.add(leaf);
                found = true;
                break;
            }
        }
        if(found == true){
            for(int i = 0; i < visited.size() - 1; i++){
                CengTreeNodeInternal internal2 = (CengTreeNodeInternal) visited.get(i);
                for(int j = 0; j < visited.get(i).level; j++){
                    System.out.print("\t");
                }
                System.out.println("<index>");
                for(int j = 0; j < internal2.keyCount(); j++){
                    for(int k = 0; k < visited.get(i).level; k++){
                        System.out.print("\t");
                    }
                    System.out.println(internal2.keyAtIndex(j));
                }
                for(int j = 0; j < visited.get(i).level; j++){
                    System.out.print("\t");
                }
                System.out.println("</index>");
            }
            CengTreeNodeLeaf leaf2 = (CengTreeNodeLeaf) visited.get(visited.size() - 1);
            for(int i = 0; i < leaf2.bookCount(); i++){
                if(leaf2.bookAtIndex(i).getBookID() == bookID){
                    for(int j = 0; j < visited.get(visited.size() - 1).level; j++){
                        System.out.print("\t");
                    }
                    System.out.println("<record>" + leaf2.bookAtIndex(i).fullName() + "</record>");
                    break;
                }
            }
            for(int i = 0; i < visited.get(visited.size() - 1).level; i++){
                System.out.print("\t");
            }
        }
        else{
            System.out.println("Could not find " + bookID + ".");
            
        }
        return visited;
    }

    private void print(CengTreeNode node){
        if(node.getType() == CengNodeType.Internal){
            CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
            for(int i = 0; i < node.level; i++){
                System.out.print("\t");
            }
            System.out.println("<index>");
            for(int i = 0; i < internal.keyCount(); i++){
                for(int j = 0; j < node.level; j++){
                    System.out.print("\t");
                }
                System.out.println(internal.keyAtIndex(i));
            }
            for(int i = 0; i < node.level; i++){
                System.out.print("\t");
            }
            System.out.println("</index>");
            for(int i = 0; i < internal.getAllChildren().size(); i++){
                print(internal.getAllChildren().get(i));
            }
        }
        else{
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
            for(int i = 0; i < node.level; i++){
                System.out.print("\t");
            }
            System.out.println("<data>");
            for(int i = 0; i < leaf.bookCount(); i++){
                for(int j = 0; j < node.level; j++){
                    System.out.print("\t");
                }
                System.out.println("<record>" + leaf.bookAtIndex(i).fullName() + "</record>");
            }
            for(int i = 0; i < node.level; i++){
                System.out.print("\t");
            }
            System.out.println("</data>");
        }
    }


    public void printTree()
    {
        print(root);
    }
    // Any extra functions...
}
