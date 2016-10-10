package bookjobs.bookjobs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aliasgar on 5/9/16.
 */



public class BooksFragment extends Fragment {

    // CHEE TENG ATTRIBUTES
    TextView bookTitle;
    TextView author;
    ImageButton btnHeart;
    ImageButton btnCross;
    ImageView ivbook;

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

        // CHEE TENG CODE
        ivbook = (ImageView)rootView.findViewById(R.id.ivBook);
        btnHeart = (ImageButton)rootView.findViewById(R.id.btnHeart);
        btnCross = (ImageButton)rootView.findViewById(R.id.btnCross);
        author = (TextView)rootView.findViewById(R.id.tvBookAuthor);
        bookTitle = (TextView)rootView.findViewById(R.id.tvBookTitle);

        btnHeart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadNewBook();
            }
        });

        btnCross.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadNewBook();
            }
        });

        return rootView;
    }

    //------------------------------------CHEE TENG METHODS-------------------------------//

    public void loadNewBook(){
        if (clickcounter==0) {

            String uri = "@drawable/book1";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Enchantment");
            author.setText("Guy Kawasaki");
        }

        else if (clickcounter==1) {
            String uri = "@drawable/book2";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("The Last Wild");
            author.setText("Piers Torday");
        }

        else if (clickcounter==2) {
            String uri = "@drawable/book3";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Boring Girls");
            author.setText("Sara Taylor");
        }

        else if (clickcounter==3) {
            String uri = "@drawable/book4";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Blooming Business");
            author.setText("Alessia Patterson");
        }

        else if (clickcounter==4) {
            String uri = "@drawable/book5";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Life Plan");
            author.setText("Michael Hyatt");
        }

        else if (clickcounter==5) {
            String uri = "@drawable/book6";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Eleven Minutes");
            author.setText("Paul Coelho");
        }

        else if (clickcounter==6) {
            String uri = "@drawable/book7";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("For One More Day");
            author.setText("Mitch Albom");
        }

        else if (clickcounter==7) {
            String uri = "@drawable/book8";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Adventures of Huckleberry Finn");
            author.setText("Mark Twain");
        }

        else if (clickcounter==8) {
            String uri = "@drawable/book9";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("The Girl's Playground");
            author.setText("Alexandria Jackson");
        }

        else if (clickcounter==9) {
            String uri = "@drawable/book10";
            int imageResource = getResources().getIdentifier(uri, null, MainActivity.PACKAGE_NAME);
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("The Railway Children");
            author.setText("E.Nesbit");
            clickcounter = 0;
        }

        clickcounter++;
    }
}
