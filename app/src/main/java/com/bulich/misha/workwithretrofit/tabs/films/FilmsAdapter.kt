package com.bulich.misha.workwithretrofit.tabs.films

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bulich.misha.workwithretrofit.R
import com.bulich.misha.workwithretrofit.api.models.FilmApiModel
import com.bulich.misha.workwithretrofit.databinding.FilmItemBinding

class FilmsAdapter(
    private val filmsList: ArrayList<FilmApiModel>,
    private val deleteFilm: (Int) -> Unit,
    private val editFilm: (FilmApiModel) -> Unit
) : RecyclerView.Adapter<FilmsAdapter.FilmsHolder>() {

    class FilmsHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            film: FilmApiModel, deleteFilm: (Int) -> Unit,
            editFilm: (FilmApiModel) -> Unit
        ) {
            val idFilm = film.id
            binding.idFilm.text = idFilm.toString()
            binding.nameFilm.text = film.name
            binding.genreFilm.text = film.genre
            binding.ratingFilm.text = film.rating

            binding.deleteFilm.setOnClickListener {
                deleteFilm(idFilm!!)
            }

            binding.editFilm.setOnClickListener {
                editFilm(film)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FilmItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.film_item, parent, false
        )
        return FilmsHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        holder.bind(filmsList[position], deleteFilm, editFilm)
    }

    override fun getItemCount(): Int {
        return filmsList.size
    }
}