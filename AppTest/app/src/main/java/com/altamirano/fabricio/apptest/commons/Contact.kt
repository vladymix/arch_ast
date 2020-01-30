package com.altamirano.fabricio.apptest.commons

data class Contact(val name:String, val idImage:Int)




object DataSample{
    fun getContacts():List<Contact> {

        val list = ArrayList<Contact>()
        list.add(Contact("Fabricio",0))
        list.add(Contact("Vanessa",0))
        list.add(Contact("Nancy",0))
        list.add(Contact("Francisco",0))
        list.add(Contact("Maria",0))
        list.add(Contact("Humberto",0))

        return list

    }
}