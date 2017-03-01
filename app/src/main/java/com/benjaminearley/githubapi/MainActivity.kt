package com.benjaminearley.githubapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.benjaminearley.githubapi.modelUpdateView.MUVActivity
import com.benjaminearley.githubapi.modelViewOnly.ViewOnlyActiity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        muv?.onClick {
            startActivity<MUVActivity>()
        }

        viewOnly?.onClick {
            startActivity<ViewOnlyActiity>()
        }
    }
}
