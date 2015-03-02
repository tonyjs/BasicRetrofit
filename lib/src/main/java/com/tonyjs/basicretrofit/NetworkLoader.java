package com.tonyjs.basicretrofit;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

/**
 * Created by tonyjs on 15. 2. 13..
 */
public class NetworkLoader<T>
        extends AsyncTask<NetworkWorker, Integer, NetworkWorkpiece> {

    public static final String TAG = NetworkLoader.class.getSimpleName();

    public interface Callback<NW extends NetworkWorkpiece> {
        public void callback(NW workpiece);
    }

    NetworkLoader() {}

    private View mProgressBar;

    public void setProgressBar(View progressBar) {
        mProgressBar = progressBar;
    }

    private Dialog mDialog;

    public void setProgressDialog(Dialog dialog) {
        mDialog = dialog;
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void load(NetworkWorker networkWorker) {
        load(true, networkWorker);
    }

    public void load(boolean threadPoolExecute, NetworkWorker networkWorker) {
        executeOnExecutor(
                threadPoolExecute ? THREAD_POOL_EXECUTOR : SERIAL_EXECUTOR, networkWorker);
    }

    @Override
    protected void onPreExecute() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    protected NetworkWorkpiece<T> doInBackground(NetworkWorker... params) {
        NetworkWorker networkRunnable = params[0];
        if (networkRunnable == null) {
            NetworkWorkpiece workpiece = new NetworkWorkpiece();
            workpiece.setSuccess(false);
            workpiece.setData(null);
            workpiece.setError("NetworkWorkpiece is null");
            return workpiece;
        }

        NetworkWorkpiece<T> workpiece = new NetworkWorkpiece<>();

        try {
            workpiece.setData((T) networkRunnable.getWorkpiece());
            workpiece.setError(null);
            workpiece.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            workpiece.setData(null);
            workpiece.setError(e.toString());
            workpiece.setSuccess(false);
        }

        return workpiece;
    }

    @Override
    protected void onPostExecute(NetworkWorkpiece workpiece) {
        if (isCancelled() || workpiece == null) {
            Log.e(TAG, isCancelled() ? "Cancelled" : "Workpiece is null");
            return;
        }

        if (mCallback == null) {
            Log.d(TAG, workpiece.toString());
            return;
        }

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        if (mDialog != null) {
            mDialog.dismiss();
        }

        mCallback.callback(workpiece);
    }

    public static final class Builder<E> {
        NetworkLoader<E> loader;

        public Builder() {
            loader = new NetworkLoader<>();
        }

        public Builder setProgressBar(View progressBar) {
            loader.setProgressBar(progressBar);
            return this;
        }

        public Builder setProgressDialog(Dialog dialog) {
            loader.setProgressDialog(dialog);
            return this;
        }

        public Builder setCallback(Callback callback) {
            loader.setCallback(callback);
            return this;
        }

        public NetworkLoader<E> load(NetworkWorker worker){
            loader.load(worker);
            return loader;
        }

        public NetworkLoader<E> load(boolean threadPoolExecute,
                                     NetworkWorker worker){
            loader.load(threadPoolExecute, worker);
            return loader;
        }
    }
}
