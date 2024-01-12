package com.example.navigationgraph

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.googlemaps.R

class ThirdFragment : Fragment() {
    lateinit var third_fragment :TextView
    lateinit var welcome :TextView

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_third, container, false)
        third_fragment = view.findViewById(R.id.third_fragment)
        welcome = view.findViewById(R.id.welcome)


        val userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        userViewModel.username.observe(requireActivity()) { s ->
            if (s == null) {
                Navigation.findNavController(view).navigate(R.id.navigationToFirstFragment)

            } else {
                welcome.text = "Welcome , "+ s

            }
        }

//        NavHostFragment.findNavController(this).popBackStack()

        return view
    }


}