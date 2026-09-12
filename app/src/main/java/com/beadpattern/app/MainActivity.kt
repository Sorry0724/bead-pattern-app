package com.beadpattern.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnSelectImage: Button
    private lateinit var etWidth: EditText
    private lateinit var etHeight: EditText
    private lateinit var seekBarColors: SeekBar
    private lateinit var tvColors: TextView
    private lateinit var btnGenerate: Button

    private var imageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                imageUri = uri
                btnSelectImage.text = "已选择图片 ✓"
                btnGenerate.isEnabled = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnSelectImage = findViewById(R.id.btnSelectImage)
        etWidth = findViewById(R.id.etWidth)
        etHeight = findViewById(R.id.etHeight)
        seekBarColors = findViewById(R.id.seekBarColors)
        tvColors = findViewById(R.id.tvColors)
        btnGenerate = findViewById(R.id.btnGenerate)

        btnGenerate.isEnabled = false
        etWidth.setText("52")
        etHeight.setText("52")
    }

    private fun setupListeners() {
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageLauncher.launch(intent)
        }

        seekBarColors.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvColors.text = "颜色数量: ${progress + 10} 色"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnGenerate.setOnClickListener {
            val uri = imageUri ?: run {
                Toast.makeText(this, "请先选择图片", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val width = etWidth.text.toString().toIntOrNull() ?: 52
            val height = etHeight.text.toString().toIntOrNull() ?: 52

            val intent = Intent(this, PatternEditorActivity::class.java).apply {
                putExtra("image_uri", uri.toString())
                putExtra("width", width)
                putExtra("height", height)
            }
            startActivity(intent)
        }
    }
}
