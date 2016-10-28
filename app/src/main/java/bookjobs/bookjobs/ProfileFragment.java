package bookjobs.bookjobs;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Aliasgar on 5/9/16.
 */
public class ProfileFragment extends Fragment {

    private ListView lvBook;
    private Context ctx;
    private DatabaseReference mCurrentUser;
    private DatabaseReference mCurrentUserBooks;
    private static DatabaseReference mDatabase;
    private static DatabaseReference mBooksDatabase;
    ArrayList<String> bookIDs;
    ArrayList<Book> foundBooks;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container,
                false);



        bookIDs = new ArrayList<String>();
        foundBooks = new ArrayList<Book>();
        ctx=getActivity().getApplicationContext();
        List<Book> bookList = new ArrayList<Book>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBooksDatabase = mDatabase.child("books");
        mCurrentUser = mDatabase.child("users");
        lvBook = (ListView)rootView.findViewById(R.id.lvListings);
        getBooks();

        ImageButton addNewListing = (ImageButton)rootView.findViewById(R.id.newListingbtn);
        addNewListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddBookActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void showBooks()
    {
            lvBook.setAdapter(new MyListingsAdapter(getContext(), R.layout.my_listing_row, foundBooks));

            lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                }
            });
    }

    private void getBooks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = mCurrentUser.orderByChild("email").equalTo(user.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                HashMap<String,Object> response = dataSnapshot.getValue(t);

                HashMap.Entry<String,Object> entry=response.entrySet().iterator().next();

                HashMap<String, Object> user = (HashMap<String, Object>) entry.getValue();

                Iterator it = user.entrySet().iterator();
                it.next();
                it.next();
                it.next();
                HashMap.Entry owns = (HashMap.Entry)it.next();
                HashMap<String, Object> ownsValue = (HashMap<String, Object>) owns.getValue();

                it = ownsValue.entrySet().iterator();
                while(it.hasNext())
                {
                    HashMap.Entry book = (HashMap.Entry)it.next();
                    bookIDs.add((String) book.getKey());
                }




                    mBooksDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {};
                            HashMap<String,Object> response = snapshot.getValue(t);

                            Iterator it = response.entrySet().iterator();
                            while(it.hasNext())
                            {
                                HashMap.Entry book = (HashMap.Entry)it.next();
                                if(bookIDs.contains(book.getKey()))
                                {
                                   try{
                                       //Found
                                       HashMap<String, Object> bookHashMap = (HashMap<String, Object>) book.getValue();

                                       foundBooks.add(new Book((String)bookHashMap.get("mISBN"),(String)bookHashMap.get("mTitle"),(String)bookHashMap.get("mAuthor"),(String)bookHashMap.get("mGenre")));

                                   }catch (Exception e)
                                   {
                                       Log.e("ProfileFragment", "Illegal format in Database. Delete db and add new");
                                   }


                                }

                            }
                            if(foundBooks.size()>0)
                            {
                                showBooks();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("PROFILEFragment", "Failed to reach the server");
            }
        });
    }
}
