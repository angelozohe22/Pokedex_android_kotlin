package com.angelo.pokedex.model

class Pokemon(val number:Int,
              val name:String,
              val url:String){
    fun getNumero():Int{
        val urlPartes = url.split("/").toTypedArray()
        return urlPartes[urlPartes.size-2].toInt()
    }
}