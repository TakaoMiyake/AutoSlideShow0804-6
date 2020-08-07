package com.example.autoslideshow0804

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.os.Build
import android.util.Log
import android.provider.MediaStore
import android.content.ContentUris
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.view.View
import android.widget.Toast

import android.os.Handler
import android.widget.Button
import java.lang.System.exit
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var mTimer: Timer? = null
    private var mTimerSec = 0.0
    private var mHandler = Handler()


    lateinit var imageUri: Uri
    private lateinit var grantResults: IntArray

    private val PERMISSIONS_REQUEST_CODE = 100

    private var error_Detective: Int = 0

    lateinit var cursor: Cursor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resolver = contentResolver




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("ANDROID", "許可されている")
               // getContentsInfo()
            } else {
                Log.d("ANDROID", "許可されていない")
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_CODE
                )
            }
        }

        cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )!!

        if (cursor!!.moveToFirst()){
            exit(1)
        }

        if (!cursor!!.moveToFirst()) {
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            imageUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageView.setImageURI(imageUri)
            Log.d("ANDROID", "In onCreate URI : " + imageUri.toString())
        }
        else{
            Toast.makeText(this, "写真データを保存してください", Toast.LENGTH_SHORT).show()

            Log.d("ANDROID","This is an illegal process!!!")
            exit(1)
        }


        go_button.setOnClickListener{
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            if (!cursor.moveToNext()) {
                cursor.moveToFirst()
            }
            else{
                cursor.moveToNext()
            }

            imageView.setImageURI(imageUri)
            Log.d("ANDROID", "In R.id.go_button URI : " + imageUri.toString())
        }


        back_button.setOnClickListener{
            val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val id = cursor.getLong(fieldIndex)
            if(!cursor.moveToPrevious()){
                cursor.moveToLast()
            }
            else{
                cursor.moveToPrevious()
            }

            imageView.setImageURI(imageUri)
            Log.d("ANDROID", "URI : " + imageUri.toString())

        }

      /*  playandstop_button.setOnClickListener{


        }*/

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d("ANDROID", PERMISSIONS_REQUEST_CODE.toString())

        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("ANDROID", "許可された")
                   // getContentsInfo()
                } else {
                    Log.d("ANDROID", "許可されなかった")
                    Toast.makeText(this, "許可してください", Toast.LENGTH_SHORT).show()

                }
        }
    }

 //   private fun getContentsInfo() {




  //  }


     fun onClick(v:View?) {

    }
    /*
       val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        val id = cursor.getLong(fieldIndex)


        imageUri =
            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

        imageView.setImageURI(imageUri)
        Log.d("ANDROID", "In onClick URI : " + imageUri.toString())

        if(v!= null){

            if(v.id == R.id.go_button) {

                //次の画像がなかったら、最初に飛ぶ
                if (!cursor.moveToNext()) {
                    cursor.moveToFirst()
                }
                else{
                    cursor.moveToNext()
                }

                imageView.setImageURI(imageUri)

                Log.d("ANDROID", "In R.id.go_button URI : " + imageUri.toString())

            }
            else if(v.id == R.id.back_button) {


                if(!cursor.moveToPrevious()){
                    cursor.moveToLast()
                }
                imageView.setImageURI(imageUri)
                Log.d("ANDROID", "URI : " + imageUri.toString())
            }
            else if(v.id == R.id.playandstop_button) {
                //I have no ideas to indicate around this area so far
            }
            else{
                error_Detective = 1
            }

            cursor.close()

        }

    }*/


}

