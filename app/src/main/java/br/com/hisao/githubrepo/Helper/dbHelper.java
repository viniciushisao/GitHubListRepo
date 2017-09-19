package br.com.hisao.githubrepo.Helper;

import java.util.List;

import br.com.hisao.githubrepo.model.Repo;
import br.com.hisao.githubrepo.util.Log;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by vinicius on 18/09/17.
 */

public class dbHelper {

    public static void storeAll(final List<Repo> repoList) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmList<Repo> _newsList = new RealmList<>();
                    _newsList.addAll(repoList);
                    realm.insert(_newsList);
                }
            });
        } catch (Exception e) {
            Log.d("dbHelper:storeAll:31 " + e.getMessage());
        }
    }

    public static void listAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Repo> results = realm.where(Repo.class).findAll();
        int cont = 1;
        for (Repo r : results) {
            Log.d("dbHelper:listAll:44 " + (cont++) + " " + r.getId() + " - " + r.getName());
        }
    }
}

