import java.lang.reflect.Array;
import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations
        type = CengNodeType.Leaf;
        setParent(parent);
        books = new ArrayList<CengBook>();
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    public void addBookLeaf(CengBook book)
    {
        int bookID = book.getBookID();
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getBookID() > bookID){
                books.add(i, book);
                return;
            }
        }
        books.add(book);
    }



    public void removeBookAtIndex(Integer index)
    {
        books.remove(index.intValue());
    }

    public CengBook bookAtIndex(Integer index)
    {
        return books.get(index);
    }

    // Extra Functions
}
