package bookjobs.bookjobs;



import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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

import java.util.List;
import java.util.Locale;

/**
 * Created by Aliasgar on 7/10/16.
 */


public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.MyViewHolder> {



    private List<Match> matchList;
    private Context context;
    AssetManager am;
    Typeface typeface_josephin;
    Typeface typeface_exo;
    Typeface typeface_engagement;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, author, genre,distance;
        public ImageView cover;
        public CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            author = (TextView) view.findViewById(R.id.author);
            distance = (TextView) view.findViewById(R.id.distance);
            cover = (ImageView) view.findViewById(R.id.cover);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }


    }


    public MatchRecyclerAdapter(List<Match> matchList, Context context) {
        this.matchList = matchList;
        this.context = context;
         am = context.getAssets();
         typeface_josephin = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Josephin.ttf"));

         typeface_exo = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Exo.ttf"));

         typeface_engagement = Typeface.createFromAsset(am,
                String.format(Locale.US, "fonts/%s", "Engagement.ttf"));

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

        holder.title.setTypeface(typeface_exo);
        holder.author.setTypeface(typeface_engagement);
        holder.genre.setTypeface(typeface_josephin);




    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }


}