package bookjobs.bookjobs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Aliasgar on 5/9/16.
 */
public class MatchesFragment extends Fragment {

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
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        Book book = new Book("1234", "Life of Pi", "Mr. Richard Williams", "Philosophy");
        User user1 = new User("John", "john@gmail.com","Loves travelling", "Singapore", new Address(10.12,17.32));
        User user2 = new User("Tay", "tay@gmail.com","Loves food", "New York", new Address(-10.12,17.32));
        Match first = new Match(book,user1, user2, new Date(2016,05,15),false,false);

        List<Match> matchList = new ArrayList<>();
        matchList.add(first);

        MatchRecyclerAdapter matchRecyclerAdapter = new MatchRecyclerAdapter(matchList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(matchRecyclerAdapter);

        return rootView;
    }
}
