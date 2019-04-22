package com.panch.minesweeperkt.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.panch.minesweeperkt.R
import com.panch.minesweeperkt.listener.MineBlockListener
import kotlinx.android.synthetic.main.layout_block.view.*

class MineView : FrameLayout {
    private var view: View = inflate(context, R.layout.layout_block, this)
    private lateinit var listener: MineBlockListener

    constructor(_listener: MineBlockListener? = null, context: Context) : super(context) {
        if (_listener != null)
            listener = _listener
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        view.imageViewBlock.scaleType = ImageView.ScaleType.FIT_XY
        view.imageViewBlock.setImageResource(R.drawable.uncleared_mine)
        view.setOnClickListener {
            listener.onMineBlockClear(this)
        }
        view.setOnLongClickListener {
            listener.onMineBlockFlag(this)

            true
        }
        flagged = false
        cleared = false
        dangerLevel = 0
    }

    var x = 0
    var y = 0
    var dangerLevel: Int = 0 // 0->none, -1 -> mined
    var flagged: Boolean = false
        set(value) {
            field = value
            if (field) {
                view.imageViewFlag.visibility = View.VISIBLE
            } else {
                view.imageViewFlag.visibility = View.GONE
            }
        }
    var cleared: Boolean = false
        set(value) {
            field = value
            if (field) {
                view.imageViewBlock.setImageResource(R.drawable.cleared_mine)
                view.imageViewFlag.visibility = View.GONE

                view.textViewBlock.text = dangerLevel.toString()
                when (dangerLevel) {
                    0 -> {
                        view.textViewBlock.visibility = View.GONE
                    }
                    -1 -> {
                        view.textViewBlock.visibility = View.GONE
                        view.imageViewFlag.setImageResource(R.drawable.mine)
                        view.imageViewFlag.visibility = View.VISIBLE
                        listener.onMineBlockExplode(this)
                    }
                    else -> view.textViewBlock.visibility = View.VISIBLE
                }

            } else {
                view.imageViewBlock.setImageResource(R.drawable.uncleared_mine)
            }
        }
}