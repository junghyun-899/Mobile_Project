package com.example.mobile_project
import androidx.exifinterface.media.ExifInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_project.databinding.ActivityAddEditBinding
import com.example.mobile_project.db.DBHelper
import com.example.mobile_project.model.TravelRecord
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import android.app.DatePickerDialog
import java.util.Calendar
import android.content.Intent
import android.widget.Toast

class AddEditActivity : AppCompatActivity() {
    private var editId = -1
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var binding: ActivityAddEditBinding
    private var selectedImageUri: Uri? = null
    private val imagePicker = registerForActivityResult(ActivityResultContracts.OpenDocument())
    {
        uri ->
        if (uri != null)
        {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            selectedImageUri = uri
            binding.imgPreview.setImageURI(uri)
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val exif = ExifInterface(inputStream!!)
                val latLong = FloatArray(2)
                if(exif.getLatLong(latLong))
                {
                    latitude = latLong[0].toDouble()
                    longitude = latLong[1].toDouble()
                }
            } catch(e: Exception)
            {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnImage.setOnClickListener {
            imagePicker.launch(arrayOf("image/*"))
        }
        binding.btnSave.setOnClickListener {
            Toast.makeText(
                this,
                "위도 : $latitude\n경도 : $longitude",
                Toast.LENGTH_LONG
            ).show()
            val record = TravelRecord(
                place = binding.etPlace.text.toString(),
                date = binding.etDate.text.toString(),
                memo = binding.etMemo.text.toString(),
                imagePath = selectedImageUri?.toString() ?: "",
                latitude = latitude,
                longitude = longitude,
                rating = binding.ratingBar.rating
            )
            val db = DBHelper(this)
            if(editId == -1){
                db.insert(record)
            }
            else{
                record.id = editId
                db.update(record)
            }
            finish()
        }
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay -> binding.etDate.setText("$selectedYear-${selectedMonth + 1}-$selectedDay") }, year, month, day
            ).show()
        }
        editId = intent.getIntExtra("recordId", -1)
        if (editId != -1) {
            val record = DBHelper(this).getById(editId)
            if (record != null) {
                binding.etPlace.setText(record.place)
                binding.etDate.setText(record.date)
                binding.etMemo.setText(record.memo)
                binding.ratingBar.rating = record.rating
                if(record.imagePath.isNotEmpty())
                {
                    selectedImageUri = Uri.parse(record.imagePath)
                    binding.imgPreview.setImageURI(selectedImageUri)
                }
            }
        }
    }
}