package com.example.travelquiz

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var answer1Button: Button
    private lateinit var answer2Button: Button
    private lateinit var answer3Button: Button
    private lateinit var answer4Button: Button
    private lateinit var nextButton: ImageButton

    private var currentQuestionIndex = 0
    private var score = 0
    private var isAnswered = false
    private var totalQuestions = 4

    private val allQuestions = arrayOf(
        R.string.question_1, R.string.question_2, R.string.question_3, R.string.question_4, R.string.question_5
    )
    private val allAnswers = arrayOf(
        arrayOf(R.string.q1_ans_1, R.string.q1_ans_2, R.string.q1_ans_3, R.string.q1_ans_4),
        arrayOf(R.string.q2_ans_1, R.string.q2_ans_2, R.string.q2_ans_3, R.string.q2_ans_4),
        arrayOf(R.string.q3_ans_1, R.string.q3_ans_2, R.string.q3_ans_3, R.string.q3_ans_4),
        arrayOf(R.string.q4_ans_1, R.string.q4_ans_2, R.string.q4_ans_3, R.string.q4_ans_4),
        arrayOf(R.string.q5_ans_1, R.string.q5_ans_2, R.string.q5_ans_3, R.string.q5_ans_4)
    )

    private var currentQuestions = mutableListOf<Int>()
    private var currentAnswers = mutableListOf<Array<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionTextView = findViewById(R.id.questionTextView)
        answer1Button = findViewById(R.id.answer1Button)
        answer2Button = findViewById(R.id.answer2Button)
        answer3Button = findViewById(R.id.answer3Button)
        answer4Button = findViewById(R.id.answer4Button)
        nextButton = findViewById(R.id.nextButton)

        val indices = allQuestions.indices.shuffled().take(totalQuestions)
        currentQuestions = indices.map { allQuestions[it] }.toMutableList()
        currentAnswers = indices.map { allAnswers[it] }.toMutableList()

        setQuestion()

        answer1Button.setOnClickListener { checkAnswer(1, answer1Button) }
        answer2Button.setOnClickListener { checkAnswer(2, answer2Button) }
        answer3Button.setOnClickListener { checkAnswer(3, answer3Button) }
        answer4Button.setOnClickListener { checkAnswer(4, answer4Button) }

        nextButton.setOnClickListener {
            if (isAnswered) {
                if (currentQuestionIndex == currentQuestions.size - 1) {
                    Toast.makeText(this, "Вікторина завершена! Ваш результат: $score з $totalQuestions", Toast.LENGTH_LONG).show()

                    currentQuestionIndex = 0
                    score = 0
                    val newIndices = allQuestions.indices.shuffled().take(totalQuestions)
                    currentQuestions = newIndices.map { allQuestions[it] }.toMutableList()
                    currentAnswers = newIndices.map { allAnswers[it] }.toMutableList()
                    setQuestion()
                } else {
                    currentQuestionIndex++
                    setQuestion()
                }
            } else {
                Toast.makeText(this, "Спочатку дайте відповідь!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setQuestion() {
        val question = currentQuestions[currentQuestionIndex]
        questionTextView.setText(question)

        val currentAns = currentAnswers[currentQuestionIndex]
        answer1Button.text = getString(currentAns[0])
        answer2Button.text = getString(currentAns[1])
        answer3Button.text = getString(currentAns[2])
        answer4Button.text = getString(currentAns[3])

        resetButtons()
        isAnswered = false
        nextButton.isEnabled = false
    }

    private fun checkAnswer(selectedOption: Int, selectedButton: Button) {
        if (isAnswered) return

        isAnswered = true
        nextButton.isEnabled = true

        val correctButton = answer1Button

        if (selectedOption == 1) {
            selectedButton.setBackgroundColor(Color.GREEN)
            selectedButton.setTextColor(Color.WHITE)
            score++
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
        } else {
            selectedButton.setBackgroundColor(Color.RED)
            selectedButton.setTextColor(Color.WHITE)
            correctButton.setBackgroundColor(Color.GREEN)
            correctButton.setTextColor(Color.WHITE)
            Toast.makeText(this, "Неправильно!", Toast.LENGTH_SHORT).show()
        }

        answer1Button.isEnabled = false
        answer2Button.isEnabled = false
        answer3Button.isEnabled = false
        answer4Button.isEnabled = false
    }

    private fun resetButtons() {
        answer1Button.setBackgroundColor(Color.parseColor("#6200EE"))
        answer1Button.setTextColor(Color.WHITE)
        answer2Button.setBackgroundColor(Color.parseColor("#6200EE"))
        answer2Button.setTextColor(Color.WHITE)
        answer3Button.setBackgroundColor(Color.parseColor("#6200EE"))
        answer3Button.setTextColor(Color.WHITE)
        answer4Button.setBackgroundColor(Color.parseColor("#6200EE"))
        answer4Button.setTextColor(Color.WHITE)

        answer1Button.isEnabled = true
        answer2Button.isEnabled = true
        answer3Button.isEnabled = true
        answer4Button.isEnabled = true
    }
}