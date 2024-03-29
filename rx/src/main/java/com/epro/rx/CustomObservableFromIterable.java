/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package com.epro.rx;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class CustomObservableFromIterable<T> extends Observable<T> {
    final CopyOnWriteArrayList<? extends T> source;
    public CustomObservableFromIterable(CopyOnWriteArrayList<? extends T> source) {
        this.source = source;
    }

    @Override
    public void subscribeActual(Observer<? super T> observer) {
        Iterator<? extends T> it;
        CopyOnWriteArrayList<? extends T> copyOnWriteArrayList;
        try {
            it = source.iterator();
            copyOnWriteArrayList = source;
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptyDisposable.error(e, observer);
            return;
        }
        boolean hasNext;
        try {
            hasNext = it.hasNext();
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptyDisposable.error(e, observer);
            return;
        }
        if (!hasNext) {
            EmptyDisposable.complete(observer);
            return;
        }

//        FromIterableDisposable<T> d = new FromIterableDisposable<T>(observer, it);
        FromListDisposable<T> d = new FromListDisposable<T>(observer, copyOnWriteArrayList);
        observer.onSubscribe(d);

        if (!d.fusionMode) {
            d.run();
        }
    }

    static final class FromIterableDisposable<T> extends BasicQueueDisposable<T> {

        final Observer<? super T> downstream;

        final Iterator<? extends T> it;

        volatile boolean disposed;

        boolean fusionMode;

        boolean done;

        boolean checkNext;

        FromIterableDisposable(Observer<? super T> actual, Iterator<? extends T> it) {
            this.downstream = actual;
            this.it = it;
        }

        void run() {
            boolean hasNext;

            do {
                if (isDisposed()) {
                    return;
                }
                T v;

                try {
                    v = ObjectHelper.requireNonNull(it.next(), "The iterator returned a null value");
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    downstream.onError(e);
                    return;
                }

                downstream.onNext(v);

                if (isDisposed()) {
                    return;
                }
                try {
                    hasNext = it.hasNext();
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    downstream.onError(e);
                    return;
                }
            } while (hasNext);

            if (!isDisposed()) {
                downstream.onComplete();
            }
        }

        @Override
        public int requestFusion(int mode) {
            if ((mode & SYNC) != 0) {
                fusionMode = true;
                return SYNC;
            }
            return NONE;
        }

        @Nullable
        @Override
        public T poll() {
            if (done) {
                return null;
            }
            if (checkNext) {
                if (!it.hasNext()) {
                    done = true;
                    return null;
                }
            } else {
                checkNext = true;
            }

            return ObjectHelper.requireNonNull(it.next(), "The iterator returned a null value");
        }

        @Override
        public boolean isEmpty() {
            return done;
        }

        @Override
        public void clear() {
            done = true;
        }

        @Override
        public void dispose() {
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }

    static final class FromListDisposable<T> extends BasicQueueDisposable<T> {

        final Observer<? super T> downstream;

        final CopyOnWriteArrayList<? extends T> it;

        volatile boolean disposed;

        boolean fusionMode;

        boolean done;

        boolean checkNext;

        int currentCursor;

        FromListDisposable(Observer<? super T> actual, CopyOnWriteArrayList<? extends T> it) {
            this.downstream = actual;
            this.it = it;
        }

        void run() {
            for(int i=0; i<it.size(); i++) {
                currentCursor = i;
                if (isDisposed()) {
                    return;
                }
                T v;

                try {
                    v = ObjectHelper.requireNonNull(it.get(i), "The iterator returned a null value");
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    downstream.onError(e);
                    return;
                }

                downstream.onNext(v);

                if (isDisposed()) {
                    return;
                }
            }

            if (!isDisposed()) {
                downstream.onComplete();
            }
        }

        @Override
        public int requestFusion(int mode) {
            if ((mode & SYNC) != 0) {
                fusionMode = true;
                return SYNC;
            }
            return NONE;
        }

        @Nullable
        @Override
        public T poll() {
            if (done) {
                return null;
            }
            if (checkNext) {
                if (currentCursor==it.size()-1) {
                    done = true;
                    return null;
                }
            } else {
                checkNext = true;
            }

            return ObjectHelper.requireNonNull(it.get(currentCursor), "The iterator returned a null value");
        }

        @Override
        public boolean isEmpty() {
            return done;
        }

        @Override
        public void clear() {
            done = true;
        }

        @Override
        public void dispose() {
            disposed = true;
        }

        @Override
        public boolean isDisposed() {
            return disposed;
        }
    }
}
