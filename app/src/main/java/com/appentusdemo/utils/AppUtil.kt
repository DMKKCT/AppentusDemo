package com.wheelr.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.appentusdemo.R
//import kotlinx.android.synthetic.main.inflate_popup_message_layout.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.jvm.Throws

class AppUtil {
    private val TAG: String = AppUtil::class.java.getSimpleName()

    companion object {
        fun roundOffTo2DecPlaces(value: Float): String {
            return String.format("%.2f", value);
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun getJsonResult(context: Context, response: String?): String {
            var result: String? = null
            try {
                //  byte[] decodedBytes = Base64Convert.decode(response);
                val decodedBytes = Base64.decode(response, Base64.DEFAULT)
                result = String(decodedBytes)
            } catch (e: Exception) {
                result = "{}"
                //result="{\"status\":\"error\",\"message\":\"Error to convert STRING response to JSON data.\"}";
                e.printStackTrace()
            }

            return result!!
        }

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        public fun checkBlank(text: String): String {
            if (text.equals("null") || text.equals("")) return "N/A"
            else return text
        }

        public fun checkNull(text: String): String {
            if (text.equals("null") || text.equals("")) return ""
            else return text
        }

        fun errorAlert(context: Context, msg: String) {
            val dialog = Dialog(context, R.style.dialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.dialog_error)
            val tv_yes: TextView = dialog.findViewById(R.id.tv_yes)
            val tv_no: TextView = dialog.findViewById(R.id.tv_no)
            val tv_message: TextView = dialog.findViewById(R.id.tv_message)
            tv_no.visibility = View.GONE
            tv_yes.text = context.getString(R.string.ok)
            tv_message.text = msg

            tv_yes.setOnClickListener {
                dialog.dismiss()
                //onBackPressed()
            }
            try {
                dialog.show()
            } catch (e: Exception) {
            }
        }


        fun showErrorDialog(context: Context, message: String) {
            val dialog = Dialog(context, R.style.dialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setContentView(R.layout.dialog_error)


            val tv_yes: TextView = dialog.findViewById(R.id.tv_yes)
            val tv_no: TextView = dialog.findViewById(R.id.tv_no)

            val tv_title: TextView = dialog.findViewById(R.id.tv_title)
            val tv_message: TextView = dialog.findViewById(R.id.tv_message)
            tv_title.text = context.getString(R.string.app_name)
            tv_message.text = message
            tv_yes.text = context.getString(R.string.ok)
            tv_no.visibility = View.GONE

            tv_yes.setOnClickListener { v ->
                dialog.dismiss()
            }
            tv_no.setOnClickListener { v -> dialog.dismiss() }
            dialog.show()
        }
    }
}