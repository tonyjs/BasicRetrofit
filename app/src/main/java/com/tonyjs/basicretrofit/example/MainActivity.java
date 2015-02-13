package com.tonyjs.basicretrofit.example;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tonyjs.basicretrofit.NetworkLoader;
import com.tonyjs.basicretrofit.NetworkWorker;
import com.tonyjs.basicretrofit.NetworkWorkpiece;

import java.util.List;

import retrofit.RestAdapter;


public class MainActivity extends ActionBarActivity
        implements NetworkLoader.Callback<NetworkWorkpiece<List<Repo>>>{

    private View mProgressBar;
    private ArrayAdapter<Repo> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progress_bar);

        ListView listView = (ListView) findViewById(R.id.list_view);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
    }

    private ProgressDialog mDialog;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage("Loading...");
        }

        NetworkWorker<List<Repo>> worker = new NetworkWorker<List<Repo>>() {
            @Override
            public List<Repo> getWorkpiece() {
                return getApiInterface().listRepos("tonyjs");
            }
        };

        new NetworkLoader.Builder<List<Repo>>()
                .setProgressBar(mProgressBar)
                .setCallback(this)
                .load(worker);
    }

    @Override
    public void callback(NetworkWorkpiece<List<Repo>> workpiece) {
        if (isFinishing()) {
            return;
        }

        if (!workpiece.isSuccess()) {
            Toast.makeText(this, workpiece.getError(), Toast.LENGTH_SHORT).show();
            return;
        }

        mAdapter.addAll(workpiece.getData());

//        Toast.makeText(this, workpiece.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ApiInterface mApiInterface;
    public ApiInterface getApiInterface() {
        if (mApiInterface == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiInterface.END_POINT)
                    .setLogLevel(BuildConfig.DEBUG ?
                            RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.BASIC)
                    .build();

            mApiInterface = adapter.create(ApiInterface.class);
        }
        return mApiInterface;
    }

}
