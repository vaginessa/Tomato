package com.shivgadhia.android.tomato.models;

public class ContentsListItem {
    private final String title;

    public ContentsListItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
