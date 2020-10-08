package com.akameko.testforipartner.repository.pojos.newsession

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class is a result of 'new_session' post to Api. Api docs: https://bnet.i-partner.ru/testAPI/
 */
class NewSessionData {
    @SerializedName("session")
    @Expose
    var session: String? = null
}