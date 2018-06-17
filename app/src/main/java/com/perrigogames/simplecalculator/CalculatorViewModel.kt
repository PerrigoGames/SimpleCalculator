package com.perrigogames.simplecalculator
import android.databinding.ObservableBoolean
import android.view.View

class CalculatorViewModel {

    private val math = MathModule()

    val moduloChecked = ObservableBoolean(false)

    fun onButtonSelected(view: View) {
        when (view.id) {
            R.id.btn0 -> math.digitSelected(0)
            R.id.btn1 -> math.digitSelected(1)
            R.id.btn2 -> math.digitSelected(2)
            R.id.btn3 -> math.digitSelected(3)
            R.id.btn4 -> math.digitSelected(4)
            R.id.btn5 -> math.digitSelected(5)
            R.id.btn6 -> math.digitSelected(6)
            R.id.btn7 -> math.digitSelected(7)
            R.id.btn8 -> math.digitSelected(8)
            R.id.btn9 -> math.digitSelected(9)
            R.id.btnEquals -> math.evaluate()
            R.id.btnDivide -> math.operatorSelected(Operator.DIVIDE)
            R.id.btnPlus -> math.operatorSelected(Operator.ADD)
            R.id.btnMultiply -> math.operatorSelected(Operator.MULTIPLY)
            R.id.btnMinus -> math.operatorSelected(Operator.SUBTRACT)
        }
    }
}