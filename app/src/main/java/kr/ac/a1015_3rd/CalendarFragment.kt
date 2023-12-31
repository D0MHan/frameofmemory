package kr.ac.a1015_3rd

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.a1015_3rd.databinding.FragmentCalendarBinding
import kr.ac.a1015_3rd.databinding.TodoItemBinding

class CalendarFragment : Fragment() {

    private var binding : FragmentCalendarBinding? = null
    private val memoViewModel: MemoViewModel by viewModels() // 뷰모델 연결
    private val adapter : TodoAdapter by lazy { TodoAdapter(memoViewModel) } // 어댑터 선언

    private var year : Int = 0
    private var month : Int = 0
    private var day : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰바인딩
        binding = FragmentCalendarBinding.inflate(inflater,container,false)

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding!!.calendarRecyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        binding!!.calendarRecyclerview.adapter = adapter

        binding!!.calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            // 날짜 선택시 그 날의 정보 할당
            this.year = year
            this.month = month+1
            this.day = day



            // 리스트 관찰하여 변경시 어댑터에 전달해줌
            memoViewModel.readDateData(this.year,this.month,this.day).observe(viewLifecycleOwner, Observer {
                adapter.setData(it)
            })
        }

        // Fab 클릭시 다이얼로그 띄움
        binding!!.calendarDialogButton.setOnClickListener {
            if(year == 0) {
                Toast.makeText(activity, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                onFabClicked()
            }
        }



        return binding!!.root
    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked() {
        if (year == 0) {
            Toast.makeText(activity, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(activity, AddDiary_CalVerisonActivity::class.java)
            intent.putExtra("selectedYear", year)
            intent.putExtra("selectedMonth", month)
            intent.putExtra("selectedDay", day)
            startActivity(intent)
        }
    }

    // 프래그먼트는 뷰보다 오래 지속 . 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


}