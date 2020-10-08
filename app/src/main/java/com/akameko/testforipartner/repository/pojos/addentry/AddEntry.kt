package com.akameko.testforipartner.repository.pojos.addentry

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class is a result of 'add_entry' post to Api. Api docs: https://bnet.i-partner.ru/testAPI/
 */
class AddEntry {
    @SerializedName("status")
    @Expose
    val status: Int? = null

    @SerializedName("data")
    @Expose
    val data: AddEntryData? = null
}