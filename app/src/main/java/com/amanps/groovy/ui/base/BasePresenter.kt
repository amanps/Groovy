package com.amanps.groovy.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V : BaseView> {

    protected var view: V? = null

    protected var compositeDisposable: CompositeDisposable? = null

    fun isViewAttached(): Boolean = view != null

    fun attachView(view: V) {
        this.view = view
        if (compositeDisposable == null)
            compositeDisposable = CompositeDisposable()
    }

    fun detachView(){
        this.view = null
        compositeDisposable?.dispose()
        compositeDisposable = null
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(MvpView) before" +
                    " accessing the Presenter")

}
