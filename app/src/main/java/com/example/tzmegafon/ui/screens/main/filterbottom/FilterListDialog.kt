package com.example.tzmegafon.ui.screens.main.filterbottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.tzmegafon.App
import com.example.tzmegafon.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterListDialog(
    private val id:(id:Int,name:String) -> Unit
) : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.filter_list_dialog, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getId = App.sharedPreferences.getInt("ID",0)
        view.findViewById<RelativeLayout>(R.id.active_box).setOnClickListener {
            id.invoke(1,"активные")
            dismiss()
        }

        view.findViewById<RelativeLayout>(R.id.succsess_box).setOnClickListener {
            id.invoke(2,"выполненые")
            dismiss()
        }
        if (getId == 1){
            view.findViewById<ImageView>(R.id.enabled_open_1).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.enabled_open_2).visibility = View.GONE
        }else if (getId == 2){
            view.findViewById<ImageView>(R.id.enabled_open_1).visibility = View.GONE
            view.findViewById<ImageView>(R.id.enabled_open_2).visibility = View.VISIBLE
        }


    }

}