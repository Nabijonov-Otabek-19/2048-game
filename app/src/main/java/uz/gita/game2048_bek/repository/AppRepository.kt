package uz.gita.game2048_bek.repository

import kotlin.random.Random

class AppRepository private constructor() {
    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized))
                instance = AppRepository()
            return instance
        }
    }

    private val NEW_ELEMENT = 2
    private var emptyList = ArrayList<Pair<Int, Int>>()

    private var listener: (() -> Unit)? = null

    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    val matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addNewElement()
        addNewElement()
    }

    var score: Int = 0

    fun resetGame() {
        emptyList.clear()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                matrix[i][j] = 0
            }
        }
        score = 0
        addNewElement()
        addNewElement()
    }

    private fun addNewElement() {
        emptyList = ArrayList()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }

        if (emptyList.isEmpty()) {
            listener?.invoke()
        } else {
            val randomPos = Random.nextInt(0, emptyList.size)
            matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
        }
    }

    fun moveToLeft() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        score += amounts.last()
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                matrix[i][k] = amounts[k]
            }
        }
        addNewElement()
    }

    fun moveToRight() {
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        amounts[amounts.size - 1] = amounts.last() * 2
                        score += amounts.last()
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                matrix[i][matrix[i].size - k - 1] = amounts[k]
            }
        }
        addNewElement()
    }

    fun moveToUp() {
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        score += amount.last()
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amount.size) {
                matrix[j][i] = amount[j]
            }
        }
        addNewElement()
    }

    fun moveToDown() {
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        amount[amount.size - 1] = amount.last() * 2
                        score += amount.last()
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in amount.size - 1 downTo 0) {
                matrix[3 - j][i] = amount[j]
            }
        }
        addNewElement()
    }
}