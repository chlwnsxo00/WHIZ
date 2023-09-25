package com.example.fragment

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.pin.MainActivity
import com.example.pin.SplashActivity
import com.example.pin.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        initWebView(SplashActivity.prefs.getString("url","https://www.bloomberg.com/markets"))
        initRefreshLayout()
        return binding.root
    }


    private fun initRefreshLayout() {
        binding.refreshLayout.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                initWebView(SplashActivity.prefs.getString("url","https://www.bloomberg.com/markets"))
                binding.refreshLayout.postDelayed({
                    // 리프레시 완료 후 호출
                    binding.refreshLayout.refreshComplete()
                }, 1000) // 예시: 1초 후 리프레시 완료
            }
        })
    }

    private fun initWebView(siteurl: String) {
        binding.wv.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        binding.wv.webViewClient = WebViewClient()
        binding.wv.webChromeClient = WebChromeClient()
        binding.wv.setNetworkAvailable(true)
        binding.wv.settings.javaScriptEnabled = true
        binding.wv.settings.domStorageEnabled = true
        // WebView 스크롤 이벤트 처리
        binding.wv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            binding.refreshLayout.isEnabled = scrollY == 0 && oldScrollY > 0
        }
        binding.wv.loadUrl(siteurl)
        binding.wv.webViewClient = MyWebClient(binding.progressBar)
    }

    class MyWebClient(private val progressBar: ProgressBar) : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            // TODO: Add your code here
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            progressBar.visibility = View.VISIBLE
            url?.let { view?.loadUrl(it) }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
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

}