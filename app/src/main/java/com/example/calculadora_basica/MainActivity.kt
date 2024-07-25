package com.example.calculadora_basica

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private var lastResult: Double? = null
    private lateinit var calc: TextView
    private lateinit var result: TextView
    private var operationFinished = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        calc = findViewById(R.id.calc)
        result = findViewById(R.id.result)


        if (savedInstanceState != null) {
            calc.text = savedInstanceState.getString("calc_text")
            result.text = savedInstanceState.getString("result_text")
            lastResult = savedInstanceState.getDouble("last_result")
        }

        val buttonZero = findViewById<Button>(R.id.zero)
        buttonZero.setOnClickListener { appendToCalc("0") }

        val buttonOne = findViewById<Button>(R.id.one)
        buttonOne.setOnClickListener { appendToCalc("1") }

        val buttonTwo = findViewById<Button>(R.id.two)
        buttonTwo.setOnClickListener { appendToCalc("2") }

        val buttonThree = findViewById<Button>(R.id.three)
        buttonThree.setOnClickListener { appendToCalc("3") }

        val buttonFour = findViewById<Button>(R.id.four)
        buttonFour.setOnClickListener { appendToCalc("4") }

        val buttonFive = findViewById<Button>(R.id.five)
        buttonFive.setOnClickListener { appendToCalc("5") }

        val buttonSix = findViewById<Button>(R.id.six)
        buttonSix.setOnClickListener { appendToCalc("6") }

        val buttonSeven = findViewById<Button>(R.id.seven)
        buttonSeven.setOnClickListener { appendToCalc("7") }

        val buttonEight = findViewById<Button>(R.id.eight)
        buttonEight.setOnClickListener { appendToCalc("8") }

        val buttonNine = findViewById<Button>(R.id.nine)
        buttonNine.setOnClickListener { appendToCalc("9") }

        val buttonOpenParenthesis = findViewById<Button>(R.id.openParenthesis)
        buttonOpenParenthesis.setOnClickListener { appendToCalc("(") }

        val buttonCloseParenthesis = findViewById<Button>(R.id.closeParenthesis)
        buttonCloseParenthesis.setOnClickListener { appendToCalc(")") }

        val buttonDiv = findViewById<Button>(R.id.div)
        buttonDiv.setOnClickListener { appendToCalc("/") }

        val buttonMulti = findViewById<Button>(R.id.mult)
        buttonMulti.setOnClickListener { appendToCalc("*") }

        val buttonSub = findViewById<Button>(R.id.sub)
        buttonSub.setOnClickListener { appendToCalc("-") }

        val buttonSum = findViewById<Button>(R.id.sum)
        buttonSum.setOnClickListener { appendToCalc("+") }

        val buttonDot = findViewById<Button>(R.id.dot)
        buttonDot.setOnClickListener { appendToCalc(".") }

        val buttonErase = findViewById<ImageButton>(R.id.erase)
        buttonErase.setOnClickListener { eraseLastCharacter() }

        val buttonAC = findViewById<Button>(R.id.ac)
        buttonAC.setOnClickListener { clearAll() }

        val buttonEqual = findViewById<Button>(R.id.equal)
        buttonEqual.setOnClickListener { evaluateExpression() }
    }

    private fun appendToCalc(value: String) {
        if (operationFinished) {
            calc.text = lastResult.toString()
            result.text = ""
            operationFinished = false
        }
        calc.append(value)
    }

    private fun eraseLastCharacter() {
        if (operationFinished) {
            clearAll()
        } else {
            calc.text = calc.text.dropLast(1)
        }
    }

    private fun clearAll() {
        calc.text = ""
        result.text = ""
        lastResult = null
        operationFinished = false
    }

    private fun evaluateExpression() {
        val expressionText = calc.text.toString()
        val modifiedExpressionText = if (expressionText.startsWith("+") ||
            expressionText.startsWith("-") ||
            expressionText.startsWith("*") ||
            expressionText.startsWith("/")) {
            "$lastResult$expressionText"
        } else {
            expressionText
        }

        try {
            val calcResult = Expression(modifiedExpressionText).calculate()
            if (calcResult.isNaN()) {
                result.text = "Operação Inválida"
            } else {
                lastResult = calcResult
                result.text = calcResult.toString()
                operationFinished = true
            }
        } catch (e: Exception) {
            result.text = "Erro!"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("calc_text", calc.text.toString())
        outState.putString("result_text", result.text.toString())
        outState.putDouble("last_result", lastResult ?: Double.NaN)
    }
}
