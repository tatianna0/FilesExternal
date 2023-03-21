package com.example.filesexternal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.*

class MainActivity : AppCompatActivity() {

    lateinit var editText1: EditText
    private val READ_BLOCK_SIZE = 100

    private val REQUEST_CODE = 1
    var storge_permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText1 = findViewById(R.id.editText1)

        ActivityCompat.requestPermissions(
            this, storge_permissions, REQUEST_CODE)
    }


    fun onClickSave(v: View?) {
        val str = editText1.text.toString()
        try {
            //--- SD Card Storage ---
            val sdCard = getExternalFilesDir(null)!!.absolutePath
            val directory = File("$sdCard/FilesExternal")
            directory.mkdirs()
            val file = File(directory, "textfile.txt")
            val fOut = FileOutputStream(file)
            val osw = OutputStreamWriter(fOut)

            // --- Write the string to the file ---
            osw.write(str)
            osw.flush()
            osw.close()

            // --- Display file saved message ---
            Toast.makeText(
                baseContext, "File saved successfully!",
                Toast.LENGTH_LONG
            ).show()

            //--- Clears the EditText ---
            editText1.setText("")
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }


    fun onClickLoad(v: View?) {
        try {

            //--- SD Storage ---
            val sdCard = getExternalFilesDir(null)!!.absolutePath
            val directory = File("$sdCard/FilesExternal")
            val file = File(directory, "textfile.txt")
            val fIn = FileInputStream(file)
            val isr = InputStreamReader(fIn)
            var inputBuffer =
                CharArray(READ_BLOCK_SIZE)
            var s: String? = ""
            var charRead: Int
            while (isr.read(inputBuffer).also { charRead = it } > 0) {
                //--- Convert the chars to a String ---
                val readString = String(inputBuffer, 0, charRead)
                s += readString
                inputBuffer =
                    CharArray(READ_BLOCK_SIZE)
            }
            //--- Set the EditText to the text that has been read ---
            editText1.setText(s)
            Toast.makeText(
                baseContext, "File loaded successfully!",
                Toast.LENGTH_LONG
            ).show()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }
}