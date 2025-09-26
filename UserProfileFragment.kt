package com.example.lab8_working

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class UserProfileFragment : Fragment() {

    data class UserProfile(
        val name: String,
        val email: String,
        val phone: String,
        val age: Int,
        val location: String,
        val joinDate: String,
        val preferences: List<String>
    )

    private val userProfile = UserProfile(
        name = "G. Vignesh",
        email = "g.vignesh@example.com",
        phone = "+91 9876543210",
        age = 22,
        location = "Chennai, India",
        joinDate = "January 2025",
        preferences = listOf("Budget Tracking", "Savings Goals", "Expense Analysis", "Financial Reports")
    )

    // Fragment Lifecycle Methods with Toast Messages
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(context, "🔗 UserProfileFragment: onAttach() - Fragment attached to activity", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(requireContext(), "🆕 UserProfileFragment: onCreate() - Fragment created", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireContext(), "🏗️ UserProfileFragment: onCreateView() - Fragment view being created", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "✅ UserProfileFragment: onViewCreated() - Fragment view created successfully", Toast.LENGTH_SHORT).show()

        setupUserProfile(view)
        setupButtons(view)
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(requireContext(), "▶️ UserProfileFragment: onStart() - Fragment started", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "🔄 UserProfileFragment: onResume() - Fragment resumed and active", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "⏸️ UserProfileFragment: onPause() - Fragment paused", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "⏹️ UserProfileFragment: onStop() - Fragment stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        Toast.makeText(requireContext(), "🗑️ UserProfileFragment: onDestroyView() - Fragment view destroyed", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Toast.makeText(requireContext(), "💥 UserProfileFragment: onDestroy() - Fragment destroyed", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onDetach() {
        activity?.let {
            Toast.makeText(it, "🔌 UserProfileFragment: onDetach() - Fragment detached from activity", Toast.LENGTH_SHORT).show()
        }
        super.onDetach()
    }

    private fun setupUserProfile(view: View) {
        view.findViewById<TextView>(R.id.tvUserName).text = userProfile.name
        view.findViewById<TextView>(R.id.tvUserEmail).text = userProfile.email
        view.findViewById<TextView>(R.id.tvUserPhone).text = userProfile.phone
        view.findViewById<TextView>(R.id.tvUserAge).text = "${userProfile.age} years old"
        view.findViewById<TextView>(R.id.tvUserLocation).text = userProfile.location
        view.findViewById<TextView>(R.id.tvJoinDate).text = "Member since ${userProfile.joinDate}"

        val preferencesText = userProfile.preferences.joinToString(", ")
        view.findViewById<TextView>(R.id.tvPreferences).text = preferencesText
    }

    private fun setupButtons(view: View) {
        view.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            editProfile(view)
        }

        view.findViewById<Button>(R.id.btnUpdatePreferences).setOnClickListener {
            updatePreferences()
        }

        view.findViewById<Button>(R.id.btnShowStats).setOnClickListener {
            showUserStats()
        }

        view.findViewById<Button>(R.id.btnResetProfile).setOnClickListener {
            resetProfile(view)
        }
    }

    private fun editProfile(view: View) {
        val nameInput = view.findViewById<EditText>(R.id.etEditName)
        val locationInput = view.findViewById<EditText>(R.id.etEditLocation)

        val newName = nameInput.text.toString().trim()
        val newLocation = locationInput.text.toString().trim()

        if (newName.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvUserName).text = newName
            Toast.makeText(requireContext(), "✅ Name updated to: $newName", Toast.LENGTH_SHORT).show()
        }

        if (newLocation.isNotEmpty()) {
            view.findViewById<TextView>(R.id.tvUserLocation).text = newLocation
            Toast.makeText(requireContext(), "✅ Location updated to: $newLocation", Toast.LENGTH_SHORT).show()
        }

        if (newName.isEmpty() && newLocation.isEmpty()) {
            Toast.makeText(requireContext(), "⚠️ Please enter name or location to update!", Toast.LENGTH_SHORT).show()
        } else {
            nameInput.text.clear()
            locationInput.text.clear()
        }
    }

    private fun updatePreferences() {
        val newPreferences = listOf("Advanced Analytics", "Investment Tracking", "Tax Planning", "Retirement Goals")
        val preferencesText = newPreferences.joinToString(", ")
        view?.findViewById<TextView>(R.id.tvPreferences)?.text = preferencesText
        Toast.makeText(requireContext(), "🔄 Preferences updated successfully!", Toast.LENGTH_SHORT).show()
    }

    private fun showUserStats() {
        val stats = """
            📊 User Statistics:
            
            👤 Profile Views: 156
            📅 Days Active: 45
            💰 Total Savings Tracked: $12,450
            📈 Budget Goals Set: 8
            🎯 Goals Achieved: 6
            ⭐ App Rating Given: 5/5
        """.trimIndent()

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("User Statistics")
            .setMessage(stats)
            .setPositiveButton("Cool! 😎") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(requireContext(), "📊 Stats viewed!", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun resetProfile(view: View) {
        view.findViewById<TextView>(R.id.tvUserName).text = userProfile.name
        view.findViewById<TextView>(R.id.tvUserLocation).text = userProfile.location

        val originalPreferences = userProfile.preferences.joinToString(", ")
        view.findViewById<TextView>(R.id.tvPreferences).text = originalPreferences

        view.findViewById<EditText>(R.id.etEditName).text.clear()
        view.findViewById<EditText>(R.id.etEditLocation).text.clear()

        Toast.makeText(requireContext(), "🔄 Profile reset to original values!", Toast.LENGTH_LONG).show()
    }
}