package com.example.mycalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
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
            val expressionInInfix = binding.expression.text.toString()
            if (expressionInInfix.isNotEmpty()) {
                try {
                    val expressionInPostfix = convertToPostfix(expressionInInfix)
                    val computationResult = calculateByPostfix(expressionInPostfix)
                    binding.expression.text = computationResult.toString()
                } catch (e: ArithmeticException) {
                    binding.expression.text = "${e.message}"
                } catch (e: Exception) {
                    binding.expression.text = "Error: Invalid expression"
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

    private fun operation(leftOperand: String, operator: String, rightOperand: String): Double {
        val left = leftOperand.toDouble()
        val right = rightOperand.toDouble()

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
        val stack = Stack<String>()

        for (token in tokens) {
            if (token in operators) {
                val rightOperand = stack.pop()
                val leftOperand = stack.pop()
                val result = operation(leftOperand, token, rightOperand)
                stack.push(result.toString())
            } else {
                stack.push(token)
            }
        }

        return stack.pop().toDouble()
    }

}