package com.example.lab8_working

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlin.collections.average
import kotlin.collections.filter
import kotlin.collections.find
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.joinToString
import kotlin.collections.map
import kotlin.collections.maxByOrNull
import kotlin.collections.sumOf
import kotlin.text.equals
import kotlin.text.format
import kotlin.text.isEmpty
import kotlin.text.trim
import kotlin.text.trimIndent

class BudgetFragment : Fragment() {

    data class BudgetEntry(
        val name: String,
        val income: Double,
        val expenses: Double,
        val savingsGoal: Double,
        val monthYear: String
    ) {
        val savings: Double get() = income - expenses
        val savingsProgress: Double get() = if (savingsGoal > 0) (savings / savingsGoal) * 100 else 0.0
        val expenseRatio: Double get() = if (income > 0) (expenses / income) * 100 else 0.0
    }

    private val budgetData = listOf(
        BudgetEntry("Alice", 3000.0, 2200.0, 1000.0, "June 2025"),
        BudgetEntry("Bob", 4500.0, 3800.0, 800.0, "June 2025"),
        BudgetEntry("Carol", 2800.0, 2100.0, 600.0, "June 2025"),
        BudgetEntry("David", 5200.0, 4100.0, 1500.0, "June 2025"),
        BudgetEntry("Eva", 3800.0, 2900.0, 900.0, "June 2025"),
        BudgetEntry("Frank", 3200.0, 2800.0, 700.0, "June 2025")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserGreeting(view)
        setupButtons(view)
        showDataSummary(view)
    }

    private fun setupUserGreeting(view: View) {
        val userGreeting = view.findViewById<TextView>(R.id.tvUserGreeting)
        userGreeting.text = "Welcome, G.Vignesh! 👋"
    }

    private fun setupButtons(view: View) {
        view.findViewById<Button>(R.id.btnTotalSavings).setOnClickListener { showTotalSavings() }
        view.findViewById<Button>(R.id.btnAverageIncome).setOnClickListener { showAverageIncome() }
        view.findViewById<Button>(R.id.btnBestSaver).setOnClickListener { showBestSaver() }
        view.findViewById<Button>(R.id.btnWorstExpenses).setOnClickListener { showWorstSpender() }
        view.findViewById<Button>(R.id.btnGoalAchievers).setOnClickListener { showGoalAchievers() }
        view.findViewById<Button>(R.id.btnSearchUser).setOnClickListener { searchUser(view) }
    }

    private fun showDataSummary(view: View) {
        val summaryText = view.findViewById<TextView>(R.id.tvDataSummary)
        summaryText.text = "📊 Analyzing ${budgetData.size} users for ${budgetData.first().monthYear}"
    }

    private fun showDetailedMessage(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showTotalSavings() {
        val totalSavings = budgetData.sumOf { it.savings }
        val message = "💰 Total Savings: $${String.format("%.2f", totalSavings)}"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showAverageIncome() {
        val averageIncome = budgetData.map { it.income }.average()
        val message = "💼 Average Income: $${String.format("%.2f", averageIncome)}"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showBestSaver() {
        val bestSaver = budgetData.maxByOrNull { it.savingsProgress }
        if (bestSaver != null) {
            val shortMessage = "🏆 ${bestSaver.name} is the best saver!"
            val detailedMessage = """
                🏆 Best Saver: ${bestSaver.name}
                
                💰 Income: $${String.format("%.2f", bestSaver.income)}
                💸 Expenses: $${String.format("%.2f", bestSaver.expenses)}
                💵 Savings: $${String.format("%.2f", bestSaver.savings)}
                🎯 Goal: $${String.format("%.2f", bestSaver.savingsGoal)}
                📈 Progress: ${String.format("%.1f", bestSaver.savingsProgress)}%
            """.trimIndent()
            Toast.makeText(requireContext(), shortMessage, Toast.LENGTH_SHORT).show()
            showDetailedMessage("Best Saver Details", detailedMessage)
        }
    }

    private fun showWorstSpender() {
        val worstSpender = budgetData.maxByOrNull { it.expenseRatio }
        if (worstSpender != null) {
            val shortMessage = "⚠️ ${worstSpender.name} spends the most!"
            val detailedMessage = """
                ⚠️ Highest Spender: ${worstSpender.name}
                
                💰 Income: $${String.format("%.2f", worstSpender.income)}
                💸 Expenses: $${String.format("%.2f", worstSpender.expenses)}
                📊 Expense Ratio: ${String.format("%.1f", worstSpender.expenseRatio)}%
                💵 Remaining: $${String.format("%.2f", worstSpender.savings)}
            """.trimIndent()
            Toast.makeText(requireContext(), shortMessage, Toast.LENGTH_SHORT).show()
            showDetailedMessage("Highest Spender Details", detailedMessage)
        }
    }

    private fun showGoalAchievers() {
        val achievers = budgetData.filter { it.savingsProgress >= 100.0 }
        if (achievers.isNotEmpty()) {
            val shortMessage = "🎯 ${achievers.size} users reached their goals!"
            val detailedMessage = buildString {
                append("🎯 Goal Achievers (${achievers.size} users):\n\n")
                achievers.forEach { user ->
                    append("👤 ${user.name}\n")
                    append("   Progress: ${String.format("%.1f", user.savingsProgress)}%\n")
                    append("   Savings: $${String.format("%.2f", user.savings)}\n")
                    append("   Goal: $${String.format("%.2f", user.savingsGoal)}\n\n")
                }
            }
            Toast.makeText(requireContext(), shortMessage, Toast.LENGTH_SHORT).show()
            showDetailedMessage("Goal Achievers", detailedMessage)
        } else {
            Toast.makeText(requireContext(), "😔 No one reached their savings goal yet!", Toast.LENGTH_LONG).show()
        }
    }

    private fun searchUser(view: View) {
        val searchInput = view.findViewById<EditText>(R.id.etSearchName)
        val searchName = searchInput.text.toString().trim()
        if (searchName.isEmpty()) {
            Toast.makeText(requireContext(), "⚠️ Please enter a name to search!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = budgetData.find { it.name.equals(searchName, ignoreCase = true) }
        if (user != null) {
            val shortMessage = "✅ Found ${user.name}!"
            val detailedMessage = """
                👤 ${user.name}'s Budget Details
                
                💰 Monthly Income: $${String.format("%.2f", user.income)}
                💸 Monthly Expenses: $${String.format("%.2f", user.expenses)}
                💵 Monthly Savings: $${String.format("%.2f", user.savings)}
                🎯 Savings Goal: $${String.format("%.2f", user.savingsGoal)}
                📈 Goal Progress: ${String.format("%.1f", user.savingsProgress)}%
                📊 Expense Ratio: ${String.format("%.1f", user.expenseRatio)}%
                📅 Period: ${user.monthYear}
            """.trimIndent()
            Toast.makeText(requireContext(), shortMessage, Toast.LENGTH_SHORT).show()
            showDetailedMessage("User Details", detailedMessage)
        } else {
            val availableUsers = budgetData.joinToString(", ") { it.name }
            val message = "❌ User '$searchName' not found!\n\nAvailable users: $availableUsers"
            showDetailedMessage("Search Result", message)
        }
        searchInput.text.clear()
    }
}