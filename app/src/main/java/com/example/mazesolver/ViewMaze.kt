package com.example.mazesolver

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun ViewMaze(maze: Array<Array<Int>>) {
    Column {
        maze.forEach { row ->
            Row {
                row.forEach { cell ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(if (cell == 0) colorResource(id = R.color.black) else colorResource(id = R.color.white))
                    )
                }
            }
        }
    }
}


@Composable
fun FlipMaze(maze: Array<Array<Int>>, onCellClick: (Int, Int) -> Unit) {
    Column {
        maze.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                when (cell) {
                                    1 -> colorResource(id = R.color.black)
                                    0 -> colorResource(id = R.color.black)
                                    -1 -> colorResource(id = R.color.red)
                                    2 -> colorResource(id = R.color.white)
                                    else -> colorResource(id = R.color.grey)
                                }
                            )

                            .clickable {
                                onCellClick(rowIndex, colIndex)
                            }
                    )
                }
            }
        }
    }
}


