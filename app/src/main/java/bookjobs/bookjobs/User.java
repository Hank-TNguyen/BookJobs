package bookjobs.bookjobs;

/**
 * Created by Hung on 9/8/2016.
 * User class for BookJobs app, this contains the basic particulars of the users
 *
 * 2 constructors:
 *  User(): Default constructor required for calls to DataSnapshot.getValue(User.class)
 *  User(...): actual constructor where info is filled
 */
public class User {

    private String Name;
    // email is also the ID because of its uniqueness
    private String Email;
    private String Bio;
    private String Address;

    public User(){}
    //Default constructor required for calls to DataSnapshot.getValue(User.class)

    public User(String emailAddress) {
        this.Email = emailAddress;
    }

    public User(String name, String email) {
        Name = name;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getBio() {
        return Bio;
    }

    public void setBio(String bio) {
        this.Bio = bio;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

}
