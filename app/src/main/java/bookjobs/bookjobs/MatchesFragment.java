package bookjobs.bookjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Aliasgar on 5/9/16.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = "MATCHESFRAGMENT";
    private static DatabaseReference mDatabase;
    private static DatabaseReference mBooksDatabase;
    ArrayList<String> bookIDs;
    RecyclerView recyclerView;
    ArrayList<Match> foundMatch;
    ArrayList<Book> booksMatch;
    private DatabaseReference mCurrentUser;
    private DatabaseReference mCurrentUserBooks;

    public MatchesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_matches, container,
                false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Book book = new Book("1234", "Life of Pi", "Mr. Richard Williams", "Philosophy");
        User user1 = new User("John", "john@gmail.com", "Loves travelling", "Singapore", new Address(10.12, 17.32));
        User user2 = new User("Tay", "tay@gmail.com", "Loves food", "New York", new Address(-10.12, 17.32));
        Match first = new Match(book, user1, user2, new Date(2016, 05, 15), false, false);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBooksDatabase = mDatabase.child("books");
        mCurrentUser = mDatabase.child("users");
        booksMatch = new ArrayList<>();
        foundMatch = new ArrayList<>();
        bookIDs = new ArrayList<>();

        getBooks();


        return rootView;
    }

    public void launchDetail(Match match) {
        Intent intent = new Intent(getContext(), MatchDetailActivity.class);
        intent.putExtra("match", match);
        startActivity(intent);

    }

    private void getBooks() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = mCurrentUser.orderByChild("email").equalTo(user.getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {
                };
                HashMap<String, Object> response = dataSnapshot.getValue(t);

                HashMap.Entry<String, Object> entry = response.entrySet().iterator().next();

                HashMap<String, Object> user = (HashMap<String, Object>) entry.getValue();

                Iterator it = user.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap.Entry book = (HashMap.Entry) it.next();
                    if(( book.getKey()).equals("wants"))
                    {
                        HashMap<String, Object> bookSpecific = (HashMap<String, Object>) book.getValue();
                        Iterator itBook = bookSpecific.entrySet().iterator();
                        while (itBook.hasNext())
                        {
                            HashMap.Entry bookFinal = (HashMap.Entry) itBook.next();
                            bookIDs.add((String) bookFinal.getKey());
                        }

                    }
                }


                mBooksDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {
                        };
                        HashMap<String, Object> response = snapshot.getValue(t);
                        final HashMap<String, String> pair = new HashMap<String, String>();
                        String pairValue;
                        final ArrayList<String> refkeys  = new ArrayList<String>();
                        final Iterator it = response.entrySet().iterator();
                        while (it.hasNext()) {
                            final HashMap.Entry book = (HashMap.Entry) it.next();
                            pairValue = book.getKey().toString();
                            HashMap<String, Object> bookSpecific = (HashMap<String, Object>) book.getValue();
                            Iterator itBook = bookSpecific.entrySet().iterator();
                            while (itBook.hasNext()) {
                                HashMap.Entry bookFinal = (HashMap.Entry) itBook.next();
                                if (bookFinal.getKey().equals("mISBN")) {
                                    pair.put(bookFinal.getValue().toString(),pairValue);
                                    if (bookIDs.contains(bookFinal.getValue())) {
                                        try {
                                            //Found
                                            HashMap<String, Object> bookHashMap = (HashMap<String, Object>) book.getValue();

//                                       bookHashMap.get("noOfLikes");
//                                       bookHashMap.get("availability");

                                            final Book bookMatched = new Book((String) bookHashMap.get("mISBN"), (String) bookHashMap.get("mTitle"), (String) bookHashMap.get("mAuthor"), (String) bookHashMap.get("mGenre"));
                                            booksMatch.add(bookMatched);
                                            //

                                            final String refKey = pair.get(bookMatched.getmISBN());

                                            refkeys.add(refKey);



                                            //

                                            // foundMatch.add();

                                        } catch (Exception e) {
                                            Log.e("ProfileFragment", "Illegal format in Database. Delete db and add new");
                                        }


                                    }
                                }
                            }

                        }
                        //Query queryUser = mCurrentUser.child("owns").orderByChild(refKey).equalTo("1");

                        mCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot child : dataSnapshot.getChildren()) {

                                    GenericTypeIndicator<HashMap<String, Object>> t = new GenericTypeIndicator<HashMap<String, Object>>() {
                                    };
                                    HashMap<String, Object> response = child.getValue(t);
                                    Iterator iterator1 = response.entrySet().iterator();
                                    while (iterator1.hasNext())
                                    {
                                        HashMap.Entry bookFinal = (HashMap.Entry) iterator1.next();
                                        if(bookFinal.getKey().equals("owns"))
                                        {

                                            HashMap<String, Object> bookSpecific = (HashMap<String, Object>) bookFinal.getValue();
                                            Iterator itBook = bookSpecific.entrySet().iterator();
                                            while (itBook.hasNext())
                                            {
                                                HashMap.Entry bookSpecifics = (HashMap.Entry) itBook.next();
                                                Book local =null;

                                                if(refkeys.contains(bookSpecifics.getKey().toString()))
                                                {
                                                    for(Book book: booksMatch)
                                                    {
                                                        if(bookSpecifics.getKey().toString().equals(pair.get(book.getmISBN())))
                                                        {
                                                            local = book;
                                                        }
                                                    }
                                                    //This is the user
                                                    response.get("address");
                                                    response.get("email");
                                                    response.get("name");
                                                    User user2 = new User( response.get("name").toString(),response.get("email").toString(), response.get("address").toString());
                                                    foundMatch.add(new Match(local, user2));
                                                }

                                            }
                                        }
                                    }



                                }

                                if (foundMatch.size() > 0) {
                                    showMatches();
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

            private void showMatches() {


                MatchRecyclerAdapter matchRecyclerAdapter = new MatchRecyclerAdapter(foundMatch, getContext());

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(matchRecyclerAdapter);
                recyclerView.addOnItemTouchListener(new RecyclerViewItemTouchListener(getContext(), new RecyclerViewItemTouchListener.OnItemClickEventListener() {
                    @Override
                    public void onItemLongClick(View longClickedView, int adapterPosition) {

                    }

                    @Override
                    public void onItemClick(View clickedView, int adapterPosition) {
                        launchDetail(foundMatch.get(adapterPosition));
                    }

                    @Override
                    public void onItemDoubleClick(View doubleClickedView, int adapterPosition) {

                    }
                }));

            }
        });
    }
}

