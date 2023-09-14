package com.example.pin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.Fragment.CalendarFragment
import com.example.Fragment.HomeFragment
import com.example.Fragment.StarFragment
import com.example.Fragment.UserFragment
import com.example.pin.R
import com.example.pin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView : BottomNavigationView
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        //첫 프래그먼트 화면은 home fragment로
        bottomNavigationView.selectedItemId = R.id.home
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        when(p0.itemId){
            R.id.home ->{
                val fragmentHome = HomeFragment()
                transaction.replace(R.id.frameLayout,fragmentHome, "home")
            }
            R.id.star -> {
                val fragmentStar = StarFragment()
                transaction.replace(R.id.frameLayout,fragmentStar, "star")
            }
            R.id.calendar -> {
                val fragmentCalendar = CalendarFragment()
                transaction.replace(R.id.frameLayout,fragmentCalendar, "calendar")
            }
            R.id.user -> {
                val fragmentUser = UserFragment()
                transaction.replace(R.id.frameLayout,fragmentUser, "user")
            }
        }
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        return true
    }
}