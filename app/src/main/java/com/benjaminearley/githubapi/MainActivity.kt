package com.benjaminearley.githubapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.benjaminearley.githubapi.GitHubModule.GitHubApiInterface
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubApiInterface: GitHubApiInterface
    lateinit var model: Model
    lateinit var disposable: Disposable
    lateinit var message: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //// View

        setContentView(

                relativeLayout {

                    message = textView().lparams {
                        margin = dip(32)
                        alignParentTop()
                        centerHorizontally()
                    }

                    linearLayout {
                        button {
                            width = wrapContent
                            height = wrapContent
                            text = getString(R.string.send)

                            onClick {
                                if (model.networkModel != null) return@onClick
                                model.networkModel = gitHubApiInterface
                                        .getUser("BenjaminEarley")
                                        .subscribeOn(Schedulers.io())
                                        .delay(3, TimeUnit.SECONDS) // Here to emulate a long running process
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe {
                                            model.viewModel.progressVisible = true
                                            model.viewUpdates.accept(Unit)
                                        }
                                        .subscribe({ user ->
                                            model.viewModel.user = Some(user)
                                            model.viewModel.progressVisible = false

                                            model.viewUpdates.accept(Unit)

                                            model.networkModel?.dispose()
                                            model.networkModel = null
                                        }, { error ->
                                            model.viewModel.user = None()
                                            model.viewModel.progressVisible = false

                                            model.viewUpdates.accept(Unit)

                                            model.networkModel?.dispose()
                                            model.networkModel = null
                                        })
                            }
                        }.lparams { margin = dip(8) }

                        button {
                            text = getString(R.string.clear)

                            onClick {
                                model.networkModel?.let {
                                    it.dispose()
                                    model.networkModel = null
                                }
                                model.viewModel.user = None()
                                model.viewModel.progressVisible = false

                                model.viewUpdates.accept(Unit)
                            }
                        }.lparams { margin = dip(8) }

                    }.lparams { centerInParent() }

                    progressBar = progressBar {
                        visibility = View.GONE
                    }.lparams {
                        alignParentEnd()
                        alignParentBottom()
                        margin = dip(8)
                    }
                })

        MyApp.gitHubComponent.inject(this)

        //// Update

        lastCustomNonConfigurationInstance?.let {
            model = it as Model
        } ?: {
            model = Model(BehaviorRelay.createDefault(Unit), ViewModel(None(), false), null)
        }()

        disposable = model.viewUpdates.subscribe {
            message.text = model.viewModel.user.with(
                    User::toString,
                    { null })

            progressBar.visibility = if (model.viewModel.progressVisible) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        disposable.dispose()

        if (isFinishing) {
            model.networkModel?.dispose()
        }

        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return model
    }
}
