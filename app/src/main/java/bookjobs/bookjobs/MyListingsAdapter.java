package bookjobs.bookjobs;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

/**
 * Created by Chee Teng on 10/10/2016.
 */

public class MyListingsAdapter extends ArrayAdapter<Book> {
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public MyListingsAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent ) {
        convertView = (RelativeLayout) inflater.inflate( resource, null );

//        AssetManager am = context.getAssets();
//        Typeface typeface_josephin = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "Josephin.ttf"));
//
//        Typeface typeface_exo = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "Exo.ttf"));
//
//        Typeface typeface_engagement = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "Engagement.ttf"));

        Book book = getItem( position );

        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        bookTitle.setText(book.getmTitle());

        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        bookAuthor.setText(book.getmAuthor());


        ImageView bookImage = (ImageView) convertView.findViewById(R.id.bookPic);
        String uri = "drawable/" + book.getPicURL();
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        //Drawable image = context.getResources().getDrawable(imageResource);
        //bookImage.setImageDrawable(image);

        TextView noOfLikes = (TextView) convertView.findViewById(R.id.noOfLikes);
        noOfLikes.setText(Integer.toString(book.getNoOfLikes()));

        TextView name = (TextView) convertView.findViewById(R.id.tvName);
        TextView address = (TextView) convertView.findViewById(R.id.tvaddress);

//        name.setTypeface(typeface_engagement);
//        address.setTypeface(typeface_josephin);
//        bookTitle.setTypeface(typeface_exo);
//        bookAuthor.setTypeface(typeface_josephin);



        return convertView;
    }


}
