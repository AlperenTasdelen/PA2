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
                leaf.setParent(internal);
                leaf.level++;
                CengTreeNodeLeaf newLeaf = new CengTreeNodeLeaf(internal);
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
                //System.out.println("<record>" + leaf.bookAtIndex(i).getBookID() + "|" + leaf.bookAtIndex(i).getBookTitle() + "|" + leaf.bookAtIndex(i).getAuthor() + "|" + leaf.bookAtIndex(i).getGenre() + "</record>");
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
        // TODO: Print the whole tree to console
        // use depth first search
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

        print(root);

        /*
        ArrayList<CengTreeNode> queue = new ArrayList<CengTreeNode>();
        queue.add(root);
        while(queue.size() > 0){
            CengTreeNode node = queue.get(0);
            
            if(node.getType() == CengNodeType.Internal){
                CengTreeNodeInternal internal = (CengTreeNodeInternal) node;
                for(int i = 0; i < queue.get(0).level; i++){
                    System.out.print("\t");
                }
                System.out.println("<index>");
                for(int i = 0; i < internal.keyCount(); i++){
                    for(int j = 0; j < queue.get(0).level; j++){
                        System.out.print("\t");
                    }
                    System.out.println(internal.keyAtIndex(i));
                }
                for(int i = 0; i < queue.get(0).level; i++){
                    System.out.print("\t");
                }
                System.out.println("</index>");
                for(int i = 0; i < internal.getAllChildren().size(); i++){
                    queue.add(internal.getAllChildren().get(i));
                }
            }
            else{
                CengTreeNodeLeaf leaf = (CengTreeNodeLeaf) node;
                for(int i = 0; i < queue.get(0).level; i++){
                    System.out.print("\t");
                }
                System.out.println("<data>");
                for(int i = 0; i < leaf.bookCount(); i++){
                    //System.out.println("<record>" + leaf.bookAtIndex(i).getBookID() + "|" + leaf.bookAtIndex(i).getBookTitle() + "|" + leaf.bookAtIndex(i).getAuthor() + "|" + leaf.bookAtIndex(i).getGenre() + "</record>");
                    for(int j = 0; j < queue.get(0).level; j++){
                        System.out.print("\t");
                    }
                    System.out.println("<record>" + leaf.bookAtIndex(i).fullName() + "</record>");
                }
                for(int i = 0; i < queue.get(0).level; i++){
                    System.out.print("\t");
                }
                System.out.println("</data>");
            }
            queue.remove(0);
        }
        */
    }
    // Any extra functions...
}
