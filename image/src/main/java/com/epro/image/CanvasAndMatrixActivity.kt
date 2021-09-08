package com.epro.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_canvas_and_matrix.*

class CanvasAndMatrixActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas_and_matrix)

        tv_canvas_move.setOnClickListener {
            canvas_view_matrix.setIsMatrixMove(false)
        }
        tv_matrix_move.setOnClickListener {
            canvas_view_matrix.setIsMatrixMove(true)
        }
    }
}