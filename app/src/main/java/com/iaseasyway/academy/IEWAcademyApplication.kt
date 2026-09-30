package com.iaseasyway.academy

import android.app.Application
import com.iaseasyway.academy.data.repository.AcademyRepository
import com.iaseasyway.academy.data.security.SecurityManager

class IEWAcademyApplication : Application() {

    companion object {
        lateinit var instance: IEWAcademyApplication
            private set
    }

    val repository: AcademyRepository by lazy {
        AcademyRepository()
    }

    var isTamperedEnvironment: Boolean = false
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        isTamperedEnvironment = SecurityManager.isDeviceTampered()
    }
}
