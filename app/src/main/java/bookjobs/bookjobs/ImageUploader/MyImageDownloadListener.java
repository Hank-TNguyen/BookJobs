package bookjobs.bookjobs.ImageUploader;

import android.graphics.Bitmap;

/**
 * Created by Hung on 10/22/2016.
 */
public interface MyImageDownloadListener {
    public void onSuccess(Bitmap bm);
    public abstract void onFail();
}
