package com.example.lawrence.calculatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorMode {
    None,Add,Subtract,Multiply
}
class MainActivity : AppCompatActivity() {
    var lastButtonWasMode = false
    var currentMode = CalculatorMode.None
    var labelString = ""
    var savedNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }

    fun setupCalculator() {
        val allButtons = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for(i in allButtons.indices) {
            allButtons[i].setOnClickListener { didPressNumber(i) }
        }
        buttonAdd.setOnClickListener { changeMode(CalculatorMode.Add) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorMode.Multiply) }
        buttonSubtract.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        buttonEqual.setOnClickListener { didPressEquals() }
        buttonClear.setOnClickListener { didPressClear() }
    }

    fun didPressEquals() {
        if(lastButtonWasMode) {
            return
        }
        val labelInt = labelString.toInt()

        when(currentMode) {

            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract  -> savedNum -= labelInt
            CalculatorMode.Multiply -> savedNum *= labelInt
            CalculatorMode.None -> return
        }

        currentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }

    fun didPressClear() {
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString = ""
        savedNum = 0
        textView.text = "0"
    }

    fun updateText() {

        if(labelString.length > 8){
            didPressClear()
            textView.text = "Too Big"
            return

        }

        val labelInt = labelString.toInt()
        labelString = labelInt.toString()

        if (currentMode == CalculatorMode.None) {
            savedNum = labelInt
        }
        val  df = DecimalFormat("#,###")
        textView.text = df.format(labelInt)
    }

    fun changeMode(mode:CalculatorMode) {

        if(savedNum == 0) {
            return
        }

        currentMode = mode
        lastButtonWasMode = true
    }

    fun didPressNumber(num:Int) {
        val strVal = num.toString()

        if(lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$strVal"
        updateText()
    }
}
