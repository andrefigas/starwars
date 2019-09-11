package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

const val NAME = "name"

class Planet(url: String) : AnyFromApi(url), Parcelable {
    @SerializedName(NAME) var name: String = ""

    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
        name = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Planet> {
        override fun createFromParcel(parcel: Parcel): Planet {
            return Planet(parcel)
        }

        override fun newArray(size: Int): Array<Planet?> {
            return arrayOfNulls(size)
        }
    }

    override fun isCompleted() = name.isNotEmpty()
}