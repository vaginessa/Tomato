package com.shivgadhia.android.tomato;

import android.app.Application;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.cache.LruBitmapCache;

public class TomatoApplication extends Application {
    private static ImageManager imageManager;

    @Override
    public void onCreate() {
        super.onCreate();
        LoaderSettings settings = new LoaderSettings.SettingsBuilder().withDisconnectOnEveryCall(true)
                .withCacheManager(new LruBitmapCache(this)).build(this);
        imageManager = new ImageManager(this, settings);
    }

    public static ImageManager getImageManager() {
        return imageManager;
    }



}
