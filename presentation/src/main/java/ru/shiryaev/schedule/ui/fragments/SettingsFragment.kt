package ru.shiryaev.schedule.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.shiryaev.schedule.databinding.FrSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FrSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }
}