package com.bulich.misha.workwithretrofit.tabs.films

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bulich.misha.workwithretrofit.R
import com.bulich.misha.workwithretrofit.api.ApiClient
import com.bulich.misha.workwithretrofit.api.models.FilmApiModel
import com.bulich.misha.workwithretrofit.databinding.CatalogFilmsBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CatalogFilms : Fragment() {

    private var binding: CatalogFilmsBinding? = null
    private var filmsAdapter: FilmsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.catalog_films, container, false)

        loadFilms()

        binding?.deleteAllFilms?.setOnClickListener {
            clearAllFilms()
        }
        return binding?.root
    }


    private fun clearAllFilms() {
        val callClearAllFilms: Call<ResponseBody?>? = ApiClient.instance?.api?.clearFilm()

        callClearAllFilms?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Все фильмы удалены", Toast.LENGTH_LONG).show()
                loadFilms()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun loadFilms() {
        val callLoadFilms = ApiClient.instance?.api?.getFilm()

        callLoadFilms?.enqueue(object : Callback<ArrayList<FilmApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<FilmApiModel>>,
                response: Response<ArrayList<FilmApiModel>>
            ) {
                val loadFilms = response.body()

                binding?.recyclerFilms?.layoutManager = LinearLayoutManager(context)
                filmsAdapter = loadFilms?.let {
                    FilmsAdapter(it, { idFilms: Int -> deleteFilm(idFilms) },
                        { filmApiModel: FilmApiModel -> editFilm(filmApiModel) })
                }
                binding?.recyclerFilms?.adapter = filmsAdapter

                Toast.makeText(context, "Загрузка", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ArrayList<FilmApiModel>>, t: Throwable) {

                Toast.makeText(context, "Ошибка! Включите интернет", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun editFilm(filmApiModel: FilmApiModel) {
        val editPanelFilm = PanelEditFilm()
        val parameters = Bundle()
        parameters.putString("idFilm", filmApiModel.id.toString())
        parameters.putString("nameFilm", filmApiModel.name)
        parameters.putString("genreFilm", filmApiModel.genre)
        parameters.putString("ratingFilm", filmApiModel.rating)
        editPanelFilm.arguments = parameters

        editPanelFilm.show((context as FragmentActivity).supportFragmentManager, "editFilm")

    }


    private fun deleteFilm(idFilms: Int) {
        val callDeleteFilm: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteFilm(idFilms)

        callDeleteFilm?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Фильм удален!", Toast.LENGTH_LONG).show()
                loadFilms()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет", Toast.LENGTH_LONG).show()
            }

        })
    }
}