package com.perrigogames.simplecalculator

import android.text.method.TextKeyListener.clear

class MathModule {

    // The digits currently displayed on the screen
    val digits = mutableListOf<Int>()
    // Shorthand
    val digitCount: Int
        get() = digits.size

    // The back stack of numbers to operate on
    private val numberStack = mutableListOf<Long>()
    val stackSize: Int
        get() = numberStack.size

    // Indicates the current operator
    var currentOperator: Operator? = null
        set(v) {
            if (lastKey == BUTTON_DIGIT && field != null && v != null) {
                evaluate()
            } else if (lastKey == BUTTON_EVALUATE) {
                numberStack.clear()
            }
            field = v
            lastKey = BUTTON_OPERATOR
        }

    // Indicates whether the last key used was an operator. A number following an
    // operator causes the previous number to be pushed to the stack.
    private var lastKey = BUTTON_DIGIT

    fun digitSelected(digit: Int) {
        if (digit < 0 || digit > 9) {
            throw IllegalArgumentException("Supplied digit %d is not a single digit".format(digit))
        }

        when (lastKey) {
            BUTTON_OPERATOR -> pushCurrentToBackstack()
            BUTTON_EVALUATE -> clear()
        }

        if (digitCount >= DIGIT_LIMIT) {
            return
        } else if (digitCount == 0 && digit == 0) {
            return
        }
        digits.add(digit)
        lastKey = BUTTON_DIGIT
    }

    // Enters a multi-digit number into the math module by selecting each digit
    fun enterNumber(number: Long, useButtons: Boolean = true) {
        val negative = number < 0 // this just gets ignored currently
        var temp = Math.abs(number)
        val tempDigits = mutableListOf<Int>()
        while (temp > 0) {
            tempDigits.add(0, (temp % 10).toInt())
            temp /= 10
        }
        if (useButtons) {
            while (tempDigits.isNotEmpty()) {
                digitSelected(tempDigits.removeAt(0))
            }
        } else {
            digits.clear()
            digits.addAll(tempDigits)
        }
    }

    fun operatorSelected(op: Operator) {
        currentOperator = op
    }

    fun evaluate() {
        if (currentOperator == null) {
            return
        }
        pushCurrentToBackstack(if (stackSize == 0) 2 else 1)
        enterNumber(currentOperator!!.evaluate(
                numberStack[stackSize - 2], numberStack.removeAt(stackSize - 1)),
                false)
        lastKey = BUTTON_EVALUATE
    }

    fun clear() {
        digits.clear()
        numberStack.clear()
        currentOperator = null
        lastKey = BUTTON_DIGIT
    }

    fun clearDisplay() {
        digits.clear()
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

    // This is me being exceedingly lazy
    fun toLong(): Long = toString().toLong()

    private fun pushCurrentToBackstack(times: Int = 1) {
        for (i in 0 until times) {
            numberStack.add(toString().toLong())
        }
        digits.clear()
    }

    companion object {
        const val DIGIT_LIMIT = 8

        const val BUTTON_DIGIT = 0
        const val BUTTON_OPERATOR = 1
        const val BUTTON_EVALUATE = 2
    }
}