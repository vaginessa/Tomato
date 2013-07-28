package com.shivgadhia.android.tomato.persistance;

import android.net.Uri;
import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class TomatoProvider extends SQLiteContentProviderImpl {

    public static final String AUTHORITY = "content://com.shivgadhia.android.tomato/";

    private Uri createUri(String tableName) {
        return Uri.parse(TomatoProvider.AUTHORITY + tableName);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = super.delete(uri, selection, selectionArgs);
        notifyUriChange(createUri(Tables.VIEW_BLOGS));
        return result;
    }
}