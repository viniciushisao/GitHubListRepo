package br.com.hisao.githubrepo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.hisao.githubrepo.R;
import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.util.Log;

/**
 * Created by vinicius on 19/08/17.
 */

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.ViewHolder> {


    private static List<Repo> mRepoList;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public GithubAdapter(List<Repo> repoList) {
        this.mRepoList = repoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView;
        switch (viewType) {
            case TYPE_ITEM:
                contactView = inflater.inflate(R.layout.githubrepo_list_item, parent, false);
                return new ViewHolder(contactView);
            case TYPE_FOOTER:
                contactView = inflater.inflate(R.layout.githubrepo_list_item_footer, parent, false);
                return new ViewHolder(contactView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d("GithubAdapter:onBindViewHolder:41 " + position + " - " + this.getItemCount());

        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                Repo repo = mRepoList.get(position);
                if (repo != null) {
                    holder.txvId.setText(repo.getId().toString());
                    holder.txvName.setText(repo.getName());
                    holder.txvDescription.setText(repo.getDescription());
                }
        }
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvName;
        TextView txvId;
        TextView txvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            txvDescription = itemView.findViewById(R.id.txvDescription);
            txvId = itemView.findViewById(R.id.txvId);
            txvName = itemView.findViewById(R.id.txvName);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private static boolean isPositionFooter(int position) {
        return position == mRepoList.size() - 1;
    }
}
