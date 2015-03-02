# BasicRetrofit
Sample Of Retrofit.
Using Retrofit with AsyncTask

    View progressBar = findViewById(R.id.progress_bar);

    new NetworkLoader.Builder<List<Repo>>()
      .setProgressBar(progressBar)
      .setCallback(new NetworkLoader.Callback() {
          @Override
          public void callback(NetworkWorkpiece workpiece) {
              if (isFinishing()) {
                  return;
              }
  
              if (!workpiece.isSuccess()) {
                  Toast.makeText(
                          getApplicationContext(), workpiece.getError(), Toast.LENGTH_SHORT).show();
                  return;
              }
          }
      })
      .load(new NetworkWorker<List<Repo>>() {
          @Override
          public List<Repo> getWorkpiece() {
              return getApiInterface().listRepos("tonyjs");
          }
      });

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
