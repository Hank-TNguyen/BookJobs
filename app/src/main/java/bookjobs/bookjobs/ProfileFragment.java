package bookjobs.bookjobs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aliasgar on 5/9/16.
 */
public class ProfileFragment extends Fragment {

    private ListView lvBook;
    private Context ctx;

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

        ctx=getActivity().getApplicationContext();
        List<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book("asd", "PS I Love You", "Cecelia Ahern", "Love", "profilebook", 42));
        bookList.add(new Book("asd", "The Da Vinci Code", "Dan Brown", "Mystery", "davinci", 65));
        bookList.add(new Book("asd", "For One More Day", "Mitch Albom", "Novel", "book7", 28));
        bookList.add(new Book("asd", "The Girl's Playground", "Alexandria Jackson", "Novel", "book9", 5));

        lvBook = (ListView)rootView.findViewById(R.id.lvListings);
        lvBook.setAdapter(new MyListingsAdapter(ctx, R.layout.my_listing_row, bookList));

        lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book o = (Book) parent.getItemAtPosition(position);
                Toast.makeText(ctx, o.getmTitle().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
