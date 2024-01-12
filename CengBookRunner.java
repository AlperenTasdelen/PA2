import java.awt.EventQueue;
import java.util.ArrayList;

public class CengBookRunner
{
    private static Integer order;

    private static String fileName;

    private static Boolean guiEnabled;

    private static CengTree bookTree;
    private static CengGUI window;

    // If you want to see the nodes always next to each other, change this to false.
    private static Boolean wrapNodes = false;

    // If you want to have a static window, change this to false
    private static Boolean packFrame = false;

    public static void main(String[] args) throws Exception
    {
        if(args.length != 3)
        {
            throw new Exception("Usage : java CengBookRunner <order> <inputFileName> <guiEnabled> (True or False) ");
        }

        order = Integer.parseInt(args[0]);
        //System.out.println("Order: " + order);
        fileName = args[1];
        guiEnabled = Boolean.parseBoolean(args[2]);

        bookTree = new CengTree(order);
        
        if(bookTree == null)
        {
            System.out.println("Err: Tree is not initialized yet.");

            return;
        }

        System.out.println("root:" + bookTree.root);

        //System.out.println("here");

        Integer orderN = 2 * order + 1; // N-based order, for GUI purposes only.

        CengGUI.orderN = orderN;

        CengTreeParser.parseBooksFromFile(fileName);

        if(guiEnabled)
        {
            EventQueue.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                window = new CengGUI();
                                window.show();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
        }

        CengTreeParser.startParsingCommandLine();
    }

    public static void addBook(CengBook book)
    {
        //System.out.println("Adding book");
        bookTree.addBook(book);
        //System.out.println("Book added");
        if(guiEnabled)
        {
            if(window == null)
            {
                System.out.println("Err: Window is not initialized yet.");

                return;
            }

            window.modelNeedsUpdate(bookTree.root);
        }
    }

    public static void searchBook(Integer key)
    {
        ArrayList<CengTreeNode> visitedNodes = bookTree.searchBook(key);

        if(guiEnabled)
        {
            window.modelNeedsUpdate(bookTree.root);

            if(visitedNodes != null)
            {
                window.updateWithSearchResult(visitedNodes, key);
            }
        }
    }

    public static void printTree()
    {
        System.out.println("Printing Tree:");
        if(bookTree == null)
        {
            System.out.println("Tree is empty.");

            return;
        }
        bookTree.printTree();
        System.out.println("Printed Tree");
        if(guiEnabled)
        {
            window.modelNeedsUpdate(bookTree.root);
        }
    }

    public static String getFilenameToParse()
    {
        return CengBookRunner.fileName;
    }

    public static Boolean shouldWrap()
    {
        return CengBookRunner.wrapNodes;
    }

    public static Boolean shouldPack()
    {
        return CengBookRunner.packFrame;
    }
}
