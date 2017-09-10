/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.parse.ParseAnalytics

import kotlinx.android.synthetic.main.activity_main.*
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.parse.ParseUser


class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnKeyListener {

    var isSignUp=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoginOrSignup.setOnClickListener(this)
        tvLoginOrSignup.setOnClickListener(this)
        background.setOnClickListener(this)

        etPassword.setOnKeyListener(this)

        etPassword.setTypeface(Typeface.DEFAULT)
        etPassword.setTransformationMethod(PasswordTransformationMethod())

        ParseAnalytics.trackAppOpenedInBackground(intent)

        if (ParseUser.getCurrentUser()!=null) {
            runUserListActivity(this)
        }
    }



    override fun onClick(view: View?) {
        when (view) {
            btnLoginOrSignup -> {
                    loginOrSignUpUser()
            }
            tvLoginOrSignup -> {
                if (isSignUp) {
                    btnLoginOrSignup.setText(getString(R.string.btn_login_login));
                    tvLoginOrSignup.setText(getString(R.string.tv_login_login))
                } else {
                    btnLoginOrSignup.setText(getString(R.string.btn_login_signup));
                    tvLoginOrSignup.setText(getString(R.string.tv_login_signup))
                }
                isSignUp=!isSignUp
            }
            background -> {
               var inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                       as InputMethodManager
               inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
            }

        } //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1==KeyEvent.KEYCODE_ENTER && p2!!.action==KeyEvent.ACTION_DOWN) {
           onClick(btnLoginOrSignup)
        }
        return false
    }


    private fun loginOrSignUpUser() {
        if (isSignUp) {
            parseSignUp(etUsername.text.toString(),
                    etPassword.text.toString(),
                    this)
        } else {
            parseLogIn(etUsername.text.toString(),
                    etPassword.text.toString(),
                    this)
        }


    }

}
