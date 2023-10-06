package com.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pin.MainActivity
import com.example.pin.R
import com.example.pin.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }


}