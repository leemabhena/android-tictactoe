package hu.ait.tictactoe.model

object TicTacToeModel {

    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2

    private var nextPlayer = CIRCLE.toShort()

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY)
    )

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CIRCLE
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short) {
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    fun getWinner(): Short {
        if (rows(CIRCLE) || columns(CIRCLE) || diagonals(CIRCLE)) {
            return CIRCLE
        } else if (rows(CROSS) || columns(CROSS) || diagonals(CROSS)) {
            return CROSS
        }
        return EMPTY
    }

    private fun rows(player: Short): Boolean {
        if (model[0][0] == player && model[0][1] == player && model[0][2] == player) {
            return true
        } else if (model[1][0] == player && model[1][1] == player && model[1][2] == player) {
            return true
        } else if (model[2][0] == player && model[2][1] == player && model[2][2] == player) {
            return true
        }
        return false
    }

    private fun columns(player: Short): Boolean {
        if (model[0][0] == player && model[1][0] == player && model[2][0] == player) {
            return true
        } else if (model[0][1] == player && model[1][1] == player && model[2][1] == player) {
            return true
        } else if (model[0][2] == player && model[1][2] == player && model[2][2] == player) {
            return true
        }
        return false
    }

    private fun diagonals(player: Short): Boolean {
        if (model[0][0] == player && model[1][1] == player && model[2][2] == player) {
            return true
        } else if (model[2][0] == player && model[1][1] == player && model[0][2] == player) {
            return true
        }
        return false
    }

    fun isGameOver(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (model[i][j] == EMPTY) return false
            }
        }
        return true
    }

}
