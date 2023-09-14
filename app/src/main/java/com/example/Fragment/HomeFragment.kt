package com.example.Fragment

import `in`.srain.cube.views.ptr.PtrDefaultHandler
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.adapter.NewsAdapter
import com.example.doveNews.`interface`.NewsClick
import com.example.objects.News
import com.example.pin.ArticleActivity
import com.example.pin.MainActivity
import com.example.pin.R
import com.example.pin.databinding.FragmentHomeBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), NewsClick {
    private var Address : String = ""
    lateinit var binding:FragmentHomeBinding
    private var newsList = ArrayList<News>()
    private lateinit var newsadApter: NewsAdapter

    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        NewsCrawling()
        initFloatingButton()
        initPowerMenu()
        initPowerMenuBackground()
        initRefreshLayout()
        return binding.root
    }
    private fun NewsCrawling() {
        Thread(kotlinx.coroutines.Runnable {
            val doc =
                Jsoup.connect("https://www.bloomberg.com/markets")
                    .userAgent("Chrome").get()
            var elements: Elements = doc.select(".styles_storyBlock__CQtg9")
            // mobile-padding 클래스의 board-list의 id를 가진 것들을 elements 객체에 저장
            /*
            크롤링 하는 법 : class 는 .(class) 로 찾고 id 는 #(id) 로 검색
             */
            for (elements in elements) {  //elements의 개수만큼 반복
                val title = elements.select(".hover:underline.focus:underline a").first()!!.text()
                Log.d("crawling", title)
                val cover = elements.select(".styles_storyImage__knB2m a figure img").attr("src")
                Log.d("crawling", cover)
                val address = elements.select(".hover:underline.focus:underline a").attr("href")
                Log.d("crawling", address)
                val page = "BloomBerg"
                Log.d("crawling", page)
                newsList.add(
                    News(
                        title, cover, address
                    )
                )     //위에서 크롤링 한 내용들을 itemlist에 추가
            }
            mainActivity.runOnUiThread {
                initNewsRecyclerView(newsList)
            }
        }).start()
    }
    private fun initRefreshLayout() {
        binding.refreshFrame.setPtrHandler(object : PtrDefaultHandler() {
            override fun onRefreshBegin(frame: PtrFrameLayout) {
                newsList.clear()
                NewsCrawling()
                // 예를 들어, 데이터를 다시 로드하거나 업데이트할 수 있습니다.
                binding.refreshFrame.postDelayed({
                    // 리프레시 완료 후 호출
                    binding.refreshFrame.refreshComplete()
                }, 500) // 1초 후 리프레시 완료
            }
        })
    }

    private fun initNewsRecyclerView(newsList: ArrayList<News>) {
        binding.newsRv.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        newsadApter = NewsAdapter(newsList, this)
        binding.newsRv.adapter = newsadApter

//        newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                // 스크롤이 끝에 도달했는지 확인
//                if(!isKeywordNew){
//                    if (!newsRecyclerView.canScrollVertically(1)) {
//                        newsadApter.deleteLoading()
//                        NewsCrawling(sid)
//                    }
//                }
//            }
//        })

    }
    private fun initFloatingButton() {
        binding.fab.setOnClickListener {
            binding.nsv.smoothScrollTo(0, 0)
        }
    }
    private fun initPowerMenuBackground() {
        binding.powerMenuBackground.setOnClickListener { }
    }

    private fun initPowerMenu() {
        binding.powerMenu.isClickable = false
        binding.powerMenu.setOnClickListener { }
        initCloseButton()
        initReadMoreButton()
    }

    private fun initReadMoreButton() {
        binding.powerReadMore.setOnClickListener {
            val intent = Intent(mainActivity, ArticleActivity::class.java)
            intent.putExtra("address", Address)
            startActivity(intent)
        }
    }

    private fun initCloseButton() {
        binding.powerClose.setOnClickListener {
            binding.powerMenu.isVisible = false
            binding.powerMenu.isClickable = false
            binding.powerMenuBackground.isClickable = false
            binding.powerMenuBackground.isVisible = false
            binding.fab.isVisible = true
            binding.fab.isClickable = true
        }
    }

    override fun NewsClick(
        address: String,
        title: String,
        image: String
    ) {
        Thread(kotlinx.coroutines.Runnable {
            mainActivity.runOnUiThread {
                binding.powerTitle.text = title
                Glide.with(binding.root).load(image).into(binding.powerImage)
                binding.powerMenu.isVisible = true
                binding.powerMenu.isClickable = true
                binding.powerMenuBackground.isClickable = true
                binding.powerMenuBackground.isVisible = true
                binding.fab.isVisible = false
                binding.fab.isClickable = false
                Address= address
            }
        }).start()
    }


}