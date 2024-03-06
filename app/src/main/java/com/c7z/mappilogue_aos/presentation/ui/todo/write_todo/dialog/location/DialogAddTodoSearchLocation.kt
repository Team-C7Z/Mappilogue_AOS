package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.databinding.DialogAddTodoSearchLocationBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location.adapter.LocationAdapter
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location.viewmodel.DialogAddTodoSearchLocationViewModel
import com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DialogAddTodoSearchLocation : DialogFragment() {
    private lateinit var binding : DialogAddTodoSearchLocationBinding
    private val viewModel : DialogAddTodoSearchLocationViewModel by viewModels()
    private val todoViewModel : AddTodoViewModel by activityViewModels()

    private val locationAdapter by lazy {
        LocationAdapter(::onLocationItemClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_todo_search_location, container, false)

        initBinding()
        initUi()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
        binding.viewModel = viewModel
    }

    private fun initUi() {
        initSearch()
        initRV()
    }

    private fun initRV() {
        binding.dialogAddTodoSearchLocationRvContent.adapter = locationAdapter
    }

    private fun initSearch() {
        binding.dialogAddTodoSearchLocationEdtContent.setOnEditorActionListener { textView, i, keyEvent ->
            if(textView.text.isNotEmpty() && keyEvent.action == KeyEvent.ACTION_DOWN) {
                requestSearch()
            }
            return@setOnEditorActionListener true
        }
    }

    private fun requestSearch() {
        lifecycleScope.launch {
            viewModel.requestSearch().collectLatest {
                locationAdapter.submitData(it)
            }
        }
    }

    private fun onLocationItemClicked(document : ResponseKakaoLocation.Document) {
        todoViewModel.insertLocationList(document)
        dismiss()
    }

    /** Set Dialog Size **/
    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x;
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt(); params?.height = (deviceHeight * 0.7).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}