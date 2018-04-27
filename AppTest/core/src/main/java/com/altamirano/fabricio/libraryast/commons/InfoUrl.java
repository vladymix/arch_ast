package com.altamirano.fabricio.libraryast.commons;

/**
 * @autor Created by Fabricio Altamirano on 27/3/18.
 */

public class InfoUrl {
    private String url =null;
    private String url_icon = null;
    private String _title= null;
    private String _description = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_icon() {
        return url_icon;
    }

    public void setUrl_icon(String url_icon) {
        this.url_icon = url_icon;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String getDescription() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

}
