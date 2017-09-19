package br.com.hisao.githubrepo.presenter;

import java.util.List;

import br.com.hisao.githubrepo.MyApplication;
import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.model.RepoManager;

/**
 * Created by vinicius on 28/08/17.
 */

public class MainPresenter implements MainPresenterInterface {

    private boolean isRetrievingData;
    private int currentPage;
    private RepoManager repoManager;
    private MainPresenterInterfaceCallBack mainPresenterInterfaceCallBack;

    public MainPresenter(MainPresenterInterfaceCallBack mainPresenterInterfaceCallBack) {
        this.mainPresenterInterfaceCallBack = mainPresenterInterfaceCallBack;
        this.currentPage = 0;
        this.repoManager = new RepoManager();
        this.isRetrievingData = false;
    }

    private void retrieveData() {
        if (!isRetrievingData) {
            isRetrievingData = true;
            if (MyApplication.isInternetAvailable()) {
                repoManager.retrieveDataFromInternet(currentPage++, retrieveDataCallBack);
            } else {
                repoManager.retrieveDataFromDB(currentPage++, retrieveDataCallBack);
            }
        }
    }

    private RepoManager.RetrieveDataCallBack retrieveDataCallBack = new RepoManager.RetrieveDataCallBack() {
        @Override
        public void onDataReceived(List<Repo> repoList) {
            mainPresenterInterfaceCallBack.hideLoadingPage();

            if (repoList.size() < RepoManager.REPOS_PER_PAGE) {
                mainPresenterInterfaceCallBack.removeFooter();
            } else {
                mainPresenterInterfaceCallBack.showList(repoList);
            }

            isRetrievingData = false;
        }

        @Override
        public void onDataError() {
            mainPresenterInterfaceCallBack.showErrorPage();
            isRetrievingData = false;
        }
    };

    @Override
    public void onCallOnResume() {
        mainPresenterInterfaceCallBack.showLoadingPage();
        retrieveData();
    }

    @Override
    public void onListReachBotton() {
        retrieveData();
    }

    @Override
    public void onCallRetry() {
        mainPresenterInterfaceCallBack.hideLoadingPage();
        mainPresenterInterfaceCallBack.showLoadingPage();
        retrieveData();
    }
}
