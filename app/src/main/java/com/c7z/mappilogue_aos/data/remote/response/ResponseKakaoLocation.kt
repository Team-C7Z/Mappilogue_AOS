package com.c7z.mappilogue_aos.data.remote.response

data class ResponseKakaoLocation (
    val meta : Meta,
    val documents : List<Document>
)  {
    data class Meta (
        val is_end : Boolean
    )

    data class Document (
        val id : String,
        val place_name : String = "",
        val road_address_name : String = "",
        val x : String = "",
        val y : String = "",
        var time : String = "설정 안 함",
        var isChecked : Boolean = false
            )
}