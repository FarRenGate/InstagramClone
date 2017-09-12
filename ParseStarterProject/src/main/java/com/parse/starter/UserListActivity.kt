package com.parse.starter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_user_list.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class UserListActivity : AppCompatActivity() {

    private val INTENT_GET_PHOTO_REQUEST_CODE = 1
    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.instagram_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.menuLogout -> {
                ParseUser.logOut()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        var mAdapter = ArrayAdapter<String>(this, R.layout.list_adapter_item)
        updateUsers(mAdapter, lvUserList)
        lvUserList.setOnItemClickListener { adapterView, view, i, l ->
            var username = adapterView.getItemAtPosition(i) as String
            Log.i("Item click", username)
            intent = Intent(this, ImageViewActivity::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?,
                                            grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults!!.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_GET_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK &&
                data != null) {
            var resultedImageUri = data.getData()

            try {
                var resultedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        resultedImageUri)
                Log.i("Photo_handling", "Received")

                var stream = ByteArrayOutputStream()
                resultedImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                var byteArray = stream.toByteArray()

                var parseFile = ParseFile("image.png", byteArray)
                var parseObject = ParseObject("Image")
                parseObject.put("image", parseFile)
                parseObject.put("username", ParseUser.getCurrentUser().getUsername())

                parseObject.saveInBackground { e: ParseException? ->
                    if (e == null) {
                        Toast.makeText(this, "Image Shared", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error in sharing the image", Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

    fun takePhoto(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE)
            } else {
                getPhoto()
            }
        } else {
            getPhoto()
        }

    }

    fun getPhoto() {
        intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, INTENT_GET_PHOTO_REQUEST_CODE)
    }
}
