package com.example.mazesolver.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mazesolver.viewmodel.MazeViewModel
import com.example.mazesolver.R
import com.example.mazesolver.databinding.FragmentDashboardBinding
import com.example.mazesolver.viewmodel.DashboardViewModel


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val mazeViewModel: MazeViewModel by activityViewModels()

    private val dashboardViewModel: DashboardViewModel by viewModels()

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

        dashboardViewModel.joke.observe(viewLifecycleOwner) { joke ->
            binding.textDashboard.text = joke
        }

        dashboardViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.buttonFetchJoke.setOnClickListener {
            dashboardViewModel.fetchJoke()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
