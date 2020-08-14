package com.example.frombefore.manager

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import com.example.frombefore.R

class ProgressDialogManager() {
    companion object {
        var progressDialog: AppCompatDialog? = null
        public fun progressOn(activity: Activity) {
            if (progressDialog == null) {
                progressDialog = AppCompatDialog(activity)
                progressDialog!!.setCancelable(false)
                progressDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                progressDialog!!.setContentView(R.layout.progress_dialog)
                progressDialog!!.show()

                val frameView = progressDialog!!.findViewById<ImageView>(R.id.frame_img)
                val frameAnimation = frameView?.background as AnimationDrawable
                frameView.post(Runnable {
                    frameAnimation.start()
                })
            }
        }

        public fun progressOff() {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss();
            }
        }
    }

}