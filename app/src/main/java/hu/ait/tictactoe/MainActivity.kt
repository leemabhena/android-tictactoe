package hu.ait.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import com.google.android.material.snackbar.Snackbar
import hu.ait.tictactoe.databinding.ActivityMainBinding
import hu.ait.tictactoe.view.TicTacToeView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var circleClock: Chronometer
    lateinit var crossClock: Chronometer

    // keep track of chronometer variables
    private var isCircleChronometerRunning = false
    private var circlePauseOffset: Long = 0

    private var isCrossChronometerRunning = false
    private var crossPauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clearBtn = binding.btnClear

        clearBtn.setOnClickListener {
            val tictoctoeView = binding.ticTacToeView
            tictoctoeView.resetGame()
            // stop circle time
            resetCircleChronometer()
            // stop cross time
            resetCrossChronometer()
        }

        circleClock = binding.circleChronometer
        crossClock = binding.crossChronometer


    }

    public fun showNextPlayer(msg: String) {
        binding.nextPlayer.text = msg
    }

    public fun showMessage(msg: String) {
//        binding.tvResult.text = msg
        // show a Toas.. or Snackbar..
        // Create and show the Snack bar using the root view from binding
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }


    // start and pause the circle chronometer
    fun startCircleChronometer() {
        if (!isCircleChronometerRunning) {
            circleClock.base = SystemClock.elapsedRealtime() - circlePauseOffset
            circleClock.start()
            isCircleChronometerRunning = true
        }
    }

    // Start the cross chronometer
    fun startCrossChronometer() {
        if (!isCrossChronometerRunning) {
            crossClock.base = SystemClock.elapsedRealtime() - crossPauseOffset
            crossClock.start()
            isCrossChronometerRunning = true
        }
    }

    // Pause the circle chronometer
    fun pauseCircleChronometer() {
        if (isCircleChronometerRunning) {
            circlePauseOffset = SystemClock.elapsedRealtime() - circleClock.base
            circleClock.stop()
            isCircleChronometerRunning = false
        }
    }

    // pause the cross chronometer
    fun pauseCrossChronometer() {
        if (isCrossChronometerRunning) {
            crossPauseOffset = SystemClock.elapsedRealtime() - crossClock.base
            crossClock.stop()
            isCrossChronometerRunning = false
        }
    }

    // reset the circle chronometer
    fun resetCircleChronometer() {
        circleClock.base = SystemClock.elapsedRealtime()
        circlePauseOffset = 0
        circleClock.stop()
        isCircleChronometerRunning = false
    }

    // reset the cross chronometer
    fun resetCrossChronometer() {
        crossClock.base = SystemClock.elapsedRealtime()
        crossPauseOffset = 0
        crossClock.stop()
        isCrossChronometerRunning = false
    }
}