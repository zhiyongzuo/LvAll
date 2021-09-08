package com.epro.rx;

import android.os.Bundle;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class ZipArrayJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_array_java);
        Observable<Integer> observable1 = Observable.just(1);
        Observable<Integer> observable2 = Observable.just(2);
        Observable<Integer> observable3 = Observable.just(3);
        Observable<Integer> observable4 = Observable.just(4);
        Observable<Integer> observable5 = Observable.just(5);
        Observable<Integer> observable6 = Observable.just(6);
        Observable<Integer> observable7 = Observable.just(7);
        Observable<Integer> observable8 = Observable.just(8);
        Observable<Integer> observable9 = Observable.just(9);
        Observable<Integer> observable10 = Observable.just(10);
        Observable<Integer> observable11 = Observable.just(11);
        Observable<Integer> observable12 = Observable.just(12);
        Observable<Integer> observable13 = Observable.just(13);
        Observable<Integer> observable14 = Observable.just(14);
        Observable<Integer> observable15 = Observable.just(15);

        Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, Object>() {
            @NonNull
            @Override
            public Object apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                LogUtils.e("zip--" + integer + "---" + integer2);
                return "zip";
            }
        }).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });


        Observer observer = new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogUtils.e("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Object o) {
                LogUtils.e("onNext" + GsonUtils.toJson(o));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogUtils.e(e.getMessage());
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete");
            }
        };

//        BiFunction<Integer, Integer, Integer, Integer,Integer, Integer,Integer, Integer,Integer, Integer,Integer, Integer,Integer, Object> biFunction = new BiFunction<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer,Integer, Integer, Integer, Integer,Integer>() {
//            @NonNull
//            @Override
//            public Object apply(@NonNull Integer integer, @NonNull Integer integer2, @NonNull Integer integer3, @NonNull Integer integer4 , @NonNull Integer integer5 , @NonNull Integer integer6 , @NonNull Integer integer7 , @NonNull Integer integer8, @NonNull Integer integer9, @NonNull Integer integer10, @NonNull Integer integer11, @NonNull Integer integer12, @NonNull Integer integer13) throws Exception {
//                LogUtils.e("zip--" + integer + "---" + integer2);
//                return "zipArray";
//            }
//        };
//        Observable.zipArray(Functions.toFunction(biFunction), false, 10, observable3, observable4, observable5, observable6, observable7, observable8, observable9, observable10, observable11, observable12, observable13, observable14, observable15)
//                .subscribe(observer);

        Observable.mergeArray(observable3, observable4, observable5, observable6, observable7, observable8, observable9, observable10, observable11, observable12, observable13, observable14, observable15)
                .subscribe(observer);


    }
}