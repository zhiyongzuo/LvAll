package com.epro.rx;

import android.text.TextUtils;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.MainThread;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);

        Observable.just(new TestClass()).map(new Function<TestClass, Object>() {
            @Override
            public Object apply(TestClass testClass) throws Exception {
                System.out.println(Thread.currentThread().getName());
                System.out.println("apply");
                return new Object();
            }
        })
//        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(Thread.currentThread().getName());
                System.out.println("accept");
            }
        });
    }

    class TestClass{
        TestClass(){
            System.out.println(Thread.currentThread().getName());
            System.out.println("I'm created!");
        }
    }

    @Test
    public void testAddAll() {
        List<File> list = new ArrayList();

        List<File> list1 = new ArrayList();
        list1.add(new File("1"));
        list1.add(new File("2"));
        list.addAll(list1);

        List<File> list2 = new ArrayList();
        list2.add(new File("3"));
        list2.add(new File("4"));
        list2.add(new File("5"));

        list.addAll(list2);
        System.out.print(list);
    }

    @Test
    public void testSplit() {
        String qf = "";
        String cjgz = "逾期金额:24.15|2019-11-07|无";
        qf = cjgz.substring(cjgz.indexOf(":") + 1, cjgz.indexOf("|"));
        System.out.print(qf);
    }
}