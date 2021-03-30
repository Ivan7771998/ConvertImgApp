package com.dev777popov.convertimgapp.mvp.presenter

import com.dev777popov.convertimgapp.mvp.model.Image
import com.dev777popov.convertimgapp.mvp.view.IConverter
import com.dev777popov.convertimgapp.mvp.view.MainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class MainPresenter(
    private val scheduler: Scheduler,
    private val converter: IConverter
) : MvpPresenter<MainView>() {

    fun convertClick() {
        viewState.selectImage()
    }

    var conversionDisposable: Disposable? = null

    fun imageSelected(image: Image) {
        viewState.showProgressInConvert()
        conversionDisposable = converter.convert(image)
            .observeOn(scheduler)
            .subscribe({
                viewState.hideProgressInConvert()
                viewState.showSuccessConvert()
            }, {
                viewState.hideProgressInConvert()
                viewState.showErrorConvert()
            })
    }

    fun convertCancel() {
        conversionDisposable?.dispose()
        viewState.hideProgressInConvert()
        viewState.showCancelConvert()
    }
}