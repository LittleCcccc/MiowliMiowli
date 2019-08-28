package io.miowlimiowli.manager;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.List;

import io.miowlimiowli.manager.sql.SqlId;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

public class DisplayableNewsTest {

    @Test
    public void setRead() throws InterruptedException {
        Manager.getInstance().setContext((Application) InstrumentationRegistry.getInstrumentation()
                .getTargetContext()
                .getApplicationContext());
        Manager manager = Manager.getInstance();
        manager.user = new User("sjb", "psd");
        manager.FetchDisplayableNewsbyCategory(10, 1, "")
        .observeOn(Schedulers.single()).subscribe(items->{
            System.out.println(items.size());
            for(DisplayableNews news : items){
                System.out.println("hehe");
                news.setRead(true);
            }
        });
        Thread.sleep(5000);
        System.out.println("haha");
        List<SqlId> list = manager.db.SqlUserandNewsDao().getLikeListByUsername("sjb");
        System.out.println(list.size());

    }
}