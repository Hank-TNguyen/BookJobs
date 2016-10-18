package bookjobs.bookjobs;

import android.graphics.Bitmap;

/**
 * Created by Hung on 10/9/2016.
 */
public interface MyCompleteListener {
    public void onSuccess(Bitmap bm);
    public abstract void onFail();
}
