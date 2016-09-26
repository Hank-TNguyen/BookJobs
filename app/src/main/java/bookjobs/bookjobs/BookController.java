package bookjobs.bookjobs;

import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
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
    public static class UploadBookTask extends AsyncTask<Void, Void, Boolean>{
        private Book mBook;
        private String mUserEmail;
        private DatabaseReference mCurrentUser;
        final String OWN_LIST = "uOwns";

        public UploadBookTask(Book mBook, String mUser) {
            this.mBook = mBook;
            this.mUserEmail = mUser;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
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
                newBookRef.setValue(mBookTest, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            Log.e(DBTAG, "Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            Log.d(DBTAG, "Data saved successfully.");
                            // update the 'owns' list in user's data
                            final String bookRef = newBookRef.getKey();
                            mCurrentUser.child("owns").child(bookRef).setValue("1");
                            //TODO: we can use this to count how many of the same books an user has
                        }
                    }
                });
            } catch (DatabaseException e){
                Log.e(DBTAG, "Error occurred", e);
            }
            // if owner is desired in book, we can modify this part

            return true;
        }

    }
}
