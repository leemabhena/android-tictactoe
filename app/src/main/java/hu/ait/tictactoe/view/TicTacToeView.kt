package hu.ait.tictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoe.MainActivity
import hu.ait.tictactoe.model.TicTacToeModel

class TicTacToeView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBackground: Paint
    lateinit var paintLine: Paint
    lateinit var paintText: Paint

    init {
        paintBackground = Paint()
        paintBackground.setColor(Color.BLACK)
        paintBackground.style = Paint.Style.FILL

        // Initialize line paint
        paintLine = Paint()
        paintLine.setColor(Color.WHITE)
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        // initialize text paint
        paintText = Paint()
        paintText.setColor(Color.GREEN)
        paintText.textSize = 100f

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawGameArea(canvas)

        drawPlayers(canvas)
    }


    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    // change color
                    paintLine.setColor(Color.CYAN)
                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                    // change color back to white
                    paintLine.setColor(Color.WHITE)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintLine)

                    canvas.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintLine)
                }
            }
        }
    }

    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine)
        canvas.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine)

        // two vertical lines
        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())
                TicTacToeModel.changeNextPlayer()

                var nextPlayer = "0"
                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
                    nextPlayer = "X"
                }
                (context as MainActivity).showNextPlayer("Next player is $nextPlayer")

                // Reset and start the clock for the next player
                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CIRCLE) {
                    // Start the circle chronometer and pause the prev player time
                    (context as MainActivity).startCircleChronometer()
                    (context as MainActivity).pauseCrossChronometer()
                } else {
                    (context as MainActivity).startCrossChronometer()
                    (context as MainActivity).pauseCircleChronometer()
                }

                if (TicTacToeModel.getWinner() == TicTacToeModel.CIRCLE) {
                    (context as MainActivity).showMessage("CIRCLE won!")
                    (context as MainActivity).pauseCrossChronometer()
                    (context as MainActivity).pauseCircleChronometer()

                } else if (TicTacToeModel.getWinner() == TicTacToeModel.CROSS) {
                    (context as MainActivity).showMessage("CROSS won!")
                    (context as MainActivity).pauseCrossChronometer()
                    (context as MainActivity).pauseCircleChronometer()
                }
                if ( TicTacToeModel.isGameOver()) {
                    if (TicTacToeModel.getWinner() == TicTacToeModel.EMPTY)
                        (context as MainActivity).showMessage("There was a tie!")
                        (context as MainActivity).pauseCrossChronometer()
                        (context as MainActivity).pauseCircleChronometer()

                }

                invalidate()
            }
        }

        return true
    }

    fun resetGame() {
        TicTacToeModel.resetModel()
        invalidate()
    }


}