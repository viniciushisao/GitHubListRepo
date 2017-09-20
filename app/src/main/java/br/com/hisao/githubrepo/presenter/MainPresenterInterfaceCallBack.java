package br.com.hisao.githubrepo.presenter;

import java.util.List;

import br.com.hisao.githubrepo.model.Repo;

/**
 * Created by vinicius on 28/08/17.
 */

public interface MainPresenterInterfaceCallBack {

    void showLoadingPage();

    void hideLoadingPage();

    void removeLoadingFooter();

    void showList(List<Repo> repoList);

    void showErrorPage();

    void showFailInternetData();

    void hideFailInternetData();

    void hideErrorPage();

    void setPageTitle(String pageTitle);
}