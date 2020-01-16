package com.rdo.octo.mobtrends

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet_content.*
import kotlinx.android.synthetic.main.cell_drawer.view.*
import kotlinx.android.synthetic.main.drawer_content.*

abstract class BottomSheetActivity : AppCompatActivity() {

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.activity_bottom_sheet)
        rootViewToInflate.removeAllViews()
        LayoutInflater.from(this).inflate(layoutResID, rootViewToInflate)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            patternContentTextView.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }
        behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                bottom_sheet_black_background.alpha = p1 * 0.5f
            }

            override fun onStateChanged(p0: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottom_sheet_black_background.visibility = View.GONE
                } else {
                    bottom_sheet_black_background.visibility = View.VISIBLE
                }
            }

        })
        bottom_sheet_black_background.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        initDrawer()
    }

    override fun onResume() {
        super.onResume()
        val pattern = intent.getIntExtra("patternId", -1)
        Pattern.values().find {
            it.id == pattern
        }?.let {
            Handler().postDelayed({
                openBottomSheetMwahaha(it)
            }, 500)
        }
    }

    override fun onBackPressed() {
        if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    protected fun openBottomSheetMwahaha(pattern: Pattern) {
        hideKeyboard()
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Open Bottom sheet")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Open Bottom sheet")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, pattern.getPatternLabel())
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        Handler().postDelayed({
            patternImageView.setImageResource(pattern.image)
            patternContentTextView.setText(pattern.text)
            fileImageView.setBackgroundResource(pattern.categoryColor)
            patternNumberTextView.text = pattern.getPatternLabel()
            patternTitleTextView.setText(pattern.title)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }, 300)
    }

    private fun initDrawer() {
        drawerRecyclerView.layoutManager = LinearLayoutManager(this)
        drawerRecyclerView.adapter = DrawerAdapter(::onDrawerItemClicked)
        closeButton.setOnClickListener {
            drawerRoot.closeDrawers()
        }
    }

    private fun onDrawerItemClicked(id: Int) {
        when (id) {
            1, 6, 7 -> startActivity(
                Intent(
                    this,
                    OperationsActivity::class.java
                ).putExtra("patternId", id)
            )
            4, 5, 8, 9 -> startActivity(
                Intent(
                    this,
                    TransferActivity::class.java
                ).putExtra("patternId", id)
            )
            2, 3 -> startActivity(
                Intent(
                    this,
                    TransferConfirmationActivity::class.java
                ).putExtra("patternId", id)
            )
        }
        finish()
    }

}

class DrawerAdapter(private val click: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = Pattern.values().toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_drawer,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.patternTitleTextView.setText(item.title)
        holder.itemView.patternNumberTextView.text = item.getPatternLabel()
        holder.itemView.patternImageView.setBackgroundResource(item.categoryColor)
        holder.itemView.drawerCellRoot.setOnClickListener {
            click(item.id)
        }
    }

}