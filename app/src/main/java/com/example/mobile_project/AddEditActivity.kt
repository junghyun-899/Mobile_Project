package com.example.mobile_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.databinding.ActivityAddEditBinding
import com.example.mobile_project.db.DBHelper
import com.example.mobile_project.model.TravelRecord
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private var selectedImageUri: Uri? = null
    private val imagePicker =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ){ uri ->

            if(uri != null){

                selectedImageUri = uri

                binding.imgPreview
                    .setImageURI(uri)
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        binding =
            ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnImage.setOnClickListener {

            imagePicker.launch("image/*")
        }

        binding.btnSave.setOnClickListener {

            val record =
                TravelRecord(

                    place =
                        binding.etPlace.text.toString(),

                    date =
                        binding.etDate.text.toString(),

                    memo =
                        binding.etMemo.text.toString(),

                    imagePath = selectedImageUri?.toString() ?: ""
                )

            DBHelper(this)
                .insert(record)

            finish()
        }
    }
}