package com.parse.starter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import com.parse.*

val ERROR_OCCURRED="Some error occurred"
val ENTER_USERNAME = "Please enter a correct username"
val ENTER_PASSWORD = "Please enter a correct password"
val SIGNUP_SUCCESS = "Successfully signed up"
val LOGIN_SUCCESS = "Successfully logged in"


/**
 * Created by Oleg on 07.09.2017.
 */
fun parseSignUp (username:String, password:String, context: Context) {
    Log.i("I", "PARse SIGN UP")
    if (!areUsernameAndPasswordCorrect(username,password,context)) {
        return
    }
    var parseUser = ParseUser()
    parseUser.setUsername(username)
    parseUser.setPassword(password)
    parseUser.signUpInBackground ({ e: ParseException? ->
        if (e==null) {
            Toast.makeText(context, SIGNUP_SUCCESS,Toast.LENGTH_SHORT).show()
            runUserListActivity(context)
        } else {
            Toast.makeText(context, ERROR_OCCURRED,Toast.LENGTH_SHORT).show()
        }
    })
}

fun parseLogIn (username: String, password: String, context: Context) {
    if (!areUsernameAndPasswordCorrect(username,password,context)) {
        return
    }
    ParseUser.logInInBackground(username,password, {user:ParseUser?, e:ParseException? ->
        if (user!=null&&e==null) {
            Toast.makeText(context, LOGIN_SUCCESS,Toast.LENGTH_SHORT).show()
            runUserListActivity(context)
        } else {
            Toast.makeText(context, ERROR_OCCURRED,Toast.LENGTH_SHORT).show()
        }
})
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

fun runUserListActivity(context: Context) {
    val intentUserList = Intent(context,UserListActivity::class.java)
    intentUserList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intentUserList)
}

fun updateUsers (mAdapter: ArrayAdapter<String>, lvUserList: ListView) {
    var query = ParseUser.getQuery()
    query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername())
    query.addAscendingOrder("username")
    query.findInBackground({userList:List<ParseUser>?, e:ParseException? ->
        if (userList!=null && e==null) {
            mAdapter.clear()
            for (parseUser in userList) {
                mAdapter.add(parseUser.getUsername())
            }
            lvUserList.setAdapter(mAdapter)
        }
    })
}

fun showPhotos(context: Context, username: String, linearLayoutImages: LinearLayout) {
        var query = ParseQuery.getQuery<ParseObject>("Image")
        query.whereEqualTo("username",username)
        query.findInBackground { imageList:List<ParseObject>?, e:ParseException? ->
            if (imageList!=null && e==null) {
                for (image in imageList) {
                    var imageFile = image.get("image") as ParseFile
                    imageFile.getDataInBackground { data:ByteArray?, e:ParseException? ->
                        if (data!=null && e==null) {
                            var bmp = BitmapFactory.decodeByteArray(data,0,data.size)
                            var imageView = ImageView(context)
                            imageView.setLayoutParams(ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            ))
                            imageView.setPadding(8,8,8,8)
                            imageView.setImageBitmap(bmp)
                            linearLayoutImages.addView(imageView)
                        }
                    }
                }
            }


        }
    }
