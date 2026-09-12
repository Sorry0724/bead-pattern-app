package com.beadpattern.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class BeadPatternView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var grid: List<List<ImageProcessor.BeadCell>> = emptyList()
    private var cellSize = 20f
    private var showGridLines = true
    private var showCompleted = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val gridLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        strokeWidth = 1f
    }
    private val completedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(100, 0, 0, 0)
    }

    var onCellClickListener: ((row: Int, col: Int) -> Unit)? = null

    fun setGrid(grid: List<List<ImageProcessor.BeadCell>>) {
        this.grid = grid
        calculateCellSize()
        invalidate()
    }

    private fun calculateCellSize() {
        if (grid.isEmpty()) return
        val cols = grid[0].size
        val rows = grid.size
        val viewWidth = width.toFloat()
        val viewHeight = height.toFloat()
        cellSize = minOf(viewWidth / cols, viewHeight / rows)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateCellSize()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (grid.isEmpty()) return

        val rows = grid.size
        val cols = grid[0].size

        val totalWidth = cols * cellSize
        val totalHeight = rows * cellSize
        val offsetX = (width - totalWidth) / 2
        val offsetY = (height - totalHeight) / 2

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val cell = grid[row][col]
                val left = offsetX + col * cellSize
                val top = offsetY + row * cellSize
                val right = left + cellSize
                val bottom = top + cellSize

                paint.color = Color.rgb(cell.color.r, cell.color.g, cell.color.b)
                canvas.drawRect(left, top, right, bottom, paint)

                if (showCompleted && cell.completed) {
                    canvas.drawRect(left, top, right, bottom, completedPaint)
                }
            }
        }

        if (showGridLines) {
            for (col in 0..cols) {
                val x = offsetX + col * cellSize
                canvas.drawLine(x, offsetY, x, offsetY + totalHeight, gridLinePaint)
            }
            for (row in 0..rows) {
                val y = offsetY + row * cellSize
                canvas.drawLine(offsetX, y, offsetX + totalWidth, y, gridLinePaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val rows = grid.size
            val cols = if (rows > 0) grid[0].size else 0
            if (rows == 0 || cols == 0) return false

            val totalWidth = cols * cellSize
            val totalHeight = rows * cellSize
            val offsetX = (width - totalWidth) / 2
            val offsetY = (height - totalHeight) / 2

            val col = ((event.x - offsetX) / cellSize).toInt()
            val row = ((event.y - offsetY) / cellSize).toInt()

            if (row in 0 until rows && col in 0 until cols) {
                onCellClickListener?.invoke(row, col)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun toggleCell(row: Int, col: Int) {
        if (row in grid.indices && col in grid[0].indices) {
            grid[row][col].completed = !grid[row][col].completed
            invalidate()
        }
    }

    fun getProgress(): Pair<Int, Int> {
        var completed = 0
        var total = 0
        for (row in grid) {
            for (cell in row) {
                total++
                if (cell.completed) completed++
            }
        }
        return Pair(completed, total)
    }
}
