package com.perrigogames.simplecalculator

enum class Operator {
    ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO;

    fun evaluate(op1: Long, op2: Long): Long {
        return when(this) {
            ADD -> op1 + op2
            SUBTRACT -> op1 - op2
            MULTIPLY -> op1 * op2
            DIVIDE -> op1 / op2
            MODULO -> op1 % op2
        }
    }
}