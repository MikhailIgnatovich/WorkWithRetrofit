package com.bulich.misha.workwithretrofit.tabs.genres

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
import com.bulich.misha.workwithretrofit.api.models.GenreApiModel
import com.bulich.misha.workwithretrofit.databinding.CatalogGenresBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CatalogGenres : Fragment() {

    private var binding: CatalogGenresBinding? = null
    private var genresAdapter: GenresAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.catalog_genres, container, false)

        loadGenres()

        binding?.deleteAllGenre?.setOnClickListener {
            clearAllGenres()
        }

        return binding?.root
    }


    private fun clearAllGenres() {
        val callClearAllGenres: Call<ResponseBody?>? = ApiClient.instance?.api?.clearGenre()

        callClearAllGenres?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Жанры удалены!", Toast.LENGTH_LONG).show()
                loadGenres()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет", Toast.LENGTH_LONG).show()
            }

        })

    }


    private fun loadGenres() {
        val callGenres = ApiClient.instance?.api?.getGenre()

        callGenres?.enqueue(object : Callback<ArrayList<GenreApiModel>> {
            override fun onResponse(
                call: Call<ArrayList<GenreApiModel>>,
                response: Response<ArrayList<GenreApiModel>>
            ) {
                val loadGenres = response.body()
                binding?.recyclerGenres?.layoutManager = LinearLayoutManager(context)
                genresAdapter = loadGenres?.let {
                    GenresAdapter(
                        it, { idGenre: Int -> deleteGenre(idGenre) },
                        { genreApiModel: GenreApiModel -> editGenre(genreApiModel) }
                    )
                }
                binding?.recyclerGenres?.adapter = genresAdapter

                Toast.makeText(context, "ЗАГРУЗКА", Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ArrayList<GenreApiModel>>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет!", Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun editGenre(genreApiModel: GenreApiModel) {
        val panelEditGenre = PanelEditGenre()
        val parameters = Bundle()
        parameters.putString("idGenre", genreApiModel.id.toString())
        parameters.putString("nameGenre", genreApiModel.name)
        panelEditGenre.arguments = parameters

        panelEditGenre.show((context as FragmentActivity).supportFragmentManager, "editGenre")
    }


    private fun deleteGenre(idGenre: Int) {
        val callDeleteGenre: Call<ResponseBody?>? = ApiClient.instance?.api?.deleteGenre(idGenre)

        callDeleteGenre?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Жанр удален!", Toast.LENGTH_LONG).show()
                loadGenres()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите Интернет", Toast.LENGTH_LONG).show()
            }

        })
    }
}