package com.example.lab8_working
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var btnBudget: Button
    private lateinit var btnProfile: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize navigation buttons
        btnBudget = findViewById(R.id.btnBudget)
        btnProfile = findViewById(R.id.btnProfile)

        // Set up navigation button listeners
        btnBudget.setOnClickListener {
            switchToFragment("budget")
        }
        
        btnProfile.setOnClickListener {
            switchToFragment("profile")
        }

        // Load the budget fragment initially if no saved instance state
        if (savedInstanceState == null) {
            switchToFragment("budget")
        }
    }
    
    private fun switchToFragment(fragmentType: String) {
        val fragment = when (fragmentType) {
            "budget" -> {
                btnBudget.isEnabled = false
                btnProfile.isEnabled = true
                BudgetFragment()
            }
            "profile" -> {
                btnBudget.isEnabled = true
                btnProfile.isEnabled = false
                UserProfileFragment()
            }
            else -> BudgetFragment()
        }
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
    
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            // Update button states based on current fragment
            val currentFragment = supportFragmentManager.fragments.lastOrNull()
            when (currentFragment) {
                is BudgetFragment -> {
                    btnBudget.isEnabled = false
                    btnProfile.isEnabled = true
                }
                is UserProfileFragment -> {
                    btnBudget.isEnabled = true
                    btnProfile.isEnabled = false
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}