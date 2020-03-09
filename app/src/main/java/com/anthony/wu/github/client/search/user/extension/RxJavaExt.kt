package com.anthony.wu.github.client.search.user.extension

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.ioToUi(): Single<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun <T> Observable<T>.ioToUi(): Observable<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun <T> Flowable<T>.ioToUi(): Flowable<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

fun Completable.ioToUi(): Completable = observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())




