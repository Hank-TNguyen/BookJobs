package bookjobs.bookjobs;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

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
        static String bookRef = null;
        private static final String TAG = "UPLOAD_RESULT";

        public UploadBookTask(AddBookActivity activity, Book mBook, String mUser) {
            this.mBook = mBook;
            this.mUserEmail = mUser;
            this.mActivity = activity;
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
                Map<String, String> mBookTest = new HashMap<String, String>();
                mBookTest.put("ISBN", "9781566199094");
                mBookTest.put("title", "Book of Love");
                //TODO: edit this so that it actually uploads what's on the screen
                newBookRef.setValue(mBookTest, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.e(DBTAG, "Data could not be saved. " + databaseError.getMessage());
                        } else {
                            Log.d(DBTAG, "Data saved successfully.");
                            // update the 'owns' list in user's data
                            bookRef = newBookRef.getKey();
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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, "Result: " + result);
            if (result!=null){
                Toast.makeText(mActivity, "Your book has been uploaded successfully!", Toast.LENGTH_SHORT);
                //an UUID was returned for the book. this means upload was succeeded.
                mActivity.uploadResult = result;

                mActivity.finish();
            } else {
                Toast.makeText(mActivity, "Failed to upload book!", Toast.LENGTH_SHORT);
                //otherwise...
            }

        }
    }
}
