package wiki.kotlin.starwars.model

import com.google.gson.annotations.SerializedName

const val URL = "url"

open class AnyFromApi(@SerializedName(URL) val url : String){
    override fun equals(other: Any?) = when(other){
        is AnyFromApi -> other.url == this.url
        else -> false
    }

    open fun isCompleted() = true

}
