package com.example.tzmegafon.data.remote.model

import com.google.gson.annotations.SerializedName

data class TodoModelRemote(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("desc")
    val desc:String,
    @SerializedName("date")
    val date:String,
    @SerializedName("active")
    val active:Boolean,
    @SerializedName("pathImage")
    val pathImage:String,
    @SerializedName("audioPath")
    val audioPath:String,
    @SerializedName("audioName")
    val audioName:String
)