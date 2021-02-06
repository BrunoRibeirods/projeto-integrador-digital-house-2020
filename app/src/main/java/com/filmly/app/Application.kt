package com.filmly.app

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        initTwitter()
    }

    private fun initTwitter() {
        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(getString(R.string.twitter_api_key), getString(R.string.twitter_api_secret)))
            .debug(true)
            .build()
        Twitter.initialize(config)

    }

}