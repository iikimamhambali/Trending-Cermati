package com.android.trendingcermati.ui.splash

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import com.android.trendingcermati.R
import com.android.trendingcermati.base.BaseActivity
import com.android.trendingcermati.ui.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : BaseActivity() {

    companion object {
        private const val DELAY_SPLASH_SCREEN: Long = 3000
    }

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Handler().postDelayed({
            startActivity<HomeActivity>()
            this.finish()
        }, DELAY_SPLASH_SCREEN)
    }

    override fun onResume() {
        super.onResume()
        setupVersion()
    }

    private fun setupVersion() {
        tvVersion.text = loadVersionNumber()
    }

    private fun loadVersionNumber(): String =
        try {
            val info = packageManager.getPackageInfo(packageName, 0)
            "V." + info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }

}
