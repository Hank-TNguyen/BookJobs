package bookjobs.bookjobs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import org.w3c.dom.Comment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import bookjobs.bookjobs.ImageUploader.FirebaseImageLoader;
import bookjobs.bookjobs.ImageUploader.ImageDownloader;
import bookjobs.bookjobs.ImageUploader.MyImageDownloadListener;

/**
 * Created by Aliasgar on 5/9/16.
 */



public class BooksFragment extends Fragment {

    private DatabaseReference mPostReference;
    private DatabaseReference mCurrentUserReference;
    private StorageReference mStorageReference;

    private final String TAG = "GetPost:";
    ArrayList<ArrayList<String>> imagesList = new ArrayList<>();
    LinearLayout imagesView;
    Bitmap[] bitmaps;

    // CHEE TENG ATTRIBUTES
    TextView bookTitle;
    TextView author, bookISBN, bookGenre;
    ImageButton btnHeart;
    ImageButton btnCross;
    ImageView ivbook;
    ArrayList<Book> bookArrayList;

    int currentBookIndex = -1;

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

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("books");

        mCurrentUserReference = FirebaseDatabase.getInstance().getReference()
                .child("users");

        imagesView = (LinearLayout) rootView.findViewById(R.id.fragment_books_images_linear_layout);

        // CHEE TENG CODE
        ivbook = (ImageView)rootView.findViewById(R.id.ivBook);
        btnHeart = (ImageButton)rootView.findViewById(R.id.btnHeart);
        btnCross = (ImageButton)rootView.findViewById(R.id.btnCross);
        author = (TextView)rootView.findViewById(R.id.tvBookAuthor);
        bookTitle = (TextView)rootView.findViewById(R.id.tvBookTitle);
        bookISBN = (TextView)rootView.findViewById(R.id.tvBookISBN);
        bookGenre = (TextView)rootView.findViewById(R.id.tvBookGenre);

        bookArrayList = new ArrayList<>();

        btnHeart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDatabase();
                loadNewBook();
            }
        });

        btnCross.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadNewBook();
            }
        });


        final List<Book> bookList = new ArrayList<>();


        if(bookList.size()<=0)
            getPost();

        return rootView;
    }

    private void updateDatabase() {

        if(currentBookIndex!=-1)
        {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            Query query = mCurrentUserReference.orderByChild("email").equalTo(user.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = dataSnapshot.getValue().toString();
                    String userRef = value.substring(1, value.indexOf('='));
                    if(bookArrayList.size()>0)
                    {
                        Book book = bookArrayList.get(currentBookIndex);
                        dataSnapshot.child(userRef).child("wants").child(book.getmISBN()).getRef().setValue(1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("BooksFragment", "Failed to reach the server");
                }
            });

        }
        else
        {
            Toast.makeText(getContext(),"Illegal click - Please report", Toast.LENGTH_SHORT).show();
        }



    }


    public void loadNewBook() {
        Book newBook;
        Random random = new Random();

        if(currentBookIndex != -1 )
        {
            if(bookArrayList.size()>0)
                bookArrayList.remove(currentBookIndex);
            else
                currentBookIndex = -1;
        }

        if(bookArrayList.size()>0)
        {
            imagesView.removeAllViews();
            currentBookIndex = random.nextInt(bookArrayList.size());
            newBook = bookArrayList.get(currentBookIndex);
            author.setText(newBook.getmAuthor());
            bookTitle.setText(newBook.getmTitle());
            bookGenre.setText(newBook.getmGenre());
            bookISBN.setText(newBook.getmISBN());

            // get the image storage reference
            mStorageReference = FirebaseStorage.getInstance().getReference();

            ArrayList<String> mImages;
            mImages = imagesList.get(currentBookIndex);


            final LayoutInflater mLI = getActivity().getLayoutInflater();

            bitmaps = new Bitmap[mImages.size()];
            for (int i = 0; i < mImages.size(); i++){
                String image = mImages.get(i);
                Log.d(TAG, "images are: " + mImages.toString());
                final int finalI = i;

                mStorageReference.child(image)
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "uri is: " + uri.toString());
                        URL url = null;
                        try {
                            url = new URL(uri.toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        ImageDownloader IDTask = new ImageDownloader(url);
                        IDTask.setCompleteListener(new MyImageDownloadListener() {
                            @Override
                            public void onSuccess(Bitmap bm) {
                                bitmaps[finalI] = bm;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ImageView iv = (ImageView) mLI.inflate(R.layout.image_view_in_scroll_view, null, true);
                                        iv.setImageBitmap(bitmaps[finalI]);
                                        imagesView.addView(iv);
                                    }
                                });
                            }

                            @Override
                            public void onFail() {

                            }
                        });
                        IDTask.execute();
                    }
                });

            }

        }
    }



    public boolean getPost()
    {
        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("books");
        mStorageReference = FirebaseStorage.getInstance().getReference();

        mPostReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    Book book = child.getValue(Book.class);
                    bookArrayList.add(book);
                    Log.d(TAG,""+child.getValue());

                    ArrayList<String> mImages = new ArrayList<>();
                    DataSnapshot photos = child.child("photos");
                    Log.d(TAG, "Number of images in this photo: " + String.valueOf(photos.getChildrenCount()));

                    for (DataSnapshot imageURL : photos.getChildren()){
                        mImages.add((String) imageURL.getValue());
                    }
                    imagesList.add(mImages);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return true;
    }


}