package bookjobs.bookjobs;

/**
 * Created by Hung on 10/9/2016.
 */
public interface UploadCompleteListener {
    public void onSuccess();
    public abstract void onFail();
}
