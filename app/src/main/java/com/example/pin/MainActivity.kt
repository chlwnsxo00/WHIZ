package com.example.pin

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Interface.ItemListener
import com.example.adapter.DragManageAdapter
import com.example.adapter.NameAdapter
import com.example.fragment.CalendarFragment
import com.example.fragment.HomeFragment
import com.example.fragment.StarFragment
import com.example.fragment.UserFragment
import com.example.obj.sites
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener, ItemListener {

    private val ivMenu: ImageView by lazy {
        findViewById(R.id.iv_menu)
    }
    private val drawerLayout: DrawerLayout by lazy {
        findViewById(R.id.drawer)
    }
    private val toolbar: androidx.appcompat.widget.Toolbar by lazy {
        findViewById(R.id.toolbar)
    }
    private val now: TextView by lazy {
        findViewById(R.id.now)
    }
    private val navigation: NavigationView by lazy {
        findViewById(R.id.navigation)
    }
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

    var fragmentHome = Fragment()

    lateinit var bottomNavigationView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        now.text = SplashActivity.prefs.getString("name","Bloomberg")

        fragmentHome = HomeFragment()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        //첫 프래그먼트 화면은 home fragment로
        bottomNavigationView.selectedItemId = R.id.home
        initRecyclerView()
        initList()
    }

    fun getNameList() : ArrayList<sites>
    {
        val list = ArrayList<sites>()
        list.add(sites("Bloomberg","https://www.bloomberg.com/markets"))
        list.add(sites("CNBC","https://www.cnbc.com/world/?region=world"))
        list.add(sites("Google finance","https://www.google.com/finance/"))
        list.add(sites("yahoo!finance","https://finance.yahoo.com/?guccounter=1&guce_referrer=aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbS8&guce_referrer_sig=AQAAAKB0BfHIF_aP13mToUmDLG_gMUpyz31uzhO03q1FeVx4SlkRq9bKPJNdcqWUH5wOKooDTauDD0aK2gWVn2rrgwEvqSbZL6IyHNTCjp0Baa6w9JTs2Czh249aP5pWJL4H2M714Oh9_3usJyzT1USs4ZiSYGvM1o7Z1rdx62-Np1YM"))
        list.add(sites("FINVIZ","https://finviz.com/map.ashx?t=sec"))

        return list
    }

    private fun initRecyclerView() {
        val rvSites = findViewById<RecyclerView>(R.id.rvSites)
        val layoutManager = LinearLayoutManager(this)
        rvSites.layoutManager = layoutManager

        val nameList = getNameList()
        val nameAdapter = NameAdapter(nameList, this)
        rvSites.adapter = nameAdapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rvSites.addItemDecoration(dividerItemDecoration)

        // Setup ItemTouchHelper
        val callback = DragManageAdapter(nameAdapter, nameList, this, ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(rvSites)
    }


    private fun initList() {
        //액션바 변경하기(들어갈 수 있는 타입 : Toolbar type
        setSupportActionBar(toolbar)

        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
        val navigationView: NavigationView = findViewById(R.id.navigation)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
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

    override fun onClicked(site: sites) {
        now.text = site.name
        Log.d("url", "prefs.set(url) = " + site.url)
        SplashActivity.prefs.setString("url", site.url)
        SplashActivity.prefs.setString("name", site.name)
        val fragmentHome = HomeFragment()
        val newTransaction = supportFragmentManager.beginTransaction() // 새로운 트랜잭션 생성
        newTransaction.replace(R.id.frameLayout, fragmentHome, "home")
        newTransaction.addToBackStack(null)
        newTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        newTransaction.commit() // 새로운 트랜잭션 커밋
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
