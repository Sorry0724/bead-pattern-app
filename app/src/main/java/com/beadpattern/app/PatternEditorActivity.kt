package com.beadpattern.app

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PatternEditorActivity : AppCompatActivity() {

    private lateinit var beadView: BeadPatternView
    private lateinit var tvStats: TextView
    private lateinit var btnStartHelper: Button

    private var grid: List<List<ImageProcessor.BeadCell>> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern_editor)

        beadView = findViewById(R.id.beadView)
        tvStats = findViewById(R.id.tvStats)
        btnStartHelper = findViewById(R.id.btnStartHelper)

        val imageUri = intent.getStringExtra("image_uri") ?: return finish()
        val width = intent.getIntExtra("width", 52)
        val height = intent.getIntExtra("height", 52)

        try {
            val uri = Uri.parse(imageUri)
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap != null) {
                grid = ImageProcessor.convertToBeadPattern(bitmap, width, height)
                beadView.setGrid(grid)
                updateStats()
            } else {
                Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show()
                finish()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "处理失败: ${e.message}", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnStartHelper.setOnClickListener {
            val intent = android.content.Intent(this, BeadHelperActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateStats() {
        val counts = ImageProcessor.countColors(grid)
        val total = ImageProcessor.totalBeads(grid)
        val colorCount = counts.size

        tvStats.text = "总豆子数: $total 颗 | 颜色数: $colorCount 色\n" +
                "前5色: " + counts.entries.take(5).joinToString(", ") {
                    "${it.key}(${it.value}颗)"
                }
    }
}
