package com.benjaminearley.githubapi

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.benjaminearley.githubapi.GitHubModule.GitHubApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubApiInterface: GitHubApiInterface
    lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApp.gitHubComponent.inject(this)

        lastCustomNonConfigurationInstance?.let {
            model = it as Model
        } ?: {
            val context = this.applicationContext
            model = Model(ViewModel(relativeLayout {

                val response = textView().lparams {
                    margin = dip(32)
                    alignParentTop()
                    centerHorizontally()
                }

                val progressBar = progressBar {
                    visibility = View.GONE
                }.lparams {
                    alignParentEnd()
                    alignParentBottom()
                    margin = dip(8)
                }

                verticalLayout {
                    gravity = Gravity.CENTER_HORIZONTAL
                    val userName = editText {
                        maxLines = 1
                        inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
                    }.lparams(width = matchParent) { margin = dip(8) }

                    linearLayout {
                        button {
                            text = getString(R.string.send)

                            onClick {
                                if (model.networkModel != null) return@onClick
                                model.networkModel = gitHubApiInterface
                                        .getUser(userName.text.toString())
                                        .subscribeOn(Schedulers.io())
                                        .delay(3, TimeUnit.SECONDS) // Here to emulate a long running process
                                        //.flatMap { Single.error<User>(Throwable()) } //emulate error
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe {
                                            progressBar.visibility = View.VISIBLE
                                        }
                                        .subscribe({ user ->
                                            progressBar.visibility = View.GONE
                                            response.text = user.toString()

                                            model.networkModel?.dispose()
                                            model.networkModel = null
                                        }, { error ->
                                            progressBar.visibility = View.GONE

                                            AlertDialog
                                                    .Builder(context)
                                                    .setMessage(error.toString())
                                                    .setPositiveButton(R.string.ok, null)
                                                    .show()

                                            model.networkModel?.dispose()
                                            model.networkModel = null
                                        })
                            }
                        }.lparams { margin = dip(8) }

                        button {
                            text = getString(R.string.clear)

                            onClick {
                                userName.setText("")
                                model.networkModel?.let {
                                    it.dispose()
                                    model.networkModel = null
                                }
                            }
                        }.lparams { margin = dip(8) }
                    }.lparams { margin = dip(8) }
                }.lparams(width = dip(280)) { centerInParent() }

            }), null)
        }()

        setContentView(model.viewModel.view)
    }

    override fun onDestroy() {

        if (isFinishing) {
            model.networkModel?.dispose()
        } else {
            (model.viewModel.view.parent as ViewGroup).removeView(model.viewModel.view)
        }

        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return model
    }
}
