package com.bulich.misha.workwithretrofit.tabs.films

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bulich.misha.workwithretrofit.R
import com.bulich.misha.workwithretrofit.databinding.PanelEditFilmBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PanelEditFilm : BottomSheetDialogFragment() {

    private var binding: PanelEditFilmBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.panel_edit_film, container, false)
        return binding?.root
    }
}