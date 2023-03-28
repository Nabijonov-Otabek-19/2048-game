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
    private var score = 0

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

    fun getScore(): Int = score

    private fun addNewElement() {
        val emptyList = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }

        if (emptyList.isEmpty()) {

        } else {
            val randomPos = Random.nextInt(0, emptyList.size)
            matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
        }
    }

    fun moveToLeft() {
        /*
        0 0 0 0   -> 0 0 0 0
        2 0 0 0   -> 2 0 0 0
        2 0 2 0   -> 4 0 0 0
        2 2 2 0   -> 4 2 0 0
        0 2 2 4   -> 4 4 0 0
        0 2 2 4   -> 8 0 0 0   xato
        2 2 2 2   -> 4 4 0 0
        2 4 2 2   -> 2 4 4 0
        * */

        // 0 2 2 2   -> 4 4 0 0
        // amounts = [] -> [2]
        // amounts = [2] -> [4]
        // amounts = [4] -> [4 , 2]
        // 0 0 0 0  -> 4 2 0 0
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
        // bosh joy yoq bolsa oyin tugashi kk
        addNewElement()
    }

    fun moveToRight() {
        /*
        0 0 0 0   -> 0 0 0 0
        2 0 0 0   -> 0 0 0 2
        2 0 2 0   -> 0 0 0 4
        2 2 2 0   -> 0 0 2 4
        0 2 2 4   -> 0 0 4 4
        0 2 2 4   -> 0 0 0 8   xato
        2 2 2 2   -> 0 0 4 4
        2 4 2 2   -> 0 2 4 4
        * */

        // 0 2 2 2   -> 4 4 0 0
        // amounts = [] -> [2]
        // amounts = [2] -> [4]
        // amounts = [4] -> [4 , 2]
        // 0 0 0 0  -> 4 2 0 0
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
        // bosh joy yoq bolsa oyin tugashi kk
        addNewElement()
    }

    fun moveToUp() {
        /*
        0 0 0 0   -> 0 0 0 0
        2 0 0 0   -> 2 0 0 0
        2 0 2 0   -> 4 0 0 0
        2 2 2 0   -> 4 2 0 0
        0 2 2 4   -> 4 4 0 0
        0 2 2 4   -> 8 0 0 0   xato
        2 2 2 2   -> 4 4 0 0
        2 4 2 2   -> 2 4 4 0
        * */

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
        // bosh joy yoq bolsa oyin tugashi kk
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