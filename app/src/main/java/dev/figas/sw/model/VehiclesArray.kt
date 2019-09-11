package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable

class VehiclesArray(array: Collection<Vehicle>) : ArrayList<Vehicle>(array.toList()), Parcelable{

    constructor(parcel: Parcel) : this(parcel.readArray(Vehicle::class.java.classLoader)?.toList() as Collection<Vehicle>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(this as List<*>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VehiclesArray> {
        override fun createFromParcel(parcel: Parcel): VehiclesArray {
            return VehiclesArray(parcel)
        }

        override fun newArray(size: Int): Array<VehiclesArray?> {
            return arrayOfNulls(size)
        }
    }

}