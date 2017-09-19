package br.com.hisao.githubrepo;

import android.app.Application;

import br.com.hisao.githubrepo.Helper.InternetHelper;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinicius on 21/08/17.
 */

public class MyApplication extends Application {

    private static Retrofit mRetrofit;
    private static boolean isInternetAvailable = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Realm.init(this);

        isInternetAvailable = InternetHelper.isInternetAvailable(getApplicationContext());
    }

    public static boolean isInternetAvailable() {
        return isInternetAvailable;
    }

    public static Retrofit getRetrofitInstance() {
        return mRetrofit;
    }

}
