package com.altamirano.fabricio.apptest.commons;

public class MenuItem {
    private String title = "";
    private  int imageId = 0;
    private Class klass = null;

    public MenuItem(Class klass){
        this.klass = klass;
    }
    public MenuItem(Class klass, String title){
        this.klass = klass;
        this.title = title;
    }

    public MenuItem(Class klass, String title, int ic_images) {
        this.klass = klass;
        this.title = title;
        this.imageId  = ic_images;
    }

    public int getImageId() {
        return imageId;
    }

    public Class getKlass() {
        return klass;
    }

    public String getTitle() {
        return title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setKlass(Class klass) {
        this.klass = klass;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
