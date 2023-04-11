package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import ru.shiryaev.domain.models.Note
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.common.controllers.ItemNoteController
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class BottomSheet @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    var onChangeState: ((Int) -> Unit)? = null

    private var mMarginTop: Int = 112

    private val mEasyAdapter = EasyAdapter()
    private val mBottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var mBottomSheet: MaterialCardView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: CircularProgressIndicator
    private lateinit var mInfoError: LinearLayoutCompat

    init {
        inflate(context, R.layout.bottom_sheet_list, this)

        initView()

        initRecyclerView()

        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 600
            isHideable = false
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    onChangeState?.invoke(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    fun setPeekHeight(height: Int) {
        mBottomSheetBehavior.peekHeight = height
    }

    fun setExpandedHeight(height: Int) {
        val params = mBottomSheet.layoutParams
        params.height = height
        mBottomSheet.layoutParams = params
    }

    fun setNoteList(notes: List<Note>): Boolean {
        val itemNote = ItemNoteController().apply {
            onClickNote = { note ->
                // TODO
            }
        }
        val itemList = ItemList.create().apply {
            addAll(notes, itemNote)
        }
        mEasyAdapter.setItems(itemList)
        return mEasyAdapter.itemCount == 0
    }

    fun setLoading(value: Boolean) {
        mProgress.isVisible = value
    }

    fun setInfoError(value: Boolean) {
        mInfoError.isVisible = value
    }

    private fun initView() {
        mBottomSheet = findViewById(R.id.bottom_sheet)
        mRecyclerView = findViewById(R.id.recycler_view)
        mProgress = findViewById(R.id.calendar_note_pb)
        mInfoError = findViewById(R.id.info_tv)
    }

    private fun initRecyclerView() {
        with (mRecyclerView) {
            adapter = mEasyAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
}