package com.akameko.testforipartner.repository.pojos.getentries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class is a result of 'get_entries' post to Api. Api docs: https://bnet.i-partner.ru/testAPI/
 */
class GetEntries {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("data")
    @Expose
    var data: List<List<Note>>? = null
}