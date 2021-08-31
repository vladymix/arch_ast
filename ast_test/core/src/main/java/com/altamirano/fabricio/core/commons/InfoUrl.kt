package com.altamirano.fabricio.core.commons

class InfoUrl {
    var url: String? = null
    var url_icon: String? = null
    private var _title: String? = null
    var description: String? = null
        private set

    fun get_title(): String? {
        return _title
    }

    fun set_title(_title: String?) {
        this._title = _title
    }

    fun set_description(_description: String?) {
        description = _description
    }
}