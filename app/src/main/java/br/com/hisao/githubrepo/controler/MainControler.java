package br.com.hisao.githubrepo.controler;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;

import br.com.hisao.githubrepo.GitHubService;
import br.com.hisao.githubrepo.Helper.dbHelper;
import br.com.hisao.githubrepo.MyApplication;
import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.util.Log;
import retrofit2.Call;

/**
 * Created by vinicius on 28/08/17.
 */

public class MainControler {

    public static final int REPOS_PER_PAGE = 15;
    private static final String REPO_USER = "JakeWharton";
    private boolean isRetrievingData = false;

    private MainControlerInterface mainControlerInterface;
    private int currentPage = 0;

    public MainControler(MainControlerInterface mainControlerInterfaceLocal) {
        this.mainControlerInterface = mainControlerInterfaceLocal;
    }

    public void retrieveData() {
        if (!isRetrievingData){
            Log.d("MainControler:retrieveData:33 ");
            isRetrievingData = true;
            retrieveDataFromInternet();
        }else{
            Log.d("MainControler:retrieveData:31 ignored isRetrievingData: " + isRetrievingData);
        }
    }

    private void retrieveDataFromInternet() {
        AsyncTask<Call<List<Repo>>, Void, List<Repo>> myTask = new AsyncTask<Call<List<Repo>>, Void, List<Repo>>() {

            @Override
            protected List<Repo> doInBackground(Call<List<Repo>>... listCall) {
                List<Repo> repoList = null;
                try {
                    currentPage += 1;
                    repoList = listCall[0].execute().body();
                } catch (IOException e) {
                    Log.d("MainActivity:doInBackground:50 " + e.getMessage());
                }
                return repoList;
            }

            @Override
            protected void onPostExecute(List<Repo> repoList) {
                super.onPostExecute(repoList);
                isRetrievingData = false;
                if (repoList != null) {

                    dbHelper.storeAll(repoList);
                    dbHelper.listAll();

                    mainControlerInterface.onDataReceived(repoList);
                } else {
                    mainControlerInterface.onDataReceivedError();
                }
            }
        };
        GitHubService service = MyApplication.getRetrofitInstance().create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos(REPO_USER, REPOS_PER_PAGE, currentPage + 1);
        myTask.execute(repos, null, null);
    }
}
