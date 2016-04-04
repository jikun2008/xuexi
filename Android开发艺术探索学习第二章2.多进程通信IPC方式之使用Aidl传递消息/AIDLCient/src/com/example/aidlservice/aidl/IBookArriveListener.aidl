package  com.example.aidlservice.aidl;
import com.example.aidlservice.aidl.Book;
interface IBookArriveListener{
       void onNewBookArrived(in Book book);
}