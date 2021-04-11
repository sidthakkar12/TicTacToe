package com.app.tictactoe.ui.core

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseVMBindingActivity<T : ViewDataBinding, VM : BaseViewModel>(private var viewModelClass: Class<VM>) :
    BaseBindingActivity<T>() {

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(viewModelClass)

        viewModel.loadingLiveData.observe(this, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        viewModel.networkLiveData.observe(this, Observer { isConnected ->
            if (!isConnected) {
                onNetworkDisconnected()
            }
        })

        viewModel.errorLiveData.observe(this, Observer {
            showToast(it)
        })
    }
}