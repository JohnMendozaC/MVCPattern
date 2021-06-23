package com.lupesoft.mvcpattern.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lupesoft.mvcpattern.R
import com.lupesoft.mvcpattern.controller.adapters.MovieAdapter
import com.lupesoft.mvcpattern.controller.adapters.setDataMovie
import com.lupesoft.mvcpattern.controller.extensions.isHide
import com.lupesoft.mvcpattern.controller.extensions.showMessage
import com.lupesoft.mvcpattern.databinding.FragmentShoppingCartLayoutBinding
import com.lupesoft.mvcpattern.model.dataAccess.utils.Status
import com.lupesoft.mvcpattern.model.dataAccess.utils.response.Resource
import com.lupesoft.mvcpattern.model.models.ShoppingCartModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingCartFragment : Fragment() {

    @Inject
    lateinit var shoppingCartModel: ShoppingCartModel

    private lateinit var binding: FragmentShoppingCartLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCartLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeActionShoppingCart()
        updateListMovies()
        subscribeActionRemoveAllMovies()
    }

    private fun subscribeActionRemoveAllMovies() {
        with(binding) {
            fabRemoveAllMovies.setOnClickListener {
                loaderShopping.root.isHide(true)
                shoppingCartModel.deleteAllMovie()
                    .observe(viewLifecycleOwner, Observer { result ->
                        actionEventAddOrRemove(result)
                    })
            }
        }
    }

    private fun subscribeActionShoppingCart() {
        with(binding) {
            shoppingCartMovieList.adapter = MovieAdapter(
                { _, movieId ->
                    loaderShopping.root.isHide(true)
                    shoppingCartModel.deleteMovie(movieId)
                        .observe(viewLifecycleOwner, Observer { result ->
                            actionEventAddOrRemove(result)
                        })
                }, menuRes = R.menu.popup_menu_shopping
            )
        }
    }

    private fun <T> actionEventAddOrRemove(result: Resource<T>) {
        with(binding) {
            when (result.status) {
                Status.LOADING -> loaderShopping.root.isHide(false)
                Status.SUCCESS -> {
                    updateListMovies()
                    loaderShopping.root.isHide(true)
                }
                Status.ERROR -> loaderShopping.root.isHide(true)
            }
            if (result.status != Status.LOADING) {
                (result.message
                    ?: requireContext().getString(R.string.something_unexpected_happened)
                        ).showMessage(requireContext())
            }
        }
    }

    private fun updateListMovies() {
        with(binding) {
            loaderShopping.root.isHide(false)
            shoppingCartModel.getAllMoviesIntoShoppingCart()
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> loaderShopping.root.isHide(false)
                        Status.SUCCESS -> {
                            shoppingCartMovieList.setDataMovie(result.data, listEmptyCart)
                            loaderShopping.root.isHide(true)
                        }
                        Status.ERROR -> {
                            shoppingCartMovieList.setDataMovie(null, listEmptyCart)
                            loaderShopping.root.isHide(true)
                            (result.message
                                ?: requireContext().getString(R.string.something_unexpected_happened)
                                    ).showMessage(requireContext())
                        }
                    }
                })
        }
    }
}