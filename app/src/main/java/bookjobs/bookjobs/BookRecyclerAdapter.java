package bookjobs.bookjobs;


/**
 * Created by mac on 15/10/16.
 */


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Aliasgar on 7/10/16.
 */


public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.MyViewHolder> {

    private List<Book> bookList;
    private Context context;


    public BookRecyclerAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getmTitle());
        holder.genre.setText(book.getmGenre());
        holder.author.setText(book.getmAuthor());
        holder.ISBN.setText(book.getmISBN());


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, genre, ISBN;
        public ImageView cover;
        public CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvBookTitle);
            genre = (TextView) view.findViewById(R.id.tvBookGenre);
            author = (TextView) view.findViewById(R.id.tvBookAuthor);
            ISBN = (TextView) view.findViewById(R.id.tvBookISBN);
            cover = (ImageView) view.findViewById(R.id.ivBook);
            //cardView = (CardView) view.findViewById(R.id.card_view);


        }


    }


}