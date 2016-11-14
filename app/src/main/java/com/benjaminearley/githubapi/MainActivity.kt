package com.benjaminearley.githubapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val githubService = MyApp
                .netComponent
                .retrofit()
                .create(GitHubService::class.java)

        button.setOnClickListener {
            githubService
                    .getUser("BenjaminEarley")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        message.text = user.toString()
                    }, { error ->
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    })
        }



    }
}
