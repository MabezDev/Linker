package com.mabezdev.Models;

/**
 * Created by Mabez on 19/08/2016.
 */
public class Link {

    private String displayName;
    private String url;

    public Link(String displayName, String url){
        this.displayName = displayName;
        this.url = url;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
