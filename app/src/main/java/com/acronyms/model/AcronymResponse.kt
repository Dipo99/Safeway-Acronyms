package com.acronyms.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AcronymResponse : Serializable {
    @SerializedName("sf")
    var sf: String? = null

    @SerializedName("lfs")
    var lfs: List<Lf>? = null

    @SerializedName("vars")
    var vars: List<Lf>? = null

    inner class Lf : Serializable {
        @SerializedName("lf")
        var lf: String? = null

        @SerializedName("freq")
        var freq: Int? = null

        @SerializedName("since")
        var since: Int? = null
    }
}