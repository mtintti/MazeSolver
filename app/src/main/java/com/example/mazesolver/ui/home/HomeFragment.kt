package com.example.mazesolver.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.material.Button
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mazesolver.FlipMaze
//import com.example.mazesolver.MazeFragment
import com.example.mazesolver.MazeViewModel
import com.example.mazesolver.R
import com.example.mazesolver.TimerModel
//import com.example.mazesolver.TimerViewModel
import com.example.mazesolver.ViewMaze
import com.example.mazesolver.databinding.FragmentHomeBinding
import java.util.Locale


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {


                val timerModel = ViewModelProvider(this@HomeFragment).get(TimerModel::class.java)  // Use TimerModel here

                 val mazeViewModel: MazeViewModel by activityViewModels()
                Log.d("MazeDebug", "MazeViewModel created")

                val maze by mazeViewModel.maze.observeAsState(emptyArray())

                val Flipped by mazeViewModel.Flipped.observeAsState(false)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                        mazeViewModel.generateMaze(mazeType = 1)
                        Log.d("MazeDebug", "Maze 2 generated.")
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.purple_500),
                            contentColor = Color.White
                        )) {
                        Text(text = stringResource(R.string.maze_1))
                    }

                    Button(
                        onClick = {
                            mazeViewModel.generateMaze(mazeType = 2)
                            Log.d("MazeDebug", "Maze 2 generated.")
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.purple_500),
                            contentColor = Color.White
                        )
                        ) {
                        Text(text = stringResource(R.string.maze_2))
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (Flipped) {
                        FlipMaze(maze) { rowIndex, colIndex ->
                            mazeViewModel.toggleCell(rowIndex, colIndex)
                        }
                    } else {
                        ViewMaze(maze)
                    }
                }

                TimerScreen(
                    viewModel = timerModel,
                    onTimerFinish = {
                        mazeViewModel.flipMaze()
                    }
                )

            }
        }
    }


    @Composable
    fun TimerScreen(viewModel: TimerModel, onTimerFinish: () -> Unit) {
        val time by viewModel.time.observeAsState(10L)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

        ) {
            Text(
                text = String.format(stringResource(R.string._02d), time),
                style = TextStyle(fontSize = 50.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.startTimer(onTimerFinish)
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.purple_500),
                    contentColor = Color.White
                )
                ) {
                Text(stringResource(R.string.start_timer))
            }
        }
    }

}