package com.c7z.mappilogue_aos.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import java.io.File

object BitmapHelper {
    fun String.convertWhenSingle(context : Context) : String {
        return this.resizeImage(context)
    }

    private fun String.resizeImage(context : Context) : String {
        return File(Bitmap.createScaledBitmap(this.convertUriToBitmap(context), 1024, 1024, true).convertToUri(context).toString()).path
    }

    private fun String.convertUriToBitmap(context: Context) : Bitmap {
        return if(Build.VERSION.SDK_INT >= 28) ImageDecoder.decodeBitmap(ImageDecoder.createSource(File(this)))
        else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this.toUri())
        }
    }

    private fun Bitmap.convertToUri(context : Context) : Uri {
        return Uri.parse(MediaStore.Images.Media.insertImage(context.contentResolver, this, this.toString(), null))
    }

    fun Uri.deletePic(context: Context) {
        context.contentResolver.delete(this, null, null)
    }
}