package com.example.mazesolver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MazeViewModel : ViewModel() {
    private val _maze = MutableLiveData<Array<Array<Int>>>()
    val maze: LiveData<Array<Array<Int>>> = _maze

    private val _Flipped = MutableLiveData(false)
    val Flipped: LiveData<Boolean> = _Flipped

    private val _pathClicks = MutableLiveData(0)
    val pathClicks: LiveData<Int> = _pathClicks

    fun generateMaze1() {
        _Flipped.value = false
        _maze.value = generateMaze1Data()
    }

    fun generateMaze2() {
        _Flipped.value = false
        _maze.value = generateMaze2Data()
    }

    fun flipMaze() {
        _Flipped.value = true
    }

    fun toggleCell(rowIndex: Int, colIndex: Int) {
        _maze.value?.let { currentMaze ->
            val newMaze = currentMaze.map { it.clone() }.toTypedArray()

            if (newMaze[rowIndex][colIndex] == 1) {
                _pathClicks.value = (_pathClicks.value ?: 0) + 1
            }

            newMaze[rowIndex][colIndex] = when (newMaze[rowIndex][colIndex]) {
                1 -> 2
                0 -> -1
                else -> newMaze[rowIndex][colIndex]
            }

            _maze.value = newMaze
        }
    }

    private fun generateMaze1Data(): Array<Array<Int>> {
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

    private fun generateMaze2Data(): Array<Array<Int>> {
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
}
