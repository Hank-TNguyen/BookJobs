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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aliasgar on 5/9/16.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = "MATCHESFRAGMENT";
    DatabaseReference mDatabase;
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
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Book book = new Book("1234", "Life of Pi", "Mr. Richard Williams", "Philosophy");
        User user1 = new User("John", "john@gmail.com","Loves travelling", "Singapore", new Address(10.12,17.32));
        User user2 = new User("Tay", "tay@gmail.com","Loves food", "New York", new Address(-10.12,17.32));
        Match first = new Match(book,user1, user2, new Date(2016,05,15),false,false);

        final List<Match> matchList = new ArrayList<>();
        matchList.add(first);

        MatchRecyclerAdapter matchRecyclerAdapter = new MatchRecyclerAdapter(matchList,getContext());

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
                launchDetail(matchList.get(adapterPosition));
            }

            @Override
            public void onItemDoubleClick(View doubleClickedView, int adapterPosition) {

            }
        }));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return rootView;
    }

    public void launchDetail(Match match)
    {
        Intent intent = new Intent(getContext(),MatchDetailActivity.class);
        intent.putExtra("match",match);
        startActivity(intent);

    }

    public void getUpdates()
    {

//        // [START single_value_read]
//        final String userId = "1234";
//        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
//
//                        // [START_EXCLUDE]
//                        if (user == null) {
//                            // User is null, error out
//                            Log.e(TAG, "User " + userId + " is unexpectedly null");
//                            Toast.makeText(getContext(),
//                                    "Error: could not fetch user.",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Write new post
//                            writeNewPost(userId, user.username, title, body);
//                        }
//
//
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                        // [START_EXCLUDE]
//
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END single_value_read]
//    }
//    // [START write_fan_out]
//    private void writeNewPost(String userId, String username, String title, String body) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        String key = mDatabase.child("posts").push().getKey();
//        U post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]

}
