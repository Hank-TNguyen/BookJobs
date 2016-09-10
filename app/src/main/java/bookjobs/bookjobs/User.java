package bookjobs.bookjobs;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Hung on 9/8/2016.
 * User class for BookJobs app, this contains the basic particulars of the users
 *
 * 2 constructors:
 *  User(): Default constructor required for calls to DataSnapshot.getValue(User.class)
 *  User(...): actual constructor where info is filled
 */
public class User {

    private String uName;
    private String uID;
    private String uPassword;
    private String uBio;
    private String uAddress;
    private ArrayList<String> uOwns;
    private ArrayList<String> uInterested;
    private ArrayList<String> uReviews;
    private ArrayList<String> uTransaction;

    public User(){}
    //Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String uName, String uPassword) {
        this.uName = uName;
        this.uPassword = uPassword;
    }
}
