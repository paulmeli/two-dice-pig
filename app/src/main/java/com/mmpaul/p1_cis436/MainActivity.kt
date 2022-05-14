package com.mmpaul.p1_cis436

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mmpaul.p1_cis436.databinding.ActivityMainBinding // Import view binding class

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // For view binding

    var p1TotalValue = 0
    var p2TotalValue = 0
    val playerTurn = arrayOf("P1", "P2")
    var playerIndex = 0
    var turnTotalValue = 0
    var randomNum1 = 0
    var randomNum2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inflate view binding class
        setContentView(binding.root) // Access root view and use as content view for layout
    }

    // Generate two random number 1-6, and set dice images accordingly
    fun randomDiceRolls(view : View) {

        for (index in 1 .. 2) {
            val randomNum = (1..6).random() // Generate random number 1-6

            // Assign random number to corresponding variables
            if (index == 1) randomNum1 = randomNum else randomNum2 = randomNum

            when (randomNum) { // Handle different random numbers by setting appropriate dice images
                1 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_1) else binding.secondDice.setImageResource(R.drawable.dice_1)
                2 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_2) else binding.secondDice.setImageResource(R.drawable.dice_2)
                3 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_3) else binding.secondDice.setImageResource(R.drawable.dice_3)
                4 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_4) else binding.secondDice.setImageResource(R.drawable.dice_4)
                5 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_5) else binding.secondDice.setImageResource(R.drawable.dice_5)
                6 -> if (index == 1) binding.firstDice.setImageResource(R.drawable.dice_6) else binding.secondDice.setImageResource(R.drawable.dice_6)
            } // end of when(randomNum)
        } // end of for-loop

        // Enable "hold" button after double roll (not including double ones) is done
        binding.holdButton.isEnabled = true
        binding.holdButton.isClickable = true
        // Set "hold" button text color back to white to indicate enabled
        binding.holdButton.setTextColor(Color.parseColor("#faf8f8"))

        // Update player score, current player, and turn total based on dice values
        handleRollValues()

        binding.holdButton.setOnClickListener { // Event handler for when "hold" button clicked

            if (playerTurn[playerIndex] == "P1") {
                p1TotalValue += turnTotalValue // Add turn total to player's score
                binding.player1Value.text = p1TotalValue.toString()

                turnTotalValue = 0 // Set turn total to 0
                binding.turnTotalVal.text = turnTotalValue.toString()

                if (p1TotalValue >= 50) { // If player's score >= 50
                    // Display winning message
                    val toast = Toast.makeText(this, "Player 1 won!", Toast.LENGTH_SHORT)
                    toast.show()

                    // Reset player's scores (reset game)
                    p1TotalValue = 0
                    binding.player1Value.text = p1TotalValue.toString()
                    p2TotalValue = 0
                    binding.player2Value.text = p2TotalValue.toString()
                }
            } else {
                p2TotalValue += turnTotalValue
                binding.player2Value.text = p2TotalValue.toString()

                turnTotalValue = 0
                binding.turnTotalVal.text = turnTotalValue.toString()

                if (p2TotalValue >= 50) { // Reset game?
                    val toast = Toast.makeText(this, "Player 2 won!", Toast.LENGTH_LONG)
                    toast.show()

                    p1TotalValue = 0
                    binding.player1Value.text = p1TotalValue.toString()
                    p2TotalValue = 0
                    binding.player2Value.text = p2TotalValue.toString()
                }
            }

            playerIndex = (playerIndex + 1) % 2 // Go to next player's turn
            binding.currPlayerValue.text = playerTurn[playerIndex]
        } // end of holdButton.setOnClickListener

    } // end of randomDiceRolls

    fun handleRollValues() {

        if (randomNum1 == 1 && randomNum2 == 1) { // If roll double ones
            if (playerTurn[playerIndex] == "P1") { // Set player's score to 0
                p1TotalValue = 0
                binding.player1Value.text = p1TotalValue.toString()
            }
            else {
                p2TotalValue = 0
                binding.player2Value.text = p2TotalValue.toString()
            }

            turnTotalValue = 0 // Set turn total to 0
            binding.turnTotalVal.text = turnTotalValue.toString()

            playerIndex = (playerIndex + 1) % 2 // Change to the next player's turn
            binding.currPlayerValue.text = playerTurn[playerIndex]
        }
        else if (randomNum1 == 1 || randomNum2 == 1) { // If roll a single one
            turnTotalValue = 0 // Set turn total to 0
            binding.turnTotalVal.text = turnTotalValue.toString()

            playerIndex = (playerIndex + 1) % 2 // Change to next player's turn
            binding.currPlayerValue.text = playerTurn[playerIndex]
        }
        else if (randomNum1 == randomNum2) { // If roll double other than two ones
            turnTotalValue += randomNum1 + randomNum2 // Add sum of roll to turn total
            binding.turnTotalVal.text = turnTotalValue.toString()

            // Force player to roll again by disabling "hold" button
            binding.holdButton.isEnabled = false
            binding.holdButton.isClickable = false
            // Change text color to gray to indicate disabled
            binding.holdButton.setTextColor(Color.parseColor("#918a8a"))
        }
        else { // If roll is not double and does not include ones
            turnTotalValue += randomNum1 + randomNum2 // Add sum of roll to turn total
            binding.turnTotalVal.text = turnTotalValue.toString()
        }

    } // end of handleRollValues

} // end of Main Activity class