package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.uimodel.Asteroid

class MainFragment : Fragment() {

    private lateinit var adapter: AsteroidsAdapter
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        val asteroidDao = AppDatabase.getInstance(requireContext()).asteroidDao
        val asteroidRepository = AsteroidRepository.getInstance(asteroidDao)
        ViewModelProvider(
            this,
            MainViewModelFactory(asteroidRepository)
        ).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater).apply {
            lifecycleOwner = this@MainFragment
            viewModel = viewModel
        }

        setHasOptionsMenu(true)

        adapter = AsteroidsAdapter { asteroid ->
            findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
        }
        binding.asteroidRecycler.adapter = adapter

        viewModel.pictureOfTheDayUrl.observe(viewLifecycleOwner, Observer { url ->
            showPictureOfTheDay(url)
        })
        viewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroids ->
            showAsteroids(asteroids)
        })

        return binding.root
    }

    private fun showPictureOfTheDay(url: String) {
        Picasso.with(requireContext())
            .load(url)
            .into(binding.activityMainImageOfTheDay)
    }

    private fun showAsteroids(asteroids: List<Asteroid>) {
        adapter.data = asteroids
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
