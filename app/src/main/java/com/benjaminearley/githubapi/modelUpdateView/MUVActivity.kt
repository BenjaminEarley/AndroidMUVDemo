package com.benjaminearley.githubapi.modelUpdateView


import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.benjaminearley.githubapi.dagger.GitHubModule.GitHubApiInterface
import com.benjaminearley.githubapi.MyApp
import com.benjaminearley.githubapi.R
import com.benjaminearley.githubapi.data.User
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MUVActivity : AppCompatActivity() {

    @Inject
    lateinit var gitHubApiInterface: GitHubApiInterface
    lateinit var model: Model
    lateinit var disposable: Disposable
    lateinit var response: TextView
    lateinit var userName: EditText
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //// View
        setContentView(

                relativeLayout {

                    response = textView().lparams {
                        margin = dip(32)
                        alignParentTop()
                        centerHorizontally()
                    }

                    verticalLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        userName = editText {
                            maxLines = 1
                            inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

                            textChangedListener {
                                onTextChanged { text, _, _, _ ->
                                    model.viewModel.userName = text.toString()
                                }
                            }

                        }.lparams(width = matchParent) { margin = dip(8) }

                        linearLayout {
                            button {
                                text = getString(R.string.send)

                                onClick {
                                    if (model.networkModel != null) return@onClick
                                    model.networkModel = gitHubApiInterface
                                            .getUser(model.viewModel.userName ?: "")
                                            .subscribeOn(Schedulers.io())
                                            .delay(3, TimeUnit.SECONDS) // Here to emulate a long running process
                                            //.flatMap { Single.error<User>(Throwable()) } //emulate error
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnSubscribe {
                                                model.viewModel.isProgressBarVisible = true
                                                model.viewUpdates.accept(Unit)
                                            }
                                            .subscribe({ user ->
                                                model.viewModel.userResult = UserResultOfSome(user)
                                                model.viewModel.isProgressBarVisible = false

                                                model.viewUpdates.accept(Unit)

                                                model.networkModel?.dispose()
                                                model.networkModel = null
                                            }, { error ->
                                                model.viewModel.userResult = UserResultOfError(error)
                                                model.viewModel.isProgressBarVisible = false

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
                                    model.viewModel.userResult = UserResultOfNone()
                                    model.viewModel.isProgressBarVisible = false
                                    userName.setText("")

                                    model.viewUpdates.accept(Unit)
                                }
                            }.lparams { margin = dip(8) }
                        }.lparams { margin = dip(8) }
                    }.lparams(width = dip(280)) { centerInParent() }

                    progressBar = progressBar {
                        visibility = View.GONE
                    }.lparams {
                        alignParentRight()
                        alignParentBottom()
                        margin = dip(8)
                    }
                })

        MyApp.gitHubComponent.inject(this)

        //// Update

        lastCustomNonConfigurationInstance?.let {
            model = it as Model
        } ?: {
            model = Model(BehaviorRelay.createDefault(Unit), ViewModel(UserResultOfNone(), false, "BenjaminEarley", 0), null)
        }()

        userName.setText(model.viewModel.userName)
        userName.setSelection(model.viewModel.cursorPosition)

        disposable = model.viewUpdates.subscribe {
            response.text = model.viewModel.userResult.with(
                    { null },
                    User::toString,
                    { null })

            model.viewModel.userResult.with(
                    { },
                    { },
                    {
                        AlertDialog
                                .Builder(this)
                                .setMessage(R.string.error)
                                .setPositiveButton(R.string.ok, null)
                                .setOnDismissListener { model.viewModel.userResult = UserResultOfNone() }
                                .show()
                        Unit })

            progressBar.visibility = if (model.viewModel.isProgressBarVisible) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        disposable.dispose()

        if (isFinishing) {
            model.networkModel?.dispose()
        } else {
            model.viewModel.cursorPosition = userName.selectionStart
        }

        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return model
    }
}