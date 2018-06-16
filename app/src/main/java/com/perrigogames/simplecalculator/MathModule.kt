package com.perrigogames.simplecalculator

class MathModule {

    // The digits currently displayed on the screen
    val digits = mutableListOf<Int>()
    // Shorthand
    val digitCount: Int
        get() = digits.size

    // The back stack of numbers to operate on
    private val numberStack = mutableListOf<Long>()

    // Indicates the current operator
    var currentOperator: Operator? = null
        set(v) {
            if (field != null && v != null) {
                evaluate()
            }
            field = v
            lastKeyOperator = true
        }

    // Indicates whether the last key used was an operator. A number following an
    // operator causes the previous number to be pushed to the stack.
    private var lastKeyOperator = false

    fun digitSelected(digit: Int) {
        if (digit < 0 || digit > 9) {
            throw IllegalArgumentException("Supplied digit %d is not a single digit".format(digit))
        }

        if (lastKeyOperator) {
            pushCurrentToBackstack()
        }

        if (digitCount >= DIGIT_LIMIT) {
            return
        } else if (digitCount == 0 && digit == 0) {
            return
        }
        digits.add(digit)
        lastKeyOperator = false
    }

    // Enters a multi-digit number into the math module by selecting each digit
    fun enterNumber(number: Long) {
        val negative = number < 0 // this just gets ignored currently
        var temp = Math.abs(number)
        val digits = mutableListOf<Int>()
        while (temp > 0) {
            digits.add(0, (temp % 10).toInt())
            temp /= 10
        }
        while(digits.isNotEmpty()) {
            digitSelected(digits.removeAt(0))
        }
    }

    fun operatorSelected(op: Operator) {
        currentOperator = op
    }

    fun evaluate() {
        pushCurrentToBackstack()
        enterNumber(currentOperator?.evaluate(numberStack[numberStack.size - 2], numberStack[numberStack.size - 1])
                ?: numberStack[numberStack.size - 1])
        currentOperator = null
        lastKeyOperator = false
    }

    fun clear() {
        digits.clear()
        numberStack.clear()
        currentOperator = null
        lastKeyOperator = false
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in 0 until digits.size) {
            builder.append(digits[i])
        }
        if (digits.size == 0) {
            builder.append(0)
        }
        return builder.toString()
    }

    fun toLong(): Long = toString().toLong()

    private fun pushCurrentToBackstack() {
        numberStack.add(toString().toLong())
        digits.clear()
    }

    companion object {
        const val DIGIT_LIMIT = 8
    }
}