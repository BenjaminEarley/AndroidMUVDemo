package com.benjaminearley.githubapi.data

import android.os.Parcel
import android.os.Parcelable

data class User(var login: String?, var blog: String?, var publicRepos: Int?) : Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(login)
        dest?.writeString(blog)
        dest?.writeInt(publicRepos ?: 0)
    }
}
