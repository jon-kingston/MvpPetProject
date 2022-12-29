package com.example.mvppetproject.rxTest

import androidx.lifecycle.ViewModel
import com.example.mvppetproject.helpers.log
import com.example.mvppetproject.homeMVVM.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModelRX @Inject constructor() : ViewModel() {

    private val _message = MutableStateFlow("0")
    val message: StateFlow<String> = _message

    fun test() {
//        val observable = Observable.just(1, 2, 3)
//        val disposeO = observable.subscribe(
//            { log(it.toString()) },
//        )
//
//        val single = Single.just(1)
//        val disposeS = single.subscribe(
//            {log(it.toString())}, {}
//        )
//
//        val flowable = Flowable.just(1, 2, 3)
//        val disposeF = flowable.subscribe(
//            {log(it.toString())}
//        )

//        val dispose = dataSourceObservable()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    _message.tryEmit("$it")
//                    log("next int $it")
//                },
//                { _message.tryEmit(it.localizedMessage ?: "ERROR") },
//                {}
//            )

//        val dispose = dataSourceFlowable()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    _message.tryEmit("$it")
//                    log("next int $it")
//                },
//                {_message.tryEmit(it.localizedMessage ?: "ERROR")}
//            )

//        val disposeMaybe = dataSourceMaybe()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { log("on Success $it") },
//                { log("on Error $it") },
//                { log("on Complete") }
//            )

        mapTest()
    }

    fun dataSourceObservable(): Observable<Int> {
        return Observable.create {
            for (i in 0..9000000) {
                it.onNext(i)
            }
            it.onComplete()
        }
    }

    fun dataSourceFlowable(): Flowable<Int> {
        return Flowable.create({
            for (i in 0..100000) {
                it.onNext(i)
            }
        }, BackpressureStrategy.MISSING)
    }

    fun dataSourceCompletable(): Completable {
        return Completable.create {
            it.onComplete()
        }
    }

    fun dataSourceMaybe(): Maybe<Int> {
        return Maybe.create {
            it.onSuccess(5)
            it.onComplete()
        }
    }

    // simple example of thread
    fun testT() {
        Thread {
            Thread.sleep(4000)
            _message.tryEmit("thread finished")
        }.start()
    }

    // test map
    fun mapTest() {
        Observable.just("1", "2", "3", "4")
            .concatMap {
//            .flatMap {
                val delay = Random.nextInt(10)
                log("delay $delay")
                Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
            }
            .map { "$it add by map" }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    log("item $it")
                }, {
                    log("item ${it.localizedMessage}")
                }
            )
    }
}