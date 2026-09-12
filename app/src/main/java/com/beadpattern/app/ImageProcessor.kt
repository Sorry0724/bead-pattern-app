package com.beadpattern.app

import android.graphics.Bitmap
import android.graphics.Color

object ImageProcessor {

    data class BeadCell(
        val row: Int,
        val col: Int,
        val color: ColorPalette.BeadColor,
        var completed: Boolean = false
    )

    fun convertToBeadPattern(
        bitmap: Bitmap,
        targetWidth: Int,
        targetHeight: Int
    ): List<List<BeadCell>> {
        val scaled = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

        val grid = mutableListOf<List<BeadCell>>()
        for (row in 0 until targetHeight) {
            val rowCells = mutableListOf<BeadCell>()
            for (col in 0 until targetWidth) {
                val pixel = scaled.getPixel(col, row)
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                val alpha = Color.alpha(pixel)

                val beadColor = if (alpha < 128 || (r > 245 && g > 245 && b > 245)) {
                    ColorPalette.mardColors.find { it.code == "G01" }!!
                } else {
                    ColorPalette.findNearestColor(r, g, b)
                }

                rowCells.add(BeadCell(row, col, beadColor))
            }
            grid.add(rowCells)
        }

        return grid
    }

    fun countColors(grid: List<List<BeadCell>>): Map<String, Int> {
        val counts = mutableMapOf<String, Int>()
        for (row in grid) {
            for (cell in row) {
                val code = cell.color.code
                counts[code] = counts.getOrDefault(code, 0) + 1
            }
        }
        return counts.toList().sortedByDescending { it.second }.toMap()
    }

    fun totalBeads(grid: List<List<BeadCell>>): Int {
        var total = 0
        for (row in grid) {
            for (cell in row) {
                total++
            }
        }
        return total
    }
}
