package com.dev777popov.convertimgapp.ui.converter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dev777popov.convertimgapp.mvp.model.Image
import com.dev777popov.convertimgapp.mvp.view.IConverter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream


class ImageConverter(private val context: Context?) : IConverter {
    override fun convert(image: Image): Single<Boolean> = Single.fromCallable() {
        context?.let {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                return@let
            }
        }

        val bitmap = BitmapFactory.decodeByteArray(image.data, 0, image.data.size)

        val dstFile = File(context?.getExternalFilesDir(null), "convertImg.jpeg")
        val stream = FileOutputStream(dstFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    }.subscribeOn(Schedulers.computation())

}