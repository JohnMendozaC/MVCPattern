package com.lupesoft.mvcpattern.movies.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lupesoft.mvcpattern.R
import com.lupesoft.mvcpattern.cart.controller.adapters.MovieAdapter
import com.lupesoft.mvcpattern.cart.controller.adapters.setDataMovie
import com.lupesoft.mvcpattern.cart.model.models.ShoppingCartModel
import com.lupesoft.mvcpattern.databinding.FragmentAllMoviesLayoutBinding
import com.lupesoft.mvcpattern.movies.model.dataAccess.models.MovieModel
import com.lupesoft.mvcpattern.shared.controller.extensions.isHide
import com.lupesoft.mvcpattern.shared.controller.extensions.showMessage
import com.lupesoft.mvcpattern.shared.model.dataAccess.utils.Status
import com.lupesoft.mvcpattern.shared.model.dataAccess.utils.response.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllMoviesFragment : Fragment() {

    private lateinit var binding: FragmentAllMoviesLayoutBinding

    @Inject
    lateinit var movieModel: MovieModel

    @Inject
    lateinit var shoppingCartModel: ShoppingCartModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllMoviesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeActionShoppingCart()
        subscribeUi()
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        with(binding) {
            when (result.status) {
                Status.LOADING -> loader.root.isHide(false)
                Status.SUCCESS -> loader.root.isHide(true)
                Status.ERROR -> loader.root.isHide(true)
            }
            if (result.status != Status.LOADING) {
                (result.message
                    ?: requireContext().getString(R.string.something_unexpected_happened))
                    .showMessage(requireContext())
            }
        }
    }

    private fun subscribeActionShoppingCart() {
        with(binding) {
            binding.movieList.adapter = MovieAdapter({ addOrDelete, movieId ->
                loader.root.isHide(true)
                if (addOrDelete) {
                    shoppingCartModel.addMovie(movieId)
                        .observe(viewLifecycleOwner, Observer { result ->
                            actionEventAddOrRemove(result)
                        })
                } else {
                    shoppingCartModel.deleteMovie(movieId)
                        .observe(viewLifecycleOwner, Observer { result ->
                            actionEventAddOrRemove(result)
                        })
                }
            }, R.menu.popup_menu)
        }
    }

    private fun subscribeUi() {
        with(binding) {
            loader.root.isHide(false)
            movieModel.getAllMovies().observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Status.LOADING -> loader.root.isHide(false)
                    Status.SUCCESS -> {
                        movieList.setDataMovie(result.data, listEmpty)
                        loader.root.isHide(true)
                    }
                    Status.ERROR -> {
                        movieList.setDataMovie(null, listEmpty)
                        loader.root.isHide(true)
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            })
        }
    }
}