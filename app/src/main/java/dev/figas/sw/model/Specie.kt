package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable

class Specie(url: String) : AnyFromApi(url), Parcelable {
    var name: String = ""

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

    companion object CREATOR : Parcelable.Creator<Specie> {
        override fun createFromParcel(parcel: Parcel): Specie {
            return Specie(parcel)
        }

        override fun newArray(size: Int): Array<Specie?> {
            return arrayOfNulls(size)
        }
    }

    override fun isCompleted() = !name.isEmpty()
}