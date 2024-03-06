package com.c7z.mappilogue_aos.presentation.ui.gallery.single_select.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c7z.mappilogue_aos.data.data.GalleryData
import java.io.File

private const val INDEX_MEDIA_ID = MediaStore.MediaColumns._ID
private const val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
private const val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
const val INDEX_DATE_ADDED = MediaStore.MediaColumns.DATE_ADDED

class CustomGalleryViewModel : ViewModel() {

    private val _imageItemList = MutableLiveData<MutableList<GalleryData>>().apply { value = mutableListOf() }
    val imageItemList : LiveData<MutableList<GalleryData>> = _imageItemList

    private val _legacyPosition = MutableLiveData<Int>()
    val legacyPosition : LiveData<Int> = _legacyPosition

    private val _checkedPosition = MutableLiveData<Int>()
    val checkedPosition : LiveData<Int> = _checkedPosition

    private val _checkedSize = MutableLiveData<Int>(0)
    val checkedSize : LiveData<Int> = _checkedSize

    @SuppressLint("Range")
    fun fetchImageItemList(context: Context) {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            INDEX_MEDIA_ID,
            INDEX_MEDIA_URI,
            INDEX_ALBUM_NAME,
            INDEX_DATE_ADDED
        )
        val selection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) MediaStore.Images.Media.SIZE + " > 0"
            else null
        val sortOrder = "$INDEX_DATE_ADDED DESC"
        val cursor = context.contentResolver.query(uri, projection, selection, null, sortOrder)

        cursor?.let {
            while(cursor.moveToNext()) {
                val mediaPath = cursor.getString(cursor.getColumnIndex(INDEX_MEDIA_URI))
                _imageItemList.value!!.add(GalleryData(Uri.fromFile(File(mediaPath)), false))
            }
        }
        cursor?.close()
    }

    fun getCheckedImageUriList(): String {
        return imageItemList.value!![checkedPosition.value!!].uri.toString().also {
            Log.e(
                "----",
                "getCheckedImageUriList: $it",

            ) }
    }

    fun changeImageClicked(position : Int) {
        _legacyPosition.value = _checkedPosition.value
        _checkedPosition.value = position
    }

}