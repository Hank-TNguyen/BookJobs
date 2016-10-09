package bookjobs.bookjobs;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static bookjobs.bookjobs.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    // CHEE TENG ATTRIBUTES
    TextView bookTitle;
    TextView author;
    ImageButton btnHeart;
    ImageButton btnCross;
    ImageView ivbook;

    int clickcounter = 0;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // CHEE TENG CODE
        ivbook = (ImageView)findViewById(R.id.ivBook);
        btnHeart = (ImageButton)findViewById(R.id.btnHeart);
        btnCross = (ImageButton)findViewById(R.id.btnCross);
        author = (TextView)findViewById(R.id.tvBookAuthor);
        bookTitle = (TextView)findViewById(R.id.tvBookTitle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_activity_add_book);
                fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                    Intent addBook = new Intent(MainActivity.this, AddBookActivity.class);
                                    addBook.putExtra("userAuth", getIntent().getStringExtra("userAuth"));
                                    startActivity(addBook);
                        }
                    });

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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.addListingMenu) {
            //Link to add book
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new BooksFragment());
            fragments.add(new MatchesFragment());
            fragments.add(new ProfileFragment());
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "BOOKS";
                case 1:
                    return "MATCHES";
                case 2:
                    return "PROFILE";
            }
            return null;
        }
    }

    //------------------------------------CHEE TENG METHODS-------------------------------//

    public void loadNewBook(){
        if (clickcounter==0) {
            String uri = "@drawable/book1.jpg";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Enchantment");
            author.setText("Guy Kawasaki");
        }

        else if (clickcounter==1) {
            String uri = "@drawable/book2.jpg";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("The Last Wild");
            author.setText("Piers Torday");
        }

        else if (clickcounter==2) {
            String uri = "@drawable/book3.jpg";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Boring Girls");
            author.setText("Sara Taylor");
        }

        else if (clickcounter==3) {
            String uri = "@drawable/book4.jpg";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            ivbook.setImageDrawable(res);
            bookTitle.setText("Blooming Business");
            author.setText("Alessia Patterson");
        }

        clickcounter++;
    }
}
