package com.benjaminearley.githubapi

import android.os.Bundle
import android.widget.Toast
import com.benjaminearley.githubapi.GitHubModule.GitHubApiInterface
import com.jakewharton.rxrelay2.BehaviorRelay
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : RxActivity() {

    @Inject
    lateinit var gitHubApiInterface: GitHubApiInterface
    lateinit var behaviorRelay: BehaviorRelay<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApp.gitHubComponent.inject(this)

        lastCustomNonConfigurationInstance?.let {
            @Suppress("UNCHECKED_CAST")
            behaviorRelay = it as BehaviorRelay<User>
        } ?: {
            behaviorRelay = BehaviorRelay.create()
        }()

        behaviorRelay.subscribe({ user ->
            message.text = user.toString()
        })

        sendButton.setOnClickListener {
            gitHubApiInterface
                    .getUser("BenjaminEarley")
                    .subscribeOn(Schedulers.io())
                    .delay(3, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
//                    .bindToLifecycle(this) Can we do this?
                    .subscribe({ user ->
                        behaviorRelay.accept(user)
                    }, { error ->
                        Toast.makeText(this,
                                if (error is CancellationException) "Rotation Occurred"
                                else "Error",
                                Toast.LENGTH_LONG).show()
                    })
        }

        clearButton.setOnClickListener {
            message.text = null
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        super.onRetainCustomNonConfigurationInstance()
        return behaviorRelay
    }
}
