package com.c7z.mappilogue_aos.data.data

data class TodoAreaData(
    val area : MutableList<AreaWithDate>
) {
    data class AreaWithDate(
        val date : String,
        val value : MutableList<AreaData>
    )

    data class AreaData(
        val name : String,
        val streetAddress : String?,
        val latitude : String?,
        val longitude : String?,
        val time : String?
    )
}
