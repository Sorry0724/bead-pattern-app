package com.beadpattern.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BeadHelperActivity : AppCompatActivity() {

    private lateinit var beadView: BeadPatternView
    private lateinit var tvProgress: TextView
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var btnReset: Button

    private var currentRow = 0
    private val rowsPerPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bead_helper)

        beadView = findViewById(R.id.beadView)
        tvProgress = findViewById(R.id.tvProgress)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnReset = findViewById(R.id.btnReset)

        val demoGrid = ImageProcessor.convertToBeadPattern(
            android.graphics.Bitmap.createBitmap(52, 52, android.graphics.Bitmap.Config.ARGB_8888),
            52, 52
        )

        beadView.setGrid(demoGrid)
        beadView.onCellClickListener = { row, col ->
            beadView.toggleCell(row, col)
            updateProgress()
        }

        btnPrev.setOnClickListener {
            if (currentRow > 0) {
                currentRow -= rowsPerPage
                updatePage()
            }
        }

        btnNext.setOnClickListener {
            if (currentRow < demoGrid.size - rowsPerPage) {
                currentRow += rowsPerPage
                updatePage()
            }
        }

        btnReset.setOnClickListener {
            for (row in demoGrid) {
                for (cell in row) {
                    cell.completed = false
                }
            }
            beadView.invalidate()
            updateProgress()
        }

        updateProgress()
    }

    private fun updateProgress() {
        val (completed, total) = beadView.getProgress()
        val percent = if (total > 0) (completed * 100 / total) else 0
        tvProgress.text = "进度: $completed / $total ($percent%)"
    }

    private fun updatePage() {
        updateProgress()
    }
}
