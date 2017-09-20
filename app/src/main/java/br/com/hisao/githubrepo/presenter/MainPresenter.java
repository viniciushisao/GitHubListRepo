package br.com.hisao.githubrepo.presenter;

import java.util.List;

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
    private boolean isInternetDataAvailable;
    private boolean hasFinished;

    public MainPresenter(MainPresenterInterfaceCallBack mainPresenterInterfaceCallBack) {
        this.mainPresenterInterfaceCallBack = mainPresenterInterfaceCallBack;
        this.currentPage = 0;
        this.repoManager = new RepoManager();
        this.isRetrievingData = false;
        this.isInternetDataAvailable = true;
        this.hasFinished = false;
    }

    private void retrieveData() {
        if (!isRetrievingData && !hasFinished) {
            isRetrievingData = true;
            if (isInternetDataAvailable) {
                this.mainPresenterInterfaceCallBack.hideFailInternetData();
                repoManager.retrieveDataFromInternet(currentPage, retrieveDataCallBack);
            } else {
                mainPresenterInterfaceCallBack.showFailInternetData();
                repoManager.retrieveDataFromDB(currentPage, retrieveDataCallBack);
            }
        }

        if (hasFinished) {
            mainPresenterInterfaceCallBack.removeFooter();
        }
    }

    private RepoManager.RetrieveDataCallBack retrieveDataCallBack = new RepoManager.RetrieveDataCallBack() {
        @Override
        public void onDataReceived(List<Repo> repoList) {
            mainPresenterInterfaceCallBack.hideLoadingPage();

            if (repoList.size() < RepoManager.REPOS_PER_PAGE) {
                hasFinished = true;
                mainPresenterInterfaceCallBack.showList(repoList);
                mainPresenterInterfaceCallBack.removeFooter();
            } else {
                mainPresenterInterfaceCallBack.showList(repoList);
            }
            currentPage++;
            isRetrievingData = false;
        }

        @Override
        public void onDataError() {
            isRetrievingData = false;
            if (isInternetDataAvailable && currentPage == 0) {
                isInternetDataAvailable = false;
                retrieveData();
            } else {
                mainPresenterInterfaceCallBack.showErrorPage();
            }
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
        isInternetDataAvailable = true;
        hasFinished = false;
        currentPage = 0;
        mainPresenterInterfaceCallBack.hideErrorPage();
        mainPresenterInterfaceCallBack.showLoadingPage();
        retrieveData();
    }
}
