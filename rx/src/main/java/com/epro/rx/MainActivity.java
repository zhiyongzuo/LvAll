package com.epro.rx;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.Utils;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;
import com.trello.rxlifecycle3.components.RxActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class MainActivity extends RxActivity {
    CopyOnWriteArrayList<ObservableSource> observableSourceArrayList = new CopyOnWriteArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_custom_moving_view);
//        ActivityUtils.startActivity(new Intent(Utils.getApp(), IteratorActivity.class));//intent is unavailable  //没有在androidmanifest中注册
//        startActivity(new Intent(Utils.getApp(), IteratorActivity.class));
//        startActivity(new Intent(Utils.getApp(), BackPressureActivity.class));
//        FromIterableActivity.Companion.startFromIterableActivity();

        findViewById(R.id.custom_moving_view_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//
//            }
//        }).doOnNext(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e("doOnNext", Thread.currentThread().getName());
//            }
//        }).


        //测试single 解绑
        //E/onSubscribe: main
        //E/accept: main
        //E/doFinally: main
        //E/apply: RxCachedThreadScheduler-1
        //E/onSuccess: main

        //之前是上面，sleep了5s之后，执行下面
//        E/onSubscribe: main
//        E/accept: main
//        E/doFinally: main
//        E/apply: RxCachedThreadScheduler-1
//        E/onError: main
//
//        Single.just(1)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        Log.e("accept", Thread.currentThread().getName());
//                        //accept: main
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .doFinally(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.e("doFinally", Thread.currentThread().getName());
//                        //doFinally: main
//                    }
//                })
//                .observeOn(Schedulers.io())
//                .map(new Function<Integer, List<Object>>() {
//                    @Override
//                    public List<Object> apply(Integer integer) throws Exception {
//                        Log.e("apply", Thread.currentThread().getName());
//                        //apply: RxCachedThreadScheduler-1
//                        List<Object> objectList = new ArrayList<>();
//
//
//                        Thread.sleep(5000);
//
//                        return objectList;
//                }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<List<Object>>bindToLifecycle())
//                .subscribe(new SingleObserver<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e("onSubscribe", Thread.currentThread().getName());
//                        //onSubscribe: main
//                    }
//
//                    @Override
//                    public void onSuccess(Object ObjectList) {
//                        Log.e("onSuccess", Thread.currentThread().getName());
//                        //onSuccess: main
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("onError", Thread.currentThread().getName());
//                    }
//                });
////                .subscribe(new SingleObserver<List<Object>>() {
////                    @Override
////                    public void onSubscribe(Disposable d) {
////                        Log.e("onSubscribe", Thread.currentThread().getName());
////                    }
////
////                    @Override
////                    public void onSuccess(List<Object> ObjectList) {
////                        Log.e("onSuccess", Thread.currentThread().getName());
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////                        Log.e("onError", Thread.currentThread().getName());
////                    }
////                });

        observableSourceArrayList.clear();
        Observable observableFirst = Observable.just("1").flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                return Observable.just("11");
            }
        });

        Observable observableSecond = Observable.just("2").flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                observableSourceArrayList.add(3, Observable.just("888"));//为什么加上这行会报错 null
                return Observable.just("22");
            }
        });

        Observable observableThird =
                Observable.just("33");
//                Observable.create(new ObservableOnSubscribe() {
//                    @Override
//                    public void subscribe(ObservableEmitter emitter) throws Exception {
//                        throw new NullPointerException("33333333xxxxxxNPE ");
//                    }
//                })
                ;

        observableSourceArrayList.add(observableFirst);
        observableSourceArrayList.add(observableSecond);
        observableSourceArrayList.add(observableThird);
//        Observable.merge(observableThird,observableFirst)
//        Observable.fromArray(observableThird,observableFirst)
//        Observable.fromArray(observableFirst, observableSecond, observableThird)
//        主要在于这个 delayErrors
//          Observable.fromArray(observableSourceArrayList.toArray())

        RxJavaPlugins.onAssembly(new CustomObservableFromIterable(observableSourceArrayList))
//          Observable.fromIterable(observableSourceArrayList)
                  .flatMap((Function) Functions.identity(), false, 1)
//                  .flatMap(new Function<Object, ObservableSource<Object>>() {
//                      @Override
//                      public ObservableSource<Object> apply(Object o) throws Exception {
//                          return (ObservableSource<Object>)o;
//                      }
//                  })
                  .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("11", "onSubscribe");
                        observableSourceArrayList.add(Observable.just("999"));
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.e("11", "onNext" + GsonUtils.toJson(o));
//                        Log.e("11", GsonUtils.toJson(observableSourceArrayList));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("11", "onError-" + e.getMessage() + "-" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("11", "onComplete");
                    }
                });

    }
}
