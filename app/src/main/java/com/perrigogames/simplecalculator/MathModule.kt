package com.perrigogames.simplecalculator

class MathModule {

    //
    private val digits = mutableListOf<Int>()
    //
    private var digitCount = 0
    //
    private val numberStack = mutableListOf<Long>()
    // Indicates the current operator
    private var currentOperator: Operator? = null
    // Tracks the position of the decimal point, indicating the index
//    private var decimalPosition = -1

    fun digitSelected(digit: Int) {
        if (digit < 0 || digit > 9) {
            throw IllegalArgumentException("Supplied digit %d is not a single digit".format(digit))
        } else if (digitCount >= DIGIT_LIMIT) {
            return
        } else if (digitCount == 0 && digit == 0) {
            return
        }
        digits.add(digit)
        digitCount++
    }

    fun operatorSelected(op: Operator) {
        currentOperator = op
    }

    fun evaluate() {

    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in 0..digitCount) {
            builder.append(digits[i])
        }
        return builder.toString()
    }

    companion object {
        const val DIGIT_LIMIT = 8
    }
}