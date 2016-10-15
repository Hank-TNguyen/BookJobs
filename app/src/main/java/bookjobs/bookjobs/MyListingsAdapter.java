package bookjobs.bookjobs;

import android.content.Context;
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

        ImageView heartPic = (ImageView) convertView.findViewById(R.id.imageView7);
        String hearturl = "drawable/heartbtn";
        int imageResource1 = context.getResources().getIdentifier(hearturl, null, context.getPackageName());
        Drawable image1 = context.getResources().getDrawable(imageResource1);
        heartPic.setImageDrawable(image1);

        TextView availability = (TextView) convertView.findViewById(R.id.tvNotAvailable);
        if (!book.isAvailability()){
            availability.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


}
