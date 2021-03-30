package com.dev777popov.convertimgapp.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface MainView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun selectImage()

    fun showProgressInConvert()
    fun hideProgressInConvert()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSuccessConvert()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCancelConvert()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorConvert()

}