package kr.ac.a1015_3rd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.ac.a1015_3rd.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 탭 설정x1
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 탭이 선택 되었을 때
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // 탭이 선택되지 않은 상태로 변경 되었을 때
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // 이미 선택된 탭이 다시 선택 되었을 때
            }
        })

        // 뷰페이저에 어댑터 연결
        binding.pager.adapter = ViewPagerAdapter(this)

        /* 탭과 뷰페이저를 연결, 여기서 새로운 탭을 다시 만드므로 레이아웃에서 꾸미지말고
        여기서 꾸며야함
         */
        TabLayoutMediator(binding.tabLayout, binding.pager) {tab, position ->
            when(position) {
                0 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.cal)
                1 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.list)
                2 -> tab.icon = ContextCompat.getDrawable(this, R.drawable.remind)
                
            }
        }.attach()

    }
}