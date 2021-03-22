package com.dev777popov.convertimgapp.mvp.view

import com.dev777popov.convertimgapp.mvp.model.Image
import io.reactivex.rxjava3.core.Single

interface IConverter {
    fun convert(image: Image): Single<Boolean>
}