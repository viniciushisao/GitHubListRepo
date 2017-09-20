package br.com.hisao.githubrepo.Helper;

import java.util.ArrayList;
import java.util.List;

import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.util.Log;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by vinicius on 18/09/17.
 */

public class DbHelper {

    public static void storeAll(final List<Repo> repoList) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<Repo> _newsList = new RealmList<>();
                    _newsList.addAll(repoList);
                    realm.copyToRealmOrUpdate(_newsList);
                }
            });
        } catch (Exception e) {
            Log.d("dbHelper:storeAll:31 " + e.getMessage());
        }
    }

    public static List<Repo> getAll() {
        List<Repo> repoList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Repo> results = realm.where(Repo.class).findAll();
            for (Repo r : results) {
                repoList.add(r);
            }
        } catch (Exception e) {
            Log.e("DbHelper:getAll:54 " + e.getMessage());
        }
        return repoList;
    }
}

