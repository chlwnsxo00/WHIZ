package com.example.pin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Interface.ItemListener
import com.example.adapter.SiteListAdapter
import com.example.fragment.CalendarFragment
import com.example.fragment.HomeFragment
import com.example.fragment.StarFragment
import com.example.fragment.UserFragment
import com.example.obj.sites
import com.example.room.Site
import com.example.viewModels.SiteViewModel
import com.example.viewModels.SiteViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener, ItemListener {

    private val newSiteActivityRequestCode = 1
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
    private lateinit var editSiteView: EditText
    private lateinit var editURLView: EditText
    var fragmentHome = Fragment()
    lateinit var bottomNavigationView: BottomNavigationView
    private val SiteViewModel: SiteViewModel by viewModels {
        SiteViewModelFactory((application as SiteApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        now.text = SplashActivity.prefs.getString("name", "Bloomberg")

        val recyclerView = findViewById<RecyclerView>(R.id.rvSites)
        val adapter = SiteListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fragmentHome = HomeFragment()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        //첫 프래그먼트 화면은 home fragment로
        bottomNavigationView.selectedItemId = R.id.home
        initRecyclerView()
        initList()
        initAdd()
    }

    private fun initAdd() {
        val button = findViewById<ImageView>(R.id.add_button)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, NewSiteActivity::class.java)
            startActivityForResult(intent, newSiteActivityRequestCode)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvSites)
        val adapter = SiteListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        SiteViewModel.allSites.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })

        val add = findViewById<ImageView>(R.id.add_button)
        add.setOnClickListener {
            val intent = Intent(this@MainActivity, NewSiteActivity::class.java)
            startActivityForResult(intent, newSiteActivityRequestCode)
        }
//        val rvSites = findViewById<RecyclerView>(R.id.rvSites)
//        val layoutManager = LinearLayoutManager(this)
//        rvSites.layoutManager = layoutManager
//
//        val nameAdapter = NameAdapter(nameList, this)
//        rvSites.adapter = nameAdapter
//
//        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        rvSites.addItemDecoration(dividerItemDecoration)
//
//        // Setup ItemTouchHelper
//        val callback = DragManageAdapter(
//            nameAdapter,
//            nameList,
//            this,
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        )
//        val helper = ItemTouchHelper(callback)
//        helper.attachToRecyclerView(rvSites)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newSiteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var word : String? = null
            var url : String? = null
            intentData?.getStringExtra(NewSiteActivity.EXTRA_SITE_REPLY)?.let { reply ->
                word = reply
            }
            intentData?.getStringExtra(NewSiteActivity.EXTRA_URL_REPLY)?.let { reply ->
                url = reply
            }
            if (word!=null&&url!=null)
                SiteViewModel.insert(Site(word!!, url!!))
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
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
        val transaction = supportFragmentManager.beginTransaction() // 새로운 트랜잭션 생성
        when (p0.itemId) {
            R.id.home -> {
                val fragmentHome = HomeFragment()
                transaction.replace(R.id.frameLayout, fragmentHome, "home")
            }
            R.id.star -> {
                val fragmentStar = StarFragment()
                transaction.replace(R.id.frameLayout, fragmentStar, "star")
            }
            R.id.calendar -> {
                val fragmentCalendar = CalendarFragment()
                transaction.replace(R.id.frameLayout, fragmentCalendar, "calendar")
            }
            R.id.user -> {
                val fragmentUser = UserFragment()
                transaction.replace(R.id.frameLayout, fragmentUser, "user")
            }
        }
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        return true
    }

    override fun onClicked(site: Site) {
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
