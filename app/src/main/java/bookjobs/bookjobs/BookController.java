package bookjobs.bookjobs;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Hung on 9/12/2016.
 */
public class BookController {

    private static DatabaseReference mDatabase;
    private static DatabaseReference mBooksDatabase;
    private static final String DBTAG = "DB in BookController";
    public BookController() {}

    /**
     * Book strictly contains the information regarding to the book, i.e. Genre, author, ISBN and title
     * That means to keep track of the owner, we need to update the users database in own
     *
     */
    public static class UploadBookTask extends AsyncTask<Void, Void, String> {
        private Book mBook;
        private String mUserEmail;
        private DatabaseReference mCurrentUser;
        final String OWN_LIST = "uOwns";
        private AddBookActivity mActivity;
        private ArrayList<Uri> pictures;
        private UploadCompleteListener completeListener;
        static String bookRef = null;
        private static final String TAG = "UPLOAD_RESULT";

        public UploadBookTask(AddBookActivity activity, UploadCompleteListener mCompleteListener, Book mBook, String mUser, ArrayList<Uri> picturesList) {
            this.mBook = mBook;
            this.mUserEmail = mUser;
            this.completeListener = mCompleteListener;
            this.mActivity = activity;
            this.pictures = picturesList;
        }


        @Override
        protected String doInBackground(Void... params) {
            // Database Connection, if no connection or what not, exception will be here
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Log.d(DBTAG, mDatabase.toString());

            // 'child database'
            mBooksDatabase = mDatabase.child("books");
            mCurrentUser = mDatabase.child("users").child(mUserEmail);

            // address to upload the book, later we can call newBookRef.getKey() to get the ID
            // and use the ID to indicate the relationship between the owner and the book
            final DatabaseReference newBookRef = mBooksDatabase.push();
            try {
                newBookRef.setValue(mBook, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.e(DBTAG, "Data could not be saved. " + databaseError.getMessage());
                        } else {
                            Log.d(DBTAG, "Data saved successfully.");
                            // update the 'owns' list in user's data
                            bookRef = newBookRef.getKey();
                            onResult(bookRef);
                            Log.d(DBTAG, "Data saved successfully. bookRef = " + bookRef);
                            mCurrentUser.child("owns").child(bookRef).setValue("1");

                            //TODO: we can use this to count how many of the same books an user has
                        }
                    }
                });
            } catch (DatabaseException e){
                Log.e(DBTAG, "Error occurred", e);
            }

            // if owner is desired in book, we can modify this part
            return bookRef;
        }


        protected void onResult(String result) {
            Log.d(TAG, "Book Reference: " + result);
            if (result!=null){
                //an UUID was returned for the book. this means upload was succeeded.
                mActivity.uploadResult = result;
                completeListener.onSuccess();


            } else {
                completeListener.onFail();
                //otherwise...
            }
        }
    }

    public static void updateBookPicture(Uri uri, String bookRef){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mBooksDatabase = mDatabase.child("books").child(bookRef);
        final DatabaseReference newPhoto = mBooksDatabase.child("photos").push();
        newPhoto.setValue(uri);
    }

}
