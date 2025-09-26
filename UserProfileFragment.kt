package com.example.lab8_working

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class UserProfileFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(context, "🔗 UserProfileFragment: onAttach()", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(requireContext(), "🆕 UserProfileFragment: onCreate()", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireContext(), "📱 UserProfileFragment: onCreateView()", Toast.LENGTH_SHORT).show()
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "✅ UserProfileFragment: onViewCreated()", Toast.LENGTH_SHORT).show()

        setupUserProfile(view)
        setupInteractiveElements(view)
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(requireContext(), "▶️ UserProfileFragment: onStart()", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(requireContext(), "🟢 UserProfileFragment: onResume()", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(requireContext(), "⏸️ UserProfileFragment: onPause()", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "⏹️ UserProfileFragment: onStop()", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        Toast.makeText(requireContext(), "🗑️ UserProfileFragment: onDestroyView()", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
    }

    override fun onDestroy() {
        Toast.makeText(requireContext(), "💥 UserProfileFragment: onDestroy()", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    override fun onDetach() {
        Toast.makeText(requireContext(), "🔓 UserProfileFragment: onDetach()", Toast.LENGTH_SHORT).show()
        super.onDetach()
    }

    private fun setupUserProfile(view: View) {
        val profileName = view.findViewById<TextView>(R.id.tvProfileName)
        val profileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)
        val profilePhone = view.findViewById<TextView>(R.id.tvProfilePhone)
        
        profileName.text = "G.Vignesh"
        profileEmail.text = "g.vignesh@example.com"
        profilePhone.text = "+1 (555) 123-4567"
    }

    private fun setupInteractiveElements(view: View) {
        val editNameButton = view.findViewById<Button>(R.id.btnEditName)
        val saveButton = view.findViewById<Button>(R.id.btnSaveProfile)
        val nameInput = view.findViewById<EditText>(R.id.etNewName)

        editNameButton.setOnClickListener {
            if (nameInput.visibility == View.GONE) {
                nameInput.visibility = View.VISIBLE
                saveButton.visibility = View.VISIBLE
                editNameButton.text = "Cancel Edit"
                Toast.makeText(requireContext(), "✏️ Edit mode enabled", Toast.LENGTH_SHORT).show()
            } else {
                nameInput.visibility = View.GONE
                saveButton.visibility = View.GONE
                editNameButton.text = "✏️ Edit Name"
                nameInput.text.clear()
                Toast.makeText(requireContext(), "❌ Edit cancelled", Toast.LENGTH_SHORT).show()
            }
        }

        saveButton.setOnClickListener {
            val newName = nameInput.text.toString().trim()
            if (newName.isNotEmpty()) {
                val profileName = view.findViewById<TextView>(R.id.tvProfileName)
                profileName.text = newName
                nameInput.visibility = View.GONE
                saveButton.visibility = View.GONE
                editNameButton.text = "✏️ Edit Name"
                nameInput.text.clear()
                Toast.makeText(requireContext(), "💾 Profile updated: $newName", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "⚠️ Please enter a valid name", Toast.LENGTH_SHORT).show()
            }
        }

        val themeButton = view.findViewById<Button>(R.id.btnToggleTheme)
        themeButton.setOnClickListener {
            val currentBackground = view.background
            if (currentBackground == null) {
                view.setBackgroundColor(0xFF2C2C2C.toInt()) // Dark theme
                themeButton.text = "☀️ Light Theme"
                Toast.makeText(requireContext(), "🌙 Dark theme enabled", Toast.LENGTH_SHORT).show()
            } else {
                view.background = null // Light theme
                themeButton.text = "🌙 Dark Theme"
                Toast.makeText(requireContext(), "☀️ Light theme enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}