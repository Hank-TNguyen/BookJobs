package bookjobs.bookjobs;

import java.util.Date;

/**
 * Created by Aliasgar on 7/10/16.
 */
public class Match {
    Book book;
    User userWants;
    User userOwns;
    Date dateOfMatch;
    boolean ownerStatus;
    boolean wanterStatus;

    public double getDistance() {
        return distance;
    }

    public void setDistance() {
        this.distance = Utility.calculateDistance(userOwns.getLocation(),userWants.getLocation());
    }

    double distance;

    public User getUserWants() {
        return userWants;
    }

    public void setUserWants(User userWants) {
        this.userWants = userWants;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUserOwns() {
        return userOwns;
    }

    public void setUserOwns(User userOwns) {
        this.userOwns = userOwns;
    }

    public Date getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(Date dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public boolean isOwnerStatus() {
        return ownerStatus;
    }

    public void setOwnerStatus(boolean ownerStatus) {
        this.ownerStatus = ownerStatus;
    }

    public boolean isWanterStatus() {
        return wanterStatus;
    }

    public void setWanterStatus(boolean wanterStatus) {
        this.wanterStatus = wanterStatus;
    }


}
