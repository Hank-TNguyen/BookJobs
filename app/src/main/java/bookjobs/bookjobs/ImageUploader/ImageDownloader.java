package bookjobs.bookjobs.ImageUploader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import bookjobs.bookjobs.MyCompleteListener;

/**
 * Created by Hung on 10/19/2016.
 */
public class ImageDownloader extends AsyncTask<Void,Void,Void> {

    private URL mUrl;
    private MyCompleteListener mCL;
    private Bitmap bm;
    private final String TAG = "ImageDownloader";

    public ImageDownloader(URL url){
        this.mUrl = url;
    }

    public void setCompleteListener(MyCompleteListener mCL) {
        this.mCL = mCL;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            bm = BitmapFactory.decodeStream(mUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCL.onSuccess(bm);
        if (bm==null)
            Log.d(TAG, "damn shit ");
        else
            Log.d(TAG, "damn good");

    }
}
