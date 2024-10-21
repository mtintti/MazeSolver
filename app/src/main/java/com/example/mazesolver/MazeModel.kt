package com.example.mazesolver

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MazeViewModel : ViewModel() {
    private val _maze = MutableLiveData<Array<Array<Int>>>()

    val maze: LiveData<Array<Array<Int>>> = _maze
    private var _Flipped = MutableLiveData(false);
    val Flipped: LiveData<Boolean> = _Flipped

    private val _pathClicks = MutableLiveData(0)
    val pathClicks: LiveData<Int> = _pathClicks

    fun generateMaze(mazeType: Int) {
        _Flipped.value = false
        val newMaze = when (mazeType) {

            1 -> generateMaze1()

            2 -> generateMaze2()


            else -> emptyArray()
        }
        _maze.value = newMaze
    }


    fun flipMaze() {
        _Flipped.value = true
    }



    fun toggleCell(rowIndex: Int, colIndex: Int) {
        _maze.value?.let { currentMaze ->
            val newMaze = currentMaze.map { it.clone() }.toTypedArray()

            if (newMaze[rowIndex][colIndex] == 1) {
                _pathClicks.value = (_pathClicks.value ?: 0) + 1
                Log.d("MazeViewModel", "Current pathClicks value: ${_pathClicks.value}")

            }


            newMaze[rowIndex][colIndex] = when (newMaze[rowIndex][colIndex]) {
                1 -> 2
                0 -> -1
                else -> newMaze[rowIndex][colIndex]
            }

            _maze.value = newMaze
        }

    }


}
private fun generateMaze1(): Array<Array<Int>>{
    return arrayOf(
        arrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0),
        arrayOf(0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0),
        arrayOf(0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0),
        arrayOf(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0)
    )
}

private fun generateMaze2(): Array<Array<Int>>{
    return arrayOf(
        arrayOf(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0),
        arrayOf(0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
        arrayOf(0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0),
        arrayOf(0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0),
        arrayOf(0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0),
        arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0)
    )
}
