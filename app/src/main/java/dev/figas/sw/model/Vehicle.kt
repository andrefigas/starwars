package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable

class Vehicle(url: String) : AnyFromApi(url), Parcelable {
    var name: String = ""

    constructor(parcel: Parcel) : this(parcel.readString().toString()) {
        name = parcel.readString().toString()
    }

    override fun isCompleted() = !name.isEmpty()

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }

}