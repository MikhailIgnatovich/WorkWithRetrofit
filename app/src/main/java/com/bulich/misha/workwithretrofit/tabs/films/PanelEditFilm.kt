package com.bulich.misha.workwithretrofit.tabs.films

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.bulich.misha.workwithretrofit.R
import com.bulich.misha.workwithretrofit.api.ApiClient
import com.bulich.misha.workwithretrofit.databinding.PanelEditFilmBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PanelEditFilm : BottomSheetDialogFragment(), View.OnKeyListener, View.OnClickListener {

    private var binding: PanelEditFilmBinding? = null
    private var idFilm: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.panel_edit_film, container, false)

        idFilm = arguments?.getString("idFilm")?.toInt()
        binding?.editNameFilm?.setText(arguments?.getString("nameFilm"))
        binding?.editGenreFilm?.setText(arguments?.getString("genreFilm"))
        binding?.editRatingFilm?.setText(arguments?.getString("ratingFilm"))

        binding?.editNameFilm?.setOnKeyListener(this)
        binding?.editGenreFilm?.setOnKeyListener(this)
        binding?.editRatingFilm?.setOnKeyListener(this)

        binding?.addButtonFilm?.setOnClickListener(this)
        return binding?.root
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        when (v.id) {

            R.id.editNameFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEditNameFilm?.text = binding?.editNameFilm?.text
                    binding?.editNameFilm?.setText("")

                    return true
                }
            }

            R.id.editGenreFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEditGenreFilm?.text = binding?.editGenreFilm?.text
                    binding?.editGenreFilm?.setText("")

                    return true
                }
            }

            R.id.editRatingFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEditRatingFilm?.text = binding?.editRatingFilm?.text
                    binding?.editRatingFilm?.setText("")

                    return true
                }
            }
        }
        return false
    }


    override fun onClick(v: View?) {
        updateFilm(
            binding?.resEditNameFilm?.text?.toString()!!,
            binding?.resEditGenreFilm?.text?.toString()!!,
            binding?.resEditRatingFilm?.text?.toString()!!
        )
    }

    private fun updateFilm(name: String, genre: String, rating: String) {
        val callUpdateFilm =
            ApiClient.instance?.api?.updateFilm(idFilm.toString().toInt(), name, genre, rating)

        callUpdateFilm?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Фильм обновлен", Toast.LENGTH_LONG).show()
                loadScreen()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loadScreen() {

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.content, CatalogFilms()).commit()
    }
}