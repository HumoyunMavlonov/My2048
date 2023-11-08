package uz.gita.my2048gamenumerick.app

import android.app.Application
import uz.gita.my2048gamenumerick.pref.MyPref


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MyPref.init(this)

    }

}