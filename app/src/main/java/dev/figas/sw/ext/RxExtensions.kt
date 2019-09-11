package wiki.kotlin.starwars.ext

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


fun <T> Single<T>.io(): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.io(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

/**
 * transform iterable of singles to one observable
 */
fun <T, R> Iterable<SingleSource<out T>>.zip(zipper: (List<T>) -> R)
        : Single<R> {
    return Single.zip(this) {
        zipper.invoke(it.map { it as T })
    };
}

/**
 * transform iterable of observables to one observable
 */
fun <T, R> Iterable<ObservableSource<out T>>.zip(zipper: (List<T>) -> R)
        : Observable<R> {
    return Observable.zip(this) {
        zipper.invoke(it.map { it as T })
    };
}

/**
 * transform two singles to a pair
 */
fun <T, R> Single<T>.zipPair(other: Single<R>)
        : Single<Pair<T, R>> {
    return zipWith(other, BiFunction { t1, t2 -> Pair(t1, t2) })
}

