package kr.ac.a1015_3rd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.a1015_3rd.databinding.FragmentTodoListBinding
import java.util.Calendar


class TodoListFragment : Fragment() {

    private var binding: FragmentTodoListBinding? = null
    private val memoViewModel: MemoViewModel by viewModels()
    private val adapter : TodoAdapter by lazy { TodoAdapter(memoViewModel) } // 어댑터 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 상단 메뉴 추가
        setHasOptionsMenu(true)
        // 뷰바인딩
        binding = FragmentTodoListBinding.inflate(inflater, container, false)

        binding?.addButton?.setOnClickListener {
            onFabClicked()
        }

        // 아이템을 가로로 하나씩 보여주고 어댑터 연결
        binding!!.todoRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding!!.todoRecyclerView.adapter = adapter

        // 리스트 관찰하여 변경시 어댑터에 전달해줌
        memoViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })





        return binding!!.root
    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked(){
        val intent = Intent(activity, AddDiaryActivity::class.java)
        startActivity(intent)
    }

//    // 다이얼로그에서 추가버튼 클릭 됐을 때
//    override fun onOkButtonClicked(content: String) {
//
//        // 현재의 날짜를 불러옴
//        val cal = Calendar.getInstance()
//        val year = cal.get(Calendar.YEAR)
//        val month = cal.get(Calendar.MONTH) + 1
//        val day = cal.get(Calendar.DATE)
//
//        // 현재의 날짜로 메모를 추가해줌
//        val memo = Memo(id,content, year, month, day)
//        memoViewModel.addMemo(memo)
//        Toast.makeText(activity,"추가", Toast.LENGTH_SHORT).show()
//    }





}