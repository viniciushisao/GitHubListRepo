package br.com.hisao.githubrepo;

import org.junit.Test;

import br.com.hisao.githubrepo.Helper.DbHelper;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void insert_data() throws Exception{

        DbHelper.storeAll(null);

    }
}