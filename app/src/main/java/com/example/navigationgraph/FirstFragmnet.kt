package com.example.navigationgraph

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.googlemaps.R
import com.example.traintraking.prefrences.AppPreferencesDelegates

class FirstFragmnet : Fragment() {

    lateinit var first_fragment :TextView
    private val appPreferences = AppPreferencesDelegates.get()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_first, container, false)
        first_fragment = view.findViewById(R.id.first_fragment)

      first_fragment.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.navigationToSecondFragment)

          if (appPreferences.wasOnboardingSeen) {
              Navigation.findNavController(view).navigate(R.id.navigationToThird)

          } else {
              Navigation.findNavController(view).navigate(R.id.navigationToSecondFragment)
          }

      }

        return view
    }


}