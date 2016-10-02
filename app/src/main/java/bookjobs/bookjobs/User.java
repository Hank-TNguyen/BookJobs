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
    // email is also the ID because of its uniqueness
    private String uEmail;
    private String uBio;
    private String uAddress;

    public User(){}
    //Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String emailAddress) {
        this.uEmail = emailAddress;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuBio() {
        return uBio;
    }

    public void setuBio(String uBio) {
        this.uBio = uBio;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

}
