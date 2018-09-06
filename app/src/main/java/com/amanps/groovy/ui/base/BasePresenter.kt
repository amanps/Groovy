package com.amanps.groovy.ui.base

open class BasePresenter<V : BaseView> {

    protected var view: V? = null

    fun isViewAttached(): Boolean = view != null

    fun attachView(view: V) {
        this.view = view
    }

    fun detachView(){
        this.view = null
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(MvpView) before" +
                    " accessing the Presenter")

}
