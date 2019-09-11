package wiki.kotlin.starwars.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import wiki.kotlin.starwars.ext.writeParcelable

const val INVALID_ID = -1

class Person(
    var id: Int = INVALID_ID,
    var name: String,
    @SerializedName("homeworld") var homeWorld: Planet,
    var gender: String,
    @SerializedName("skin_color") var skin: String,
    var species: SpeciesArray,
    var vehicles: VehiclesArray,
    var images : StringArray = StringArray(emptyList())
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is Person && other.id != INVALID_ID) {
            return other.id == id
        }
        return super.equals(other)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readParcelable<Planet>(Planet::class.java.classLoader) as Planet,
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readParcelable<SpeciesArray>(SpeciesArray::class.java.classLoader) as SpeciesArray,
        parcel.readParcelable<VehiclesArray>(VehiclesArray::class.java.classLoader) as VehiclesArray,
        parcel.readParcelable<StringArray>(StringArray::class.java.classLoader) as StringArray
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(homeWorld, flags)
        parcel.writeString(gender)
        parcel.writeString(skin)
        parcel.writeParcelable(species, flags)
        parcel.writeParcelable(vehicles, flags)
        parcel.writeParcelable(images, flags, StringArray(emptyList()))
    }

    override fun describeContents(): Int {
        return 0
    }

    fun hasImages() = !images.isEmpty()

    companion object CREATOR : Parcelable.Creator<Person> {

        val ERROR = Person(
            INVALID_ID, "", Planet(""), "", "",
            SpeciesArray(emptyList()), VehiclesArray(emptyList()), StringArray(emptyList())
        )

        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}