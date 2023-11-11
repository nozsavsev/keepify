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

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_FIRST_RUN = "firstRun";

    public static boolean isFirstRun(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean(KEY_FIRST_RUN, true);

        // If it's the first run, update the preference and return true
        if (isFirstRun) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(KEY_FIRST_RUN, false);
            editor.apply();
        }

        return isFirstRun;
    }

}
