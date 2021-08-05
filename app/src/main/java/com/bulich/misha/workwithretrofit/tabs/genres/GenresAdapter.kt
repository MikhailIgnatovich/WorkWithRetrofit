package com.bulich.misha.workwithretrofit.tabs.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bulich.misha.workwithretrofit.R
import com.bulich.misha.workwithretrofit.api.models.GenreApiModel
import com.bulich.misha.workwithretrofit.databinding.GenreItemBinding

class GenresAdapter(private val genresList: ArrayList<GenreApiModel>,
                    private val deleteGenre: (Int) -> Unit,
                    private val editGenre: (GenreApiModel) -> Unit) : RecyclerView.Adapter<GenresAdapter.GenreHolder>() {

    class GenreHolder(val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            genre: GenreApiModel, deleteGenre: (Int) -> Unit,
            editGenre: (GenreApiModel) -> Unit
        ) {
            val idGenre = genre.id

            binding.idGenre.text = idGenre.toString()

            binding.nameGenre.text = genre.name

            binding.deleteGenre.setOnClickListener {
                deleteGenre(idGenre!!)
            }

            binding.editGenre.setOnClickListener {
                editGenre(genre)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: GenreItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.genre_item, parent, false)
        return GenreHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreHolder, position: Int) {
        holder.bind(genresList[position], deleteGenre, editGenre)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }
}