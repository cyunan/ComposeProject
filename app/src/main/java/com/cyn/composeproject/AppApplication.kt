package com.cyn.composeproject

import android.annotation.SuppressLint
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AppApplication : Application() {
        companion object {
        @SuppressLint("StaticFieldLeak")
        private var instances: AppApplication? = null

        fun getInstance(): AppApplication {
            if (instances == null) {
                synchronized(AppApplication::class.java) {
                    if (instances == null) {
                        instances = AppApplication()
                    }
                }
            }
            return instances!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        instances = this
        initData()
    }

    private fun initData() {
        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            initQbSdk()
            initBugLy()
            initOtherComponent()
        }
    }

    private fun initOtherComponent() {
        //初始化其他的 推送、图片或者三方的网络框架库

    }

    private fun initBugLy() {
        // Bugly bug上报

    }

    private fun initQbSdk() {
        // x5内核初始化接口
    }
}