package bookjobs.bookjobs;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Aliasgar on 7/10/16.
 */


public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.MyViewHolder> {



    private List<Match> matchList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, genre,distance;
        public ImageView cover;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            author = (TextView) view.findViewById(R.id.author);
            distance = (TextView) view.findViewById(R.id.distance);
            cover = (ImageView) view.findViewById(R.id.cover);
        }
    }


    public MatchRecyclerAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_listview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.title.setText(match.getBook().getmTitle());
        holder.genre.setText(match.getBook().getmGenre());
        holder.author.setText(match.getBook().getmAuthor());
        holder.distance.setText(""+match.getDistance()+" km");
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }
}