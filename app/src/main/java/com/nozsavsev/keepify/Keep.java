/*
Keepify
Copyright (C) 2023  Ilia Nozdrachev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.nozsavsev.keepify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.UUID;

/**
 * Keep is a class that represents a note or reminder.
 * It implements Parcelable to allow it to be passed between Android components.
 */
public class Keep implements Parcelable {
    public String id;
    public String title;
    public String content;
    public boolean isFavorite;
    public int addedAtTimestamp;

    /**
     * Constructor that initializes a Keep object from a Parcel.
     * @param in The Parcel to read the object's data from.
     */
    protected Keep(Parcel in) {
        id = in.readString();
        isFavorite = in.readByte() != 0;
        title = in.readString();
        content = in.readString();
    }

    /**
     * Writes the Keep's data to a Parcel.
     * @param dest The Parcel to write the data to.
     * @param flags Additional flags about how the object should be written.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(title);
        dest.writeString(content);
    }

    /**
     * Describes the kinds of special objects contained in this Parcelable's marshalled representation.
     * @return A bitmask indicating the set of special object types marshalled by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * A public CREATOR field that generates instances of your Parcelable class from a Parcel.
     */
    public static final Creator<Keep> CREATOR = new Creator<Keep>() {
        @Override
        public Keep createFromParcel(Parcel in) {
            return new Keep(in);
        }

        @Override
        public Keep[] newArray(int size) {
            return new Keep[size];
        }
    };

    /**
     * Constructor that initializes a Keep object with a title, content, and favorite status.
     */
       public Keep(String title, String content, boolean isFavorite) {
        this.id = uuidGen();
        this.title = title;
        this.content = content;
        this.isFavorite = isFavorite;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }
     
    /**
     * Constructor that initializes a Keep object with a title and content.
     */
    public Keep(String title, String content) {
        this.id = uuidGen();
        this.title = title;
        this.content = content;
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * Constructor that initializes a Keep object with a title.
     */
    public Keep(String title) {
        this.id = uuidGen();
        this.title = title;
        this.content = "";
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * Constructor that initializes a Keep object with no title or content.
     */
    public Keep() {
        this.id = uuidGen();
        this.title = "";
        this.content = "";
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    /**
     * Generates a unique identifier for a Keep object.
     * @return A string representation of a UUID.
     */
    private String uuidGen() {
        return UUID.randomUUID().toString();
    }
}

/**
 * KeepListListener is an interface for classes that want to be notified when a list of Keep objects is updated.
 */
interface KeepListListener {
    /**
     * Called when a list of Keep objects is updated.
     * @param keeps The updated list of Keep objects.
     */
    void onListUpdated(List<Keep> keeps);
}