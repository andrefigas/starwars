package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable

class SpeciesArray(collection: Collection<Specie>) : ArrayList<Specie>(collection), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readArrayList(SpeciesArray::class.java.classLoader) as Collection<Specie>
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(this as List<*>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SpeciesArray> {
        override fun createFromParcel(parcel: Parcel): SpeciesArray {
            return SpeciesArray(parcel)
        }

        override fun newArray(size: Int): Array<SpeciesArray?> {
            return arrayOfNulls(size)
        }
    }
}