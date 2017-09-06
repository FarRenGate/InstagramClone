/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.parse.ParseAnalytics

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var isSignUp=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoginOrSignup.setOnClickListener(this)
        tvLoginOrSignup.setOnClickListener(this)

        ParseAnalytics.trackAppOpenedInBackground(intent)
    }



    override fun onClick(view: View?) {
        when (view) {
            btnLoginOrSignup -> {
                Toast.makeText(this,"ButtonClicked", Toast.LENGTH_SHORT).show()
                if (isSignUp) {
                    SignUpUser()
                } else {
                    LogInUser()
                }

            }
            tvLoginOrSignup -> {
                Toast.makeText(this,"Text", Toast.LENGTH_SHORT).show()
                if (isSignUp) {
                    btnLoginOrSignup.setText(getString(R.string.btn_login_login));
                    tvLoginOrSignup.setText(getString(R.string.tv_login_login))
                } else {
                    btnLoginOrSignup.setText(getString(R.string.btn_login_signup));
                    tvLoginOrSignup.setText(getString(R.string.tv_login_signup))
                }
                isSignUp=!isSignUp
            }

        } //To change body of created functions use File | Settings | File Templates.
    }

    private fun LogInUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun SignUpUser() {

    }

}
