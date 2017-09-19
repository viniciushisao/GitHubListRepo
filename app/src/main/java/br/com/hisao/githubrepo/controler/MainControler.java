package br.com.hisao.githubrepo.controler;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hisao.githubrepo.Helper.DbHelper;
import br.com.hisao.githubrepo.Helper.GitHubService;
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

    private boolean isRetrievingData;
    private List<Repo> mRepoList;

    private MainControlerInterface mainControlerInterface;
    private int currentPage;

    public MainControler(MainControlerInterface mainControlerInterfaceLocal) {
        this.mainControlerInterface = mainControlerInterfaceLocal;
        this.currentPage = 0;
        this.mRepoList = null;
        this.isRetrievingData = false;
    }

    public void retrieveData() {
        if (!isRetrievingData) {
            isRetrievingData = true;
            if (MyApplication.isInternetAvailable()) {
                retrieveDataFromInternet();
            } else {
                retrieveDataFromDB();
            }
        }
    }

    private void retrieveDataFromDB() {
        if (mRepoList == null) {
            mRepoList = DbHelper.getAll();
        }
        List<Repo> repoList = new ArrayList<>();

        for (int i = (currentPage * REPOS_PER_PAGE); i < ((currentPage + 1) * REPOS_PER_PAGE); i++) {
            if (i < mRepoList.size()) {
                repoList.add(mRepoList.get(i));
            }
        }
        isRetrievingData = false;
        currentPage++;
        if (repoList != null) {
            mainControlerInterface.onDataReceived(repoList);
        } else {
            mainControlerInterface.onDataReceivedError();
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

                    DbHelper.storeAll(repoList);
                    DbHelper.listAll();

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
