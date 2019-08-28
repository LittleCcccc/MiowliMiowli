package io.miowlimiowli.manager;

import android.app.Application;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import io.miowlimiowli.manager.sql.AppDatabase;
import io.miowlimiowli.manager.sql.SqlNews;
import io.miowlimiowli.manager.sql.SqlUserandNews;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

public class ManagerTest {

    @Test
    public void fetchDisplayableNewsbyCategory() throws InterruptedException {

        Manager.getInstance().setContext((Application) InstrumentationRegistry.getInstrumentation()
                .getTargetContext()
                .getApplicationContext());
        Manager.getInstance().user = new User("sjb", "psd");
        System.out.println("xixi");
        Manager.getInstance().FetchDisplayableNewsbyCategory(10, 1, "").observeOn(Schedulers.single()).subscribe(item->{
            System.out.println(item.get(0).title);
        });
        Thread.sleep(5000);
    }
}