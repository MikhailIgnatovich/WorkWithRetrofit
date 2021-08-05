package com.bulich.misha.workwithretrofit.tabs.genres

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
import com.bulich.misha.workwithretrofit.databinding.PanelEditGenreBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response


class PanelEditGenre : BottomSheetDialogFragment(), View.OnKeyListener {

    private var binding: PanelEditGenreBinding? = null
    private var idGenre: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.panel_edit_genre, container, false)

        idGenre = arguments?.getString("idGenre")?.toInt()
        binding?.editGenre?.setText(arguments?.getString("nameGenre").toString())

        binding?.editGenre?.setOnKeyListener(this)

        return binding?.root
    }

    override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
        when (v.id) {
            R.id.editGenre -> {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    updateGenre(binding?.editGenre?.text.toString())

                    return true
                }
            }
        }
        return false
    }


    private fun updateGenre(name: String) {
        val callUpdateGenre = ApiClient.instance?.api?.updateGenre(idGenre.toString().toInt(), name)
        callUpdateGenre?.enqueue(object : retrofit2.Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Toast.makeText(context, "Жанр обновлен!", Toast.LENGTH_LONG).show()
                loadScreen()
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Ошибка! Включите интернет!", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun loadScreen() {
        binding?.editGenre?.setText("")

        dismiss()

        (context as FragmentActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.content, CatalogGenres())
    }
}