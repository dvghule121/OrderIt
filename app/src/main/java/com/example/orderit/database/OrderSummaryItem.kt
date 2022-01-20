package com.example.orderit.database

import android.os.Parcel
import android.os.Parcelable

class OrderSummaryItem(val name: String?, val price:Int, val img: Int?, val qtty:Int ): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeValue(img)
        parcel.writeInt(qtty)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderSummaryItem> {
        override fun createFromParcel(parcel: Parcel): OrderSummaryItem {
            return OrderSummaryItem(parcel)
        }

        override fun newArray(size: Int): Array<OrderSummaryItem?> {
            return arrayOfNulls(size)
        }
    }
}