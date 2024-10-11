package com.example.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding
import java.util.Stack
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // operation buttons
        val operationSymbols = setOf('+', '-', '×', '÷', '.')

        binding.btnC.setOnClickListener {
            binding.expression.text = ""
            binding.result.text = ""
        }

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
            } else{
                binding.expression.append("-")
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


        binding.btnEqual.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            val lastChar = currText.last()
            if (currText.isNotEmpty() && lastChar !in operationSymbols) {
                val expressionInInfix = binding.expression.text.toString()
                try {
                    val expressionInPostfix = convertToPostfix(expressionInInfix)
                    val computationResult = calculateByPostfix(expressionInPostfix)
                    binding.result.text = "=${computationResult}"
                } catch (e: ArithmeticException) {
                    binding.result.text = "${e.message}"
                } catch (e: Exception) {
                    binding.result.text = "Invalid expression"
                }
            }
        }

        // number buttons
        binding.btn0.setOnClickListener {
            val currText = binding.expression.text.toString().replace(" ", "")
            val lastChar = currText.last()
            if (lastChar != '0'){
                binding.expression.append("0")
            }
        }
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

    private val operators = mapOf(
        "×" to 2,
        "÷" to 2,
        "+" to 1,
        "-" to 1
    )

    private  fun convertToPostfix(expression: String): List<String> {
        val stack = Stack<String>()
        val result = mutableListOf<String>()
        val tokens = expression.split(" ")

        for (token in tokens) {
            if (token !in operators) {
                result.add(token)
            } else {
                while (stack.isNotEmpty()) {
                    val prev = stack.peek()
                    if (operators[prev]!! >= operators[token]!!) {
                        result.add(stack.pop())
                    } else {
                        break
                    }
                }
                stack.push(token)
            }
        }

        while (stack.isNotEmpty()) {
            result.add(stack.pop())
        }

        return result
    }

    private fun operation(left: Double, operator: String, right: Double): Double {
        return when (operator) {
            "+" -> left + right
            "-" -> left - right
            "×" -> left * right
            "÷" -> {
                if (right == 0.0) {
                    throw ArithmeticException("Division by zero")
                } else {
                    left / right
                }
            }
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }

    private fun calculateByPostfix(tokens: List<String>): Double {
        val stack = Stack<Double>()

        for (token in tokens) {
            if (token in operators) {
                val rightOperand = stack.pop()
                val leftOperand = stack.pop()
                val result = operation(leftOperand, token, rightOperand)
                stack.push(result)
            } else {
                stack.push(token.toDouble())
            }
        }
        val finalResult = stack.pop()
        return String.format(Locale.US, "%.8f", finalResult).toDouble()
    }

}