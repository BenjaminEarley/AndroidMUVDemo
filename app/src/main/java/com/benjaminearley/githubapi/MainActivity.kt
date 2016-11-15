package com.benjaminearley.githubapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.benjaminearley.githubapi.GitHubModule.GitHubApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubApiInterface: GitHubApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApp.gitHubComponent.inject(this)

        button.setOnClickListener {
            gitHubApiInterface
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
