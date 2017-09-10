package com.parse.starter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        var mAdapter = ArrayAdapter<String>(this,R.layout.list_adapter_item)
        lvUserList.setAdapter(mAdapter)
        updateUsers(mAdapter,lvUserList)
    }

    fun logOutClick (v: View) {
        ParseUser.logOut()
        startActivity(Intent(this,MainActivity::class.java))
    }
}
