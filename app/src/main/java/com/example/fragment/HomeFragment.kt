package com.example.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.Interface.SiteClickListener
import com.example.obj.sites
import com.example.pin.MainActivity
import com.example.pin.R
import com.example.pin.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(),SiteClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initWebView("https://www.bloomberg.com/markets")


        val toolbar = mainActivity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        mainActivity.setSupportActionBar(toolbar) //액티비티의 앱바(App Bar)로 지정

        return binding.root
    }

    private fun initWebView(siteurl: String) {
        binding.wv.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        binding.wv.webViewClient = WebViewClient()
        binding.wv.webChromeClient = WebChromeClient()
        binding.wv.setNetworkAvailable(true)
        binding.wv.settings.javaScriptEnabled = true
        //// Sets whether the DOM storage API is enabled.
        binding.wv.settings.domStorageEnabled = true
        binding.wv.loadUrl(siteurl)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mainActivity.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSiteClicked(site: sites) {
        Log.d("wv","onSiteClicked 함수에 들어옴")
        binding.wv.loadUrl(site.url)
    }
}