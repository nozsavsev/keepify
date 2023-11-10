/*        Keepify
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

public class Keep implements Parcelable {
    public String id;
    public String title;
    public String content;
    public boolean isFavorite;

    public int addedAtTimestamp;

    protected Keep(Parcel in) {
        id = in.readString();
        isFavorite = in.readByte() != 0;
        title = in.readString();
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(title);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public Keep(String title, String content, boolean isFavorite) {
        this.id = uuidGen();
        this.title = title;
        this.content = content;
        this.isFavorite = isFavorite;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    public Keep(String title, String content) {
        this.id = uuidGen();
        this.title = title;
        this.content = content;
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    public Keep(String title) {
        this.id = uuidGen();
        this.title = title;
        this.content = "";
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    public Keep() {
        this.id = uuidGen();
        this.title = "";
        this.content = "";
        this.isFavorite = false;
        this.addedAtTimestamp = (int) (System.currentTimeMillis() / 1000L);
    }

    private String uuidGen() {
        return UUID.randomUUID().toString();
    }
}

interface KeepListListener {
    void onListUpdated(List<Keep> keeps);
}