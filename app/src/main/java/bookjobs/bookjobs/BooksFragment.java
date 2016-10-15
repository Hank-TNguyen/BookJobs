package bookjobs.bookjobs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Aliasgar on 5/9/16.
 */



public class BooksFragment extends Fragment {

    private DatabaseReference mPostReference;
    private DatabaseReference mCurrentUserReference;
    private StorageReference mStorageReference;

    // CHEE TENG ATTRIBUTES
    TextView bookTitle;
    TextView author, bookISBN, bookGenre;
    ImageButton btnHeart;
    ImageButton btnCross;
    ImageView ivbook;
    ArrayList<Book> bookArrayList;
    int currentBookIndex = -1;

    int clickcounter = 0;

    public BooksFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_books, container,
                false);

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("books");

        mCurrentUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users");

        // CHEE TENG CODE
        ivbook = (ImageView)rootView.findViewById(R.id.ivBook);
        btnHeart = (ImageButton)rootView.findViewById(R.id.btnHeart);
        btnCross = (ImageButton)rootView.findViewById(R.id.btnCross);
        author = (TextView)rootView.findViewById(R.id.tvBookAuthor);
        bookTitle = (TextView)rootView.findViewById(R.id.tvBookTitle);
        bookISBN = (TextView)rootView.findViewById(R.id.tvBookISBN);
        bookGenre = (TextView)rootView.findViewById(R.id.tvBookGenre);

        bookArrayList = new ArrayList<>();

        btnHeart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDatabase();
                loadNewBook();
            }
        });

        btnCross.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadNewBook();
            }
        });


        final List<Book> bookList = new ArrayList<>();


        if(bookList.size()<=0)
            getPost();

        return rootView;
    }

    private void updateDatabase() {

        if(currentBookIndex!=-1)
        {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            Query query = mCurrentUserReference.orderByChild("email").equalTo(user.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue().toString();
                    String userRef = value.substring(1, value.indexOf('='));
                    if(bookArrayList.size()>0)
                    {
                        Book book = bookArrayList.get(currentBookIndex);
                        dataSnapshot.child(userRef).child("wants").child(book.getmISBN()).getRef().setValue(1);

                    }
                                   }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("BooksFragment", "Failed to reach the server");
                }
            });

        }
        else
        {
            Toast.makeText(getContext(),"Illegal click - Please report", Toast.LENGTH_SHORT).show();
        }



    }


    public void loadNewBook(){
        Book newBook;
        Random random = new Random();

        if(currentBookIndex != -1 )
        {
            if(bookArrayList.size()>0)
                bookArrayList.remove(currentBookIndex);
            else
                currentBookIndex = -1;
        }

        if(bookArrayList.size()>0)
        {
            currentBookIndex = random.nextInt(bookArrayList.size());
            newBook = bookArrayList.get(currentBookIndex);
            author.setText(newBook.getmAuthor());
            bookTitle.setText(newBook.getmTitle());
            bookGenre.setText(newBook.getmGenre());
            bookISBN.setText(newBook.getmISBN());
        }




    }

    public boolean getPost()
    {
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("books");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    Book book = child.getValue(Book.class);
                    bookArrayList.add(book);
                    Log.d("TAG",""+child.getValue());
//
//                    mStorageReference.child("photos/1920.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle any errors
//                        }
//                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return true;
    }


}
