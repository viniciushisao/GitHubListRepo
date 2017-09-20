package br.com.hisao.githubrepo.model;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.hisao.githubrepo.Helper.DbHelper;
import br.com.hisao.githubrepo.Helper.GitHubService;
import br.com.hisao.githubrepo.MyApplication;
import br.com.hisao.githubrepo.util.Log;
import retrofit2.Call;

/**
 * Created by vinicius on 19/09/17.
 */

public class RepoManager {
    private static final String REPO_USER = "JakeWharton";
    public static final int REPOS_PER_PAGE = 15;
    private List<Repo> mRepoList;

    public void retrieveDataFromDB(int page, RetrieveDataCallBack retrieveDataCallBack) {
        if (mRepoList == null) {
            mRepoList = DbHelper.getAll();
        }
        List<Repo> repoList = new ArrayList<>();

        for (int i = (page * REPOS_PER_PAGE); i < ((page + 1) * REPOS_PER_PAGE); i++) {
            if (i < mRepoList.size()) {
                repoList.add(mRepoList.get(i));
            }
        }
        if (mRepoList.size() > 0){
            retrieveDataCallBack.onDataReceived(repoList);
        }else{
            retrieveDataCallBack.onDataError();
        }
    }

    public void retrieveDataFromInternet(int page, final RetrieveDataCallBack retrieveDataCallBack) {
        AsyncTask<Call<List<Repo>>, Void, List<Repo>> myTask = new AsyncTask<Call<List<Repo>>, Void, List<Repo>>() {

            @Override
            protected List<Repo> doInBackground(Call<List<Repo>>... listCall) {
                List<Repo> repoList = null;
                try {
                    repoList = listCall[0].execute().body();
                } catch (IOException e) {
                    Log.d("MainActivity:doInBackground:50 " + e.getMessage());
                }
                return repoList;
            }

            @Override
            protected void onPostExecute(List<Repo> repoList) {
                super.onPostExecute(repoList);
                if (repoList != null) {

                    DbHelper.storeAll(repoList);
                    DbHelper.listAll();
                    retrieveDataCallBack.onDataReceived(repoList);
                } else {
                    retrieveDataCallBack.onDataError();
                }
            }
        };
        GitHubService service = MyApplication.getRetrofitInstance().create(GitHubService.class);
        Call<List<Repo>> repos = service.listRepos(REPO_USER, REPOS_PER_PAGE, page);
        myTask.execute(repos, null, null);
    }

    public interface RetrieveDataCallBack {
        void onDataReceived(List<Repo> repoList);

        void onDataError();
    }
}
