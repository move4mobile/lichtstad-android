package com.move4mobile.lichtstad.model;

import com.google.firebase.database.PropertyName;

public class ResultContent {

    private String html;

    @PropertyName("html")
    public String getHtml() {
        return html;
    }

    @PropertyName("html")
    public void setHtml(String html) {
        this.html = html;
    }
}
