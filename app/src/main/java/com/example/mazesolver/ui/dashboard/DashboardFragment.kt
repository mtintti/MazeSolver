package com.example.mazesolver.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mazesolver.JokeResponse
import com.example.mazesolver.MazeViewModel
import com.example.mazesolver.R
import com.example.mazesolver.RetrofitInstance
import com.example.mazesolver.databinding.FragmentDashboardBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val mazeViewModel: MazeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mazeViewModel.pathClicks.observe(viewLifecycleOwner) { clicks ->
            binding.textDashboard.text = getString(R.string.total_path_clicks, clicks)
        }
        binding.buttonFetchJoke.setOnClickListener {
            fetchJoke()
        }
        return root
    }




    private fun fetchJoke() {
        binding.progressBar.visibility = View.VISIBLE

        RetrofitInstance.api.getProgrammingJoke().enqueue(object : Callback<JokeResponse> {
            override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val jokeResponse = response.body()!!
                    binding.textDashboard.text = when (jokeResponse.type) {
                        "single" -> jokeResponse.joke ?: getString(R.string.no_joke_available)
                        "twopart" -> "${jokeResponse.setup}\n${jokeResponse.delivery ?: getString(R.string.no_delivery_available)}"
                        else -> getString(R.string.unknown_joke_type)
                    }
                } else {
                    binding.textDashboard.text = getString(R.string.failed_to_retrieve_joke)
                }
            }

            override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.textDashboard.text = "Error: ${t.message}"
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
