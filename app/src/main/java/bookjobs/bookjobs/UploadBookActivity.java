package bookjobs.bookjobs;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import bookjobs.bookjobs.BookController.*;

public class UploadBookActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private EditText mTitleText;
    private EditText mISBNText;
    private EditText mAuthorText;
    private EditText mGenreText;
    private ImageButton btnGetLocation;
    private Button btnUploadBook;
    BookController bkc = new BookController();

    private UploadBookTask mAuthTask = null;
    Book newBook;
    User currentUser;
   // GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_book);


        mTitleText = (EditText) findViewById(R.id.book_title);
        mISBNText = (EditText) findViewById(R.id.book_isbn);
        mAuthorText = (EditText) findViewById(R.id.book_author);
        mGenreText = (EditText) findViewById(R.id.book_genre);
        btnGetLocation = (ImageButton) findViewById(R.id.getlocationBtn);
        btnUploadBook = (Button) findViewById(R.id.upload_book_btn);

        btnUploadBook.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                attemptAddNewBookListing();
            }
        });

        //===Still have to work on the user's location button===

            /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }

        btnGetLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gps = new GPSTracker(UploadBookActivity.this);

                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext()," Your location is -\nLat: "+ latitude +"\nLong: "+ longitude, Toast.LENGTH_LONG).show();
                } else{

                    gps.showSettingAlert();
                }
            }
        });*/

    }

    private void attemptAddNewBookListing(){
        String bTitle = mTitleText.getText().toString();
        String bISBN = mISBNText.getText().toString();
        String bAuthor = mAuthorText.getText().toString();
        String bGenre = mGenreText.getText().toString();

        newBook = new Book(bISBN,bTitle);
        newBook.setmAuthor(bAuthor);
        newBook.setmGenre(bGenre);
        newBook.setmISBN(bISBN);
        newBook.setmTitle(bTitle);

        //Still have to figure out how to get current user.


        mAuthTask = new BookController().new UploadBookTask(newBook,currentUser);
        mAuthTask.execute((Void) null);
    }
}
