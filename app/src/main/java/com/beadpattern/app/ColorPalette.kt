package com.beadpattern.app

object ColorPalette {

    data class BeadColor(
        val code: String,
        val r: Int,
        val g: Int,
        val b: Int,
        val name: String = ""
    )

    val mardColors = listOf(
        BeadColor("A01", 255, 242, 204, "奶白"),
        BeadColor("A02", 255, 230, 153, "浅黄"),
        BeadColor("A03", 255, 217, 102, "明黄"),
        BeadColor("A04", 255, 204, 0, "金黄"),
        BeadColor("A05", 230, 184, 0, "深黄"),
        BeadColor("A06", 204, 153, 0, "暗黄"),
        BeadColor("A07", 255, 217, 179, "浅橙"),
        BeadColor("A08", 255, 179, 102, "橙色"),
        BeadColor("A09", 255, 140, 51, "深橙"),
        BeadColor("A10", 230, 115, 0, "暗橙"),
        BeadColor("A11", 255, 204, 204, "浅粉橙"),
        BeadColor("A12", 255, 153, 153, "珊瑚橙"),

        BeadColor("B01", 229, 245, 224, "薄荷绿"),
        BeadColor("B02", 179, 224, 179, "浅绿"),
        BeadColor("B03", 153, 204, 153, "草绿"),
        BeadColor("B04", 102, 187, 102, "绿色"),
        BeadColor("B05", 76, 153, 76, "深绿"),
        BeadColor("B06", 51, 128, 51, "墨绿"),
        BeadColor("B07", 204, 230, 204, "浅薄荷"),
        BeadColor("B08", 153, 204, 153, "淡绿"),
        BeadColor("B09", 102, 204, 102, "亮绿"),
        BeadColor("B10", 51, 179, 51, "鲜绿"),
        BeadColor("B11", 0, 153, 76, "翠绿"),
        BeadColor("B12", 0, 128, 64, "深翠绿"),

        BeadColor("C01", 229, 242, 255, "天蓝"),
        BeadColor("C02", 179, 217, 255, "浅蓝"),
        BeadColor("C03", 128, 191, 255, "天青"),
        BeadColor("C04", 76, 153, 230, "蓝色"),
        BeadColor("C05", 51, 128, 204, "深蓝"),
        BeadColor("C06", 26, 92, 153, "藏蓝"),
        BeadColor("C07", 204, 242, 242, "青色"),
        BeadColor("C08", 153, 224, 224, "浅青"),
        BeadColor("C09", 102, 204, 204, "青蓝"),
        BeadColor("C10", 51, 179, 179, "深青"),
        BeadColor("C11", 0, 153, 153, "湖蓝"),
        BeadColor("C12", 0, 128, 128, "深湖蓝"),

        BeadColor("D01", 242, 229, 255, "淡紫"),
        BeadColor("D02", 217, 179, 255, "浅紫"),
        BeadColor("D03", 191, 128, 255, "紫色"),
        BeadColor("D04", 153, 76, 230, "深紫"),
        BeadColor("D05", 128, 51, 204, "暗紫"),
        BeadColor("D06", 102, 26, 153, "墨紫"),
        BeadColor("D07", 255, 229, 242, "粉紫"),
        BeadColor("D08", 255, 179, 217, "浅粉紫"),
        BeadColor("D09", 255, 128, 191, "玫紫"),
        BeadColor("D10", 230, 76, 153, "深玫紫"),

        BeadColor("E01", 255, 242, 242, "淡粉"),
        BeadColor("E02", 255, 217, 217, "浅粉"),
        BeadColor("E03", 255, 179, 179, "粉色"),
        BeadColor("E04", 255, 140, 140, "深粉"),
        BeadColor("E05", 230, 102, 102, "玫红"),
        BeadColor("E06", 204, 76, 76, "深红"),
        BeadColor("E07", 255, 230, 217, "肤色"),
        BeadColor("E08", 255, 204, 179, "浅肤"),
        BeadColor("E09", 230, 179, 153, "深肤"),
        BeadColor("E10", 204, 153, 128, "暗肤"),

        BeadColor("F01", 242, 229, 217, "米白"),
        BeadColor("F02", 230, 204, 179, "浅棕"),
        BeadColor("F03", 204, 163, 128, "棕色"),
        BeadColor("F04", 179, 128, 102, "深棕"),
        BeadColor("F05", 153, 102, 76, "暗棕"),
        BeadColor("F06", 128, 76, 51, "咖啡"),
        BeadColor("F07", 102, 51, 26, "深咖"),
        BeadColor("F08", 89, 57, 28, "浅卡其"),
        BeadColor("F09", 139, 90, 43, "卡其"),
        BeadColor("F10", 160, 82, 45, "深卡其"),

        BeadColor("G01", 255, 255, 255, "白色"),
        BeadColor("G02", 242, 242, 242, "浅灰"),
        BeadColor("G03", 217, 217, 217, "银灰"),
        BeadColor("G04", 191, 191, 191, "灰色"),
        BeadColor("G05", 153, 153, 153, "深灰"),
        BeadColor("G06", 102, 102, 102, "暗灰"),
        BeadColor("G07", 51, 51, 51, "炭灰"),
        BeadColor("G08", 0, 0, 0, "黑色"),
        BeadColor("G09", 255, 250, 240, "象牙白"),
        BeadColor("G10", 245, 222, 179, "米黄"),
    )

    fun findNearestColor(r: Int, g: Int, b: Int): BeadColor {
        var best = mardColors[0]
        var minDist = Float.MAX_VALUE
        for (color in mardColors) {
            val dr = r - color.r
            val dg = g - color.g
            val db = b - color.b
            val dist = dr * dr + dg * dg + db * db
            if (dist < minDist) {
                minDist = dist.toFloat()
                best = color
            }
        }
        return best
    }

    fun getColorByCode(code: String): BeadColor? {
        return mardColors.find { it.code == code }
    }
}
