package com.example.pin

import ExpandableListAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.Interface.ItemListener
import com.example.obj.MenuTitle
import com.example.pin.databinding.ActivityNewSiteBinding
import com.example.room.Site

class NewSiteActivity : AppCompatActivity(), ItemListener {

    private lateinit var editWordView: EditText
    private lateinit var editURLView: EditText
    private val expandableListView: ExpandableListView by lazy {
        findViewById(R.id.expandableListView)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewSiteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setExpandableList()

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar) //액티비티의 앱바(App Bar)로 지정

        val actionBar: ActionBar? = supportActionBar //앱바 제어를 위해 툴바 액세스
        actionBar!!.setDisplayHomeAsUpEnabled(true) // 앱바에 뒤로가기 버튼 만들기
        actionBar?.setHomeAsUpIndicator(R.drawable.arrow_back) // 뒤로가기 버튼 색상 설정

        editWordView = findViewById(R.id.edit_word)
        editURLView = findViewById(R.id.edit_url)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                val url = editURLView.text.toString()
                replyIntent.putExtra(EXTRA_SITE_REPLY, word)
                replyIntent.putExtra(EXTRA_URL_REPLY, url)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun setExpandableList() {
        val parentList = mutableListOf<MenuTitle>(
            MenuTitle("뉴스 및 기업 정보", 0),
            MenuTitle("기업 공시", 1),
            MenuTitle("종합적인 종목 검색과 차트", 2),
            MenuTitle("종목 스크리닝과 재무제표 정리", 3),
            MenuTitle("유명투자가의 포트폴리오 확인", 4),
            MenuTitle("개별 종목 분석과 투자 아이디어 제공", 5),
            MenuTitle("개별 종목 혹은 펀드의 기술적 분석", 6),
            MenuTitle("ETF 관련 사이트", 7),
            MenuTitle("매크로 경제 지표", 8),
            MenuTitle("기업 실적 발표와 캘린더 제공", 9),
            MenuTitle("IPO와 SPAC, 비상장 기업 관련 사이트", 10),
            MenuTitle("배당주 관련 사이트", 11),
            MenuTitle("포트폴리오 시뮬레이션", 12),
            MenuTitle("그 외 유명한 사이트", 13),
        )

        val childList = mutableListOf<MutableList<Site>>(
            mutableListOf(
                Site("Bloomberg","https://www.bloomberg.com/markets?utm_medium=cpc_search&utm_campaign=BR_ACQ_BRAND_BRANDGENERALX_EVG_XXXX_XXX_Y0469_EN_EN_X_BLOM_GO_SE_XXX_XXXXXXXXXX&gclid=CjwKCAjwgsqoBhBNEiwAwe5w07YyYqykrAK2ut5OtWCTLI01lZU3uauuM2eIxu-qRzFqpY7qowBR3RoCRjsQAvD_BwE&gclsrc=aw.ds")
                , Site("CNBC","https://www.cnbc.com/world/?region=world"),
                Site("CNN buiness","https://edition.cnn.com/business"),
                Site("Market Watch","https://www.marketwatch.com/")
            ),
            mutableListOf(
                Site("EDGAR","https://www.sec.gov/edgar/searchedgar/companysearch")
                , Site("BAMSEC","https://www.bamsec.com/")
            ),
            mutableListOf(
                Site("Yahoo Finance","https://finance.yahoo.com/")
                , Site("Google Finance","https://www.google.com/finance/"),
                Site("Investing","https://kr.investing.com/"),
                Site("Finviz","https://finviz.com/")
                , Site("Benzinga","https://www.benzinga.com/"),
                Site("MarketBeat","https://www.marketbeat.com/"),
                Site("Koyfin","https://www.koyfin.com/")
                , Site("Trading View","https://kr.tradingview.com/")
            ),
            mutableListOf(
                Site("Stockrow","https://community.stockrow.com/")
                , Site("macrotrends","https://www.macrotrends.net/")
            ),
            mutableListOf(
                Site("Gurufocus","https://www.gurufocus.com/")
            ),
            mutableListOf(
                Site("Market Screener","https://www.marketscreener.com/")
                , Site("finbox","https://finbox.com/"),
                Site("Seeking Alpha","https://seekingalpha.com/"),
                Site("The Motley Fool","https://www.fool.com/")
                , Site("VIC","http://www.vicglobal.co.kr/main.vg")
            ),
            mutableListOf(
                Site("MorningStar","https://www.morningstar.com/")
                , Site("Zacks","https://www.zacks.com/"),
                Site("Stockchart","https://stockcharts.com/"),
                Site("Barchart","https://www.barchart.com/")
            ),
            mutableListOf(
                Site("ETF Trends","https://www.etftrends.com/")
                , Site("ETF DB","https://etfdb.com/")
            ),
            mutableListOf(
                Site("Fed","https://www.federalreserve.gov/newsevents.htm")
                , Site("FRED","https://fred.stlouisfed.org/"),
                Site("Trading Economics","https://tradingeconomics.com/")
            ),
            mutableListOf(
                Site("Earnings Whisper","https://www.earningswhispers.com/")
                , Site("TipRanks","https://www.tipranks.com/"),
                Site("Fidelity","https://www.fidelity.com/"),
                Site("Alpha Street","https://www.alphastreet.com/home")
            ),
            mutableListOf(
                Site("Nasdaq IPO","https://www.nasdaq.com/market-activity/ipos")
                , Site("NYSE IPO","https://www.nyse.com/ipo-center/filings"),
                Site("SPAC Analytics","https://www.spacanalytics.com/"),
                Site("SPAC Insider","https://www.spacinsider.com/")
                , Site("SPAC Research","https://www.spacresearch.com/"),
                Site("CB Insights","https://www.cbinsights.com/research-request-a-demo?campaignid=20427135244&adgroupid=148837960981&utm_term=cb%20insights&utm_source=google&utm_medium=cpc&utm_campaign=PAID_2023_GOOGLE_BRANDED&hsa_tgt=kwd-67162128744&hsa_grp=148837960981&hsa_src=g&hsa_net=adwords&hsa_mt=e&hsa_ver=3&hsa_ad=654764386547&hsa_acc=5728918340&hsa_kw=cb%20insights&hsa_cam=20427135244&gclid=CjwKCAjwgsqoBhBNEiwAwe5w0xoD5ZMhAii9AYNBmIDtJlKnTQVzjTwTjeK8Kxg2rvjr2prySXBM1xoCO8cQAvD_BwE")
            ),
            mutableListOf(
                Site("Dvidend","https://www.dividend.com/")
                , Site("Dvidend Investor","https://www.dividendinvestor.com/")
            ),
            mutableListOf(
                Site("Portfolio Visualizer","https://www.portfoliovisualizer.com/")
            ),
            mutableListOf(
                Site("TeleTrader","https://www.teletrader.com/")
                , Site("EIA","https://www.eia.gov/"),
                Site("Business Insider","https://www.businessinsider.com/"),
                Site("Gold Price USA","https://goldprice.org/")
                , Site("NakedShort Report","https://www.nakedshortreport.com/")
            )
        )
        val adapter = ExpandableListAdapter(this, parentList, childList,this)
        expandableListView.setAdapter(adapter)
        expandableListView.setOnGroupClickListener { parent, view, groupPosition, id ->
            setListViewHeight(parent, groupPosition)
            false
        }
    }
    fun setListViewHeight(listView: ExpandableListView, group: Int) {
        val listAdapter = listView.expandableListAdapter as ExpandableListAdapter
        var totalHeight = 0
        val desiredWidth: Int = View.MeasureSpec.makeMeasureSpec(
            listView.width,
            View.MeasureSpec.EXACTLY
        )
        for (i in 0 until listAdapter.groupCount) {
            val groupItem: View = listAdapter.getGroupView(i, false, null, listView)
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += groupItem.measuredHeight
            if(listView.isGroupExpanded(i) && i != group
                || !listView.isGroupExpanded(i) && i == group) {
                for (j in 0 until listAdapter.getChildrenCount(i)) {
                    val listItem: View = listAdapter.getChildView(
                        i, j, false, null, listView
                    )
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                    totalHeight += listItem.measuredHeight
                }
            }
            val params = listView.layoutParams
            var height = (totalHeight + listView.dividerHeight* (listAdapter.groupCount-1))
            if(height < 10) {
                height = 200
            }
            params.height = height
            listView.layoutParams= params
            listView.requestLayout()
        }
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_SITE_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_URL_REPLY = "com.example.android.urllistsql.REPLY"
    }

    override fun onClicked(name: Site) {
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_SITE_REPLY, name.name)
        replyIntent.putExtra(EXTRA_URL_REPLY, name.url)
        setResult(Activity.RESULT_OK, replyIntent)
        Toast.makeText(this,"${name.name}이 추가되었습니다!",Toast.LENGTH_LONG).show()
    }
}