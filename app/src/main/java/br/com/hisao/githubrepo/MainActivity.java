package br.com.hisao.githubrepo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.hisao.githubrepo.adapter.GithubAdapter;
import br.com.hisao.githubrepo.presenter.MainPresenter;
import br.com.hisao.githubrepo.presenter.MainPresenterInterface;
import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.presenter.MainPresenterInterfaceCallBack;

public class MainActivity extends AppCompatActivity implements MainPresenterInterfaceCallBack {


    private RecyclerView rcvContacts;
    private RelativeLayout rllLoading;
    private RelativeLayout rllError;
    private RelativeLayout rllList;
    private Button btnTryAgain;
    private LinearLayoutManager linearLayoutManager;
    private TextView txvNoInternet;
    private MainPresenterInterface mainPresenterInterface;
    private GithubAdapter adapter;
    private List<Repo> currentRepoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutManager = new LinearLayoutManager(this);
        rcvContacts = (RecyclerView) findViewById(R.id.rcvList);
        rllLoading = (RelativeLayout) findViewById(R.id.rllLoading);
        rllError = (RelativeLayout) findViewById(R.id.rllError);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        rllList = (RelativeLayout) findViewById(R.id.rllList);
        txvNoInternet = (TextView) findViewById(R.id.txvNoInternet);
        rcvContacts.setHasFixedSize(true);

        mainPresenterInterface = new MainPresenter(this);

        if (MyApplication.isInternetAvailable()) {
            txvNoInternet.setVisibility(View.GONE);
        } else {
            txvNoInternet.setVisibility(View.VISIBLE);
        }

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenterInterface.onCallRetry();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenterInterface.onCallOnResume();
    }

    @Override
    public void removeFooter() {
        adapter.notifyItemRemoved(currentRepoList.size() + 1);
    }

    @Override
    public void showList(List<Repo> repoList) {
        rllList.setVisibility(View.VISIBLE);
        if (adapter == null) {
            currentRepoList = repoList;
            adapter = new GithubAdapter(currentRepoList);
            rcvContacts.setLayoutManager(linearLayoutManager);
            rcvContacts.setAdapter(adapter);
            rcvContacts.addOnScrollListener(recyclerViewOnScrollListener);
        } else {
            currentRepoList.addAll(repoList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoadingPage() {
        rllList.setVisibility(View.GONE);
        rllLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingPage() {
        rllLoading.setVisibility(View.GONE);
    }

    @Override
    public void showErrorPage() {
        rllList.setVisibility(View.GONE);
        rllError.setVisibility(View.VISIBLE);
    }

    private void hideErrorPage() {
        rllList.setVisibility(View.GONE);
        rllError.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if ((totalItemCount - lastVisibleItemPosition) < 3) {
                mainPresenterInterface.onListReachBotton();
            }
        }
    };
}
