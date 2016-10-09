package bookjobs.bookjobs;

/**
 * Created by Hung on 9/10/2016.
 *
 */
public class Book {
    private String mTitle;
    private String mISBN;
    private String mAuthor;
    private String mGenre;
    private String picURL;
    private int noOfLikes;

    public Book(String mISBN, String mTitle) {
        this.mISBN = mISBN;
        this.mTitle = mTitle;
    }

    public Book(String mISBN, String mTitle, String mAuthor, String mGenre) {
        this.mISBN = mISBN;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mGenre = mGenre;
    }

    public Book(String mISBN, String mTitle, String mAuthor, String mGenre, String picURL, int noOfLikes){
        this.mISBN = mISBN;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mGenre = mGenre;
        this.picURL = picURL;
        this.noOfLikes = noOfLikes;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmISBN() {
        return mISBN;
    }

    public void setmISBN(String mISBN) {
        this.mISBN = mISBN;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}
