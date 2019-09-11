package wiki.kotlin.starwars.ext

import android.os.Parcel
import android.os.Parcelable

/**
 * transform data iterable to string
 */
fun <T> StringBuilder.format(iterable: Iterable<T>, function: (T) -> String, separator: String = " ; "): StringBuilder {
    iterable.forEach { append("${function.invoke(it)}$separator") }
    if (length > 1) {
        deleteCharAt(length - separator.length)
    }
    return this
}

/**
 * writeParcelable with default value
 */
fun Parcel.writeParcelable(parcelable: Parcelable?, flags: Int, default: Parcelable) {
    writeParcelable(
        parcelable ?: default,
        flags
    )
}

