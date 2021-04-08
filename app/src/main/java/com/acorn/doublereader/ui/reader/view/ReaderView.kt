package com.acorn.doublereader.ui.reader.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.base.commonmodule.extend.dp
import kotlin.math.abs

/**
 * Created by acorn on 2021/4/6.
 */
class ReaderView : View {
    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 50f
        strokeWidth = 2f.dp
        color = Color.BLACK
    }
    private var text: String? = null
    private var mStaticLayout: StaticLayout? = null
    private var textWidth: Int = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
//        context.obtainStyledAttributes(attr, R.styleable.ReaderView, defStyleAttr, R.style.ReaderView).apply {
//            recycle()
//        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.WHITE)
        if (null == text) {
            return
        }
//        text?.also { mText ->
//            canvas?.drawText(
//                mText,
//                0,
//                mText.length,
//                paddingLeft.toFloat(),
//                abs(textPaint.ascent()),
//                textPaint
//            )
//        }
        mStaticLayout?.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        textWidth = w - paddingLeft - paddingRight
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        textWidth = width - paddingRight - paddingLeft
    }

    fun setText(text: String?) {
        this.text = text
        if (null == text)
            return
        post {
            mStaticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, textWidth)
                .setLineSpacing(0f, 1f).build()
            invalidate()
        }
    }
}