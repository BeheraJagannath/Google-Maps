package com.example.navigationgraph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.googlemaps.R
import com.example.traintraking.prefrences.AppPreferencesDelegates


class SecondFragment : Fragment() {
    lateinit var second_fragment : TextView
    lateinit var usernam : EditText
    lateinit var password : EditText
    private val appPreferences = AppPreferencesDelegates.get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_second, container, false)
        second_fragment = view.findViewById(R.id.second_fragment)
        usernam = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)



        second_fragment.setOnClickListener {
            appPreferences.wasOnboardingSeen=true

                val username =  usernam .text.toString()

                val userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
                userViewModel.setUserName(username)
//                NavHostFragment.findNavController(this).popBackStack()

                findNavController().navigate(R.id.thirdFragment)
//            Navigation.findNavController(view).navigate(R.id.navigationToThirdFragment)



        }



        return  view
    }


}