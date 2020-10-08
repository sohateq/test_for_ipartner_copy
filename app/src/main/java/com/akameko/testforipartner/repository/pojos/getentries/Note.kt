package com.akameko.testforipartner.repository.pojos.getentries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class is a result of 'get_entries' post to Api. Api docs: https://bnet.i-partner.ru/testAPI/
 */
class Note {
    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("body")
    @Expose
    val body: String? = null

    @SerializedName("da")
    @Expose
    val da: String = "0000000000"

    @SerializedName("dm")
    @Expose
    val dm: String = "0000000000"
}