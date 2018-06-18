package com.perrigogames.simplecalculator

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Test class for [MathModule] functions
 */
class MathModuleTest {

    val math = MathModule()

    @Test
    fun inputs_integers() {
        val target = StringBuilder()
        for (i in 1..9) {
            if (i <= MathModule.DIGIT_LIMIT) {
                target.append(i)
            }
            math.digitSelected(i)
            assertEquals(target.toString(), math.toString())
        }
    }

    @Test
    fun inputs_operators() {
        math.enterNumber(12)
        assertModuleContents("12", null)
        math.operatorSelected(Operator.ADD)
        assertModuleContents("12", Operator.ADD)
        math.enterNumber(34)
        assertModuleContents("34", Operator.ADD)
    }

    @Test
    fun arithmetic_all() {
        val performOperationTest = { in1: Long, in2: Long, op: Operator, expected: Long ->
            math.enterNumber(in1)
            math.operatorSelected(op)
            math.enterNumber(in2)
            math.evaluate()
            assertEquals(expected, math.toLong())
            math.clear()
        }
        performOperationTest(3, 6, Operator.ADD, 9)
        performOperationTest(5, 4, Operator.SUBTRACT, 1)
        performOperationTest(3, 3, Operator.MULTIPLY, 9)
        performOperationTest(8, 2, Operator.DIVIDE, 4)
        performOperationTest(9, 2, Operator.DIVIDE, 4)
        performOperationTest(4, 2, Operator.MODULO, 0)
        performOperationTest(9, 2, Operator.MODULO, 1)
        performOperationTest(5334, 4378, Operator.ADD, 9712)
        performOperationTest(65573, 25550, Operator.SUBTRACT, 40023)
        performOperationTest(121, 456, Operator.MULTIPLY, 55176)
        performOperationTest(3125, 25, Operator.DIVIDE, 125)
        performOperationTest(3139, 25, Operator.DIVIDE, 125)
        performOperationTest(3125, 25, Operator.MODULO, 0)
        performOperationTest(3139, 25, Operator.MODULO, 14)
    }

    @Test
    fun arithmetic_chain() {
        math.enterNumber(2500)
        math.operatorSelected(Operator.ADD)
        math.enterNumber(525)
        math.operatorSelected(Operator.SUBTRACT)
        math.enterNumber(1000)
        math.evaluate()
        assertEquals(math.toLong(), 2025)
    }

    // Asserts the math module is currently displaying the specified number and operator
    private fun assertModuleContents(display: String, operator: Operator?) {
        assertEquals(display, math.toString())
        assertEquals(operator, math.currentOperator)
    }
}
