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
        //root.setParent(null);
        root.level = 0;
        //root.type = CengNodeType.Leaf;
        //System.out.println("Tree initialized");
    }

    public void addBook(CengBook book)
    {
        // TODO: Insert Book to Tree
        /*
        if(root == null){
            root = new CengTreeNodeLeaf(null);
            root.level = 0;
        }
        */
        //System.out.println("adding book");
        if(root.getType() == CengNodeType.Leaf){
            CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) root;
            leaf.addBookLeaf(book);
            if(leaf.bookCount() <= 2 * CengTreeNode.order){
                
            }
            else{
                CengTreeNodeInternal internal = new CengTreeNodeInternal(null);
                internal.level = leaf.level;
                internal.addKey(leaf.bookKeyAtIndex(leaf.bookCount() - 1));
                internal.addChild(leaf);
                leaf.setParent(internal);
                leaf.level++;
                CengTreeNodeLeaf newLeaf = new CengTreeNodeLeaf(internal);
                newLeaf.level = leaf.level;
                internal.addChild(newLeaf);
                for(int i = 0; i < leaf.bookCount(); i++){
                    if(i < leaf.bookCount() / 2){
                        
                    }
                    else{
                        newLeaf.addBookLeaf(leaf.bookAtIndex(i));
                        leaf.removeBookAtIndex(i);
                        i--;
                    }
                }
                // Leaf to internal push up
                internal.addKey(newLeaf.bookKeyAtIndex(0));
                internal.addChild(leaf);
                //leaf.setParent(internal);
                internal.addChild(newLeaf);
                //newLeaf.setParent(internal);
                root = internal;

                System.out.println("root pushed up");
            }
        }
        else{
            CengTreeNodeInternal internal = (CengTreeNodeInternal) root;
            internal.addBookInternal(book);
            //System.out.println("root is internal");
            if(root.getParent() != null){
                //root pushed up
                //System.out.println("root pushed up");
                root = root.getParent();
            }
        }
    }

    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // TODO: Search within whole Tree, return visited nodes.
        // Return null if not found.

        return null;
    }

    public void printTree()
    {
        // TODO: Print the whole tree to console
        // use breadth first search
        //System.out.println("Printing Tree...");
        /*
         *  format: for each node if it is a interval write this and do tab for each level -> 1 tab for each level:
         *  <index>
         *  <key1>
         *  <key2>
         *  ...
         *  </index>
         *   
         *  if it is a leaf write this and do tab for each level -> 1 tab for each level:
         *  <data>
         *  <record><bookID1>|<bookTitle>|<author>|<genre></record>
         *  <record><bookID2>|<bookTitle>|<author>|<genre></record>
         *  ...
         *  </data>
         */

        //TODO: implement tab structure
        ArrayList<CengTreeNode> queue = new ArrayList<CengTreeNode>();
        queue.add(root);
        while(queue.size() > 0){
            for(int i = 0; i < queue.get(0).level; i++){
                System.out.print("\t");
            }
            CengTreeNode node = queue.get(0);
            queue.remove(0);
            if(node.getType() == CengNodeType.Internal){
                CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
                System.out.println("<index>");
                for(int i = 0; i < internal.keyCount(); i++){
                    System.out.println(internal.keyAtIndex(i));
                }
                System.out.println("</index>");
                for(int i = 0; i < internal.getAllChildren().size(); i++){
                    queue.add(internal.getAllChildren().get(i));
                }
            }
            else{
                CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
                System.out.println("<data>");
                for(int i = 0; i < leaf.bookCount(); i++){
                    //System.out.println("<record>" + leaf.bookAtIndex(i).getBookID() + "|" + leaf.bookAtIndex(i).getBookTitle() + "|" + leaf.bookAtIndex(i).getAuthor() + "|" + leaf.bookAtIndex(i).getGenre() + "</record>");
                    System.out.println("<record>" + leaf.bookAtIndex(i).fullName() + "</record>");
                }
                System.out.println("</data>");
            }
        }
    }

    // Any extra functions...
}
