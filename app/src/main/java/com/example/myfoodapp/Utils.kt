package com.example.myfoodapp

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myfoodapp.databinding.DialogLoadingBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {

    private var dialog : AlertDialog? = null

    fun showDialog(context: Context, message: String){
        val progress = DialogLoadingBinding.inflate(LayoutInflater.from(context))
        progress.tvMessage.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }

    fun showToast(context: Context, message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    private var firebaseAuth: FirebaseAuth? = null
    fun getAuthInstance(): FirebaseAuth {
        if (firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance()
        }
        return firebaseAuth!!
    }
}