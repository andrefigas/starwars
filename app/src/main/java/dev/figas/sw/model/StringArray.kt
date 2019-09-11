package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable

class StringArray(collection: Collection<String>) : ArrayList<String>(collection), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readArrayList(StringArray::class.java.classLoader) as Collection<String>
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(this as List<*>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StringArray> {
        override fun createFromParcel(parcel: Parcel): StringArray {
            return StringArray(parcel)
        }

        override fun newArray(size: Int): Array<StringArray?> {
            return arrayOfNulls(size)
        }
    }
}