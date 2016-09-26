package bookjobs.bookjobs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hung on 9/26/2016.
 */
public class AddBookActivity extends AppCompatActivity {
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_MULTIPLE_PICTURE = 201;
    private static final String LOG_TAG = "Add Image-AddBook";
    private ViewGroup mLinearLayout;
    ImageButton insertPicture;
    private Button mSubmitButton;
    private FirebaseAuth mDatabaseAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //set up wings
        final TextView isbnView = (TextView) findViewById(R.id.addbook_ISBN);
        final TextView titleView = (TextView) findViewById(R.id.addbook_title);
//        TextView genreView

        mLinearLayout = (ViewGroup) findViewById(R.id.addbook_pictures_linear_layout);
        insertPicture = (ImageButton) findViewById(R.id.addbook_insert_picture);
        mSubmitButton = (Button) findViewById(R.id.addbook_submit_button);
        insertPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intentGallery.setType(IMAGE_TYPE);

                intentGallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                Intent chooser = Intent.createChooser(intentGallery,
                        getString(R.string.select_picture));
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {new Intent(MediaStore.ACTION_IMAGE_CAPTURE)});
                startActivityForResult(chooser, SELECT_MULTIPLE_PICTURE);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book toUpload = new Book(isbnView.getText().toString(), titleView.getText().toString());

//                mDatabaseAuth = getIntent().getParcelableExtra("userAuth");
//                String userEmail = mDatabaseAuth.getCurrentUser().getEmail();
                String userEmail = getIntent().getStringExtra("userAuth");
                new BookController.UploadBookTask(toUpload, userEmail).execute();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "Finished Gallery Activity Result code: " + String.valueOf(resultCode==RESULT_OK));


        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_MULTIPLE_PICTURE){
                Log.d(LOG_TAG, "The requeste code is: " + String.valueOf(requestCode==SELECT_MULTIPLE_PICTURE));
                Log.d(LOG_TAG, "Action type is: " + String.valueOf(data.getAction()));
                if (Intent.ACTION_SEND_MULTIPLE.equals(data.getAction())
                        && data.hasExtra(Intent.EXTRA_STREAM)) {
                    // retrieve a collection of selected images
                    ArrayList<Parcelable> list = data.getParcelableArrayListExtra(Intent.EXTRA_STREAM);

                    // iterate over these images
                    try {
                        inflatePictures(list);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Failed to get images ", e);
                    }
                }
                else if (data.getAction()=="inline-data"){
                    try {
                        inflatePicture((Bitmap) data.getExtras().get("data"));
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Failed to get images ", e);
                    }
                }
            }
        }
    }

    //helper method use to inflate the images
    private void inflatePictures(ArrayList<Parcelable> pictures) throws IOException {

        LayoutInflater pictureInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (pictures != null) {
            Log.d(LOG_TAG, "Pictures list is " + pictures.toString());
            for (Parcelable parcel : pictures) {
                Uri uri = (Uri) parcel;
                Log.d(LOG_TAG, "Processing picture at uri: " + uri.toString());
                // handle the images one by one here

                ImageView toInsert = (ImageView) pictureInflater.inflate(R.layout.image_view_in_scroll_view, mLinearLayout, true);
                try {
                    toInsert.setImageBitmap(new UserPicture(uri, getContentResolver()).getBitmap());
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Failed to load image", e);
                }
                mLinearLayout.addView(toInsert);
            }
        }
    }

    private void inflatePicture(Bitmap picture) throws IOException{
        LayoutInflater pictureInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView toInsert = (ImageView) pictureInflater.inflate(R.layout.image_view_in_scroll_view, null, true);
        toInsert.setImageBitmap(picture);
        mLinearLayout.addView(toInsert);
    }

}



