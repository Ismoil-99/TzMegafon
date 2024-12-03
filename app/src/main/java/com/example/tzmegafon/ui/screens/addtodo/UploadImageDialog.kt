package com.example.tzmegafon.ui.screens.addtodo

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import com.example.tzmegafon.R
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class UploadImageDialog(private val upload : (file: File, uri: Uri) -> Unit) : BottomSheetDialogFragment() {

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data


            if (resultCode == Activity.RESULT_OK) {
                val file = data?.data!!.toFile()
                val uri = data?.data!!
                Log.d("data","$data")

                if (data?.data != null){
                    upload.invoke(file,uri)
                    dismiss()
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Не выбранно!", Toast.LENGTH_SHORT).show()
            }
        }


    private val startForProfileImageResultGalery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data


            if (resultCode == Activity.RESULT_OK) {
                val file = data?.data!!.toFile()
                val uri = data?.data!!

                Log.d("data","$data")

                if (data?.data != null){
                    upload.invoke(file,uri)
                    dismiss()
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Не выбранно!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.upload_image_dialog, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RelativeLayout>(R.id.box1).setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .cropSquare()
                .compress(512)
                .maxResultSize(200, 200)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }


        view.findViewById<RelativeLayout>(R.id.box2).setOnClickListener {

            ImagePicker.with(this)
                .galleryOnly()
                .compress(512)
                .cropSquare()
                .maxResultSize(200,200)
                .createIntent { intent ->
                    startForProfileImageResultGalery.launch(intent)
                }
        }
    }

}