package com.example.mycalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // operation buttons
        val operationSymbols = setOf('+', '-', '×', '÷', '.')

        binding.btnC.setOnClickListener { binding.expression.text = "" }

        binding.btnAdd.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            if (currText.isNotEmpty()) {
                val lastChar = currText.last()
                if (lastChar !in operationSymbols) {
                    binding.expression.append(" + ")
                }
            }
        }

        binding.btnSubtract.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            if (currText.isNotEmpty()) {
                val lastChar = currText.last()
                if (lastChar !in operationSymbols) {
                    binding.expression.append(" - ")
                }
            }
        }

        binding.btnMultiply.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            if (currText.isNotEmpty()) {
                val lastChar = currText.last()
                if (lastChar !in operationSymbols) {
                    binding.expression.append(" × ")
                }
            }
        }

        binding.btnDivide.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            if (currText.isNotEmpty()) {
                val lastChar = currText.last()
                if (lastChar !in operationSymbols) {
                    binding.expression.append(" ÷ ")
                }
            }
        }

        binding.btnDot.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            if (currText.isNotEmpty()) {
                val lastChar = currText.last()
                if (lastChar !in operationSymbols) {
                    binding.expression.append(".")
                }
            }
        }


        // number buttons
        binding.btn0.setOnClickListener { binding.expression.append("0") }
        binding.btn1.setOnClickListener { binding.expression.append("1") }
        binding.btn2.setOnClickListener { binding.expression.append("2") }
        binding.btn3.setOnClickListener { binding.expression.append("3") }
        binding.btn4.setOnClickListener { binding.expression.append("4") }
        binding.btn5.setOnClickListener { binding.expression.append("5") }
        binding.btn6.setOnClickListener { binding.expression.append("6") }
        binding.btn7.setOnClickListener { binding.expression.append("7") }
        binding.btn8.setOnClickListener { binding.expression.append("8") }
        binding.btn9.setOnClickListener { binding.expression.append("9") }

    }
}