package com.parse.starter

import android.content.Context
import android.widget.Toast
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser

val ERROR_OCCURRED="Some error occurred"
val ENTER_USERNAME = "Please enter a correct username"
val ENTER_PASSWORD = "Please enter a correct password"
val SIGNUP_SUCCESS = "Successfully signed up"
val LOGIN_SUCCESS = "Successfully logged in"


/**
 * Created by Oleg on 07.09.2017.
 */
fun parseSignUp (username:String, password:String, context: Context) {
    if (!areUsernameAndPasswordCorrect(username,password,context)) {
        return
    }
    var parseUser = ParseUser()
    parseUser.setUsername(username)
    parseUser.setPassword(password)
    parseUser.signUpInBackground ({ e: ParseException? ->
        if (e==null) {
            Toast.makeText(context, SIGNUP_SUCCESS,Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, ERROR_OCCURRED,Toast.LENGTH_SHORT).show()
        }
    })
}

fun parseLogIn (username: String, password: String, context: Context) {
    if (!areUsernameAndPasswordCorrect(username,password,context)) {
        return
    }
    ParseUser.logInInBackground(username,password, object:LogInCallback{
        override fun done(user: ParseUser?, e: ParseException?) {
            if (user!=null&&e==null) {
                Toast.makeText(context, LOGIN_SUCCESS,Toast.LENGTH_SHORT).show()//To change body of created functions use File | Settings | File Templates.
            } else {
                Toast.makeText(context, ERROR_OCCURRED,Toast.LENGTH_SHORT).show()
            }
        }

    }
    )
}

fun areUsernameAndPasswordCorrect (username: String, password: String, context: Context) :Boolean{
    if (username.isBlank()||username.contains(" ")) {
        Toast.makeText(context, ENTER_USERNAME,Toast.LENGTH_SHORT).show()
        return false
    }
    if (password.isBlank()||password.contains(" ")) {
        Toast.makeText(context, ENTER_PASSWORD,Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}