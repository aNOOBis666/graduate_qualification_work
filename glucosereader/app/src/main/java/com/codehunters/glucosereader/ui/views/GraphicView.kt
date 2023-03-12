package com.codehunters.glucosereader.ui.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.codehunters.data.models.GlucoseData
import com.codehunters.data.models.LinePosition
import com.codehunters.glucosereader.R


class GraphicView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        private const val GRAPH_START_X = 2F
        private const val START_X = 82F

        private const val DEFAULT_STROKE_WIDTH = 2F
        private const val DEFAULT_ACCENT_LINE_COLOR = Color.BLUE
        private const val DEFAULT_LINES_COLOR = Color.BLACK
        private const val DEFAULT_VALUES_DELTA = 3
        private const val DEFAULT_LINES_COUNT = 8
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.GraphicView,
            0, 0
        ).apply {
            dashedLines = getInteger(R.styleable.GraphicView_dashedLines, DEFAULT_LINES_COUNT)
            dashedLinesDelta = getInteger(R.styleable.GraphicView_dashedLinesDelta, DEFAULT_VALUES_DELTA)
            accentLineColor = getColor(R.styleable.GraphicView_accent_line_color, DEFAULT_ACCENT_LINE_COLOR)
            accentLineWidth = getFloat(R.styleable.GraphicView_accent_line_width, DEFAULT_STROKE_WIDTH)
        }
    }

    private var itemsList = emptyList<GlucoseData>()
    fun setItemsList(items: List<GlucoseData>) {
        itemsList = items
        this.invalidate()
    }
    fun getItemsList() = itemsList

    private val paintBackground = Paint()
    private var guidelinePaint = Paint()
    private var guideDashPaint = Paint()
    private var graphLine = Paint()
    private var textPaint = Paint()

    private val paintAxisX = Path()
    private val paintAxisY = Path()
    private val paintDiagAxis = Path()

    private var dashedLines: Int
    private var dashedLinesDelta: Int
    private var accentLineColor: Int
    private var accentLineWidth: Float

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawBackground(canvas)
        guideDashPaint = drawGuideline(true)
        guidelinePaint = drawGuideline()
        graphLine = drawGuideline(
            lineWidth = accentLineWidth,
            lineColor = accentLineColor
        )
        setTextStyle()
        drawHorizontalGuidelines(canvas)
        drawGraph(canvas)
    }

    private fun drawBackground(canvas: Canvas?) {
        paintBackground.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }
        canvas?.drawPaint(paintBackground)
    }

    private fun setTextStyle() {
        textPaint.apply {
            textSize = context.resources.getDimension(R.dimen.graphic_text_size)
            color = Color.BLACK
        }
    }

    private fun drawGuideline(
        isDashed: Boolean = false,
        lineWidth: Float = DEFAULT_STROKE_WIDTH,
        lineColor: Int = DEFAULT_LINES_COLOR
    ) = Paint().apply {
            strokeWidth = lineWidth
            isAntiAlias = true
            color = lineColor
            style = Paint.Style.STROKE
            if (isDashed) pathEffect = DashPathEffect(floatArrayOf(10f, 8f), 0f)
        }

    private fun drawHorizontalGuidelines(canvas: Canvas?) {
        val linePos = LinePosition(
            startX = GRAPH_START_X,
            endX = width.toFloat(),
            startY = height.toFloat() - GRAPH_START_X,
            endY = height.toFloat() - GRAPH_START_X
        )
        setAxis(canvas, paintAxisX, guidelinePaint, linePos)

        for (i in 1 until dashedLines) {
            val yPos: Float = (i * height.toFloat() / (dashedLines))

            canvas?.drawText(
                "${(dashedLines - i) * dashedLinesDelta}",
                GRAPH_START_X,
                yPos + (context.resources.getDimension(R.dimen.graphic_text_size) / 3),
                textPaint
            )
            val gridLine = LinePosition(
                startX = START_X,
                endX = width.toFloat(),
                startY = yPos,
                endY = yPos
            )
            setAxis(canvas, paintAxisY, guideDashPaint, gridLine)
        }
    }

    private fun setAxis(canvas: Canvas?, path: Path, paint: Paint, linePos: LinePosition) {
        canvas?.drawPath(
            path.apply {
                reset()
                moveTo(linePos.startX, linePos.startY)
                lineTo(linePos.endX, linePos.endY)
            },
            paint
        )
    }

    private fun drawGraph(canvas: Canvas?) {
        val itemWidth = width.toFloat() / (itemsList.size - 1)
        var previousPos: LinePosition? = null
        itemsList.map {
            val currentLine = LinePosition(
                startX = previousPos?.endX ?: START_X,
                endX = previousPos?.endX?.plus(itemWidth) ?: START_X,
                startY = previousPos?.endY ?: it.value.toYPos(),
                endY = it.value.toYPos()
            )
            setAxis(canvas, paintDiagAxis, graphLine, currentLine)
            previousPos = currentLine
        }
    }

    private fun Float.toYPos(): Float {
        val itemHeight = height.toFloat() / dashedLines
        val numOfItems = this / dashedLinesDelta
        return height - (numOfItems * itemHeight)
    }
}