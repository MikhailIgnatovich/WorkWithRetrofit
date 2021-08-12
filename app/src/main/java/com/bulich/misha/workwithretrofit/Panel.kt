package com.bulich.misha.workwithretrofit

import android.media.Rating
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bulich.misha.workwithretrofit.api.ApiClient
import com.bulich.misha.workwithretrofit.databinding.PanelBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Panel : Fragment(), View.OnKeyListener, View.OnClickListener {

    private var binding: PanelBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.panel, container, false)

        binding?.enterNameFilm?.setOnKeyListener(this)
        binding?.enterGenreFilm?.setOnKeyListener(this)
        binding?.enterRatingFilm?.setOnKeyListener(this)

        binding?.addEnterButtonFilm?.setOnClickListener(this)

        return binding?.root
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        when (v.id) {

            R.id.enterNameFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterNameFilm?.text = binding?.enterNameFilm?.text
                    binding?.enterNameFilm?.setText("")

                    return true
                }
            }

            R.id.enterGenreFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterGenreFilm?.text = binding?.enterGenreFilm?.text
                    binding?.enterGenreFilm?.setText("")

                    return true
                }
            }

            R.id.enterRatingFilm -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    binding?.resEnterRatingFilm?.text = binding?.enterRatingFilm?.text
                    binding?.enterRatingFilm?.setText("")

                    return true
                }
            }
        }
        return false
    }


    override fun onClick(v: View?) {
//        insertGenre(binding?.resEnterGenreFilm?.text?.toString()!!)
        insertFilm(
            binding?.resEnterNameFilm?.text?.toString()!!,
            binding?.resEnterGenreFilm?.text?.toString()!!,
            binding?.resEnterRatingFilm?.text?.toString()!!
        )
        clearResEnterFilm()

    }


    private fun insertGenre(name: String) {
        val callInsertGenre = ApiClient.instance?.api?.insertGenre(name)

        callInsertGenre?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                TODO()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                Log.d("PAN", "$t")
            }

        })
    }


    private fun insertFilm(name: String?, genre: String?, rating: String?) {
        val callInsertFilm: Call<ResponseBody?>? = ApiClient.instance?.api?.insertFilm(name, genre, rating)

        callInsertFilm?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Фильм добавлен в базу данных", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun clearResEnterFilm() {
        binding?.resEnterNameFilm?.setText("")
        binding?.resEnterGenreFilm?.setText("")
        binding?.resEnterRatingFilm?.setText("")
    }
}