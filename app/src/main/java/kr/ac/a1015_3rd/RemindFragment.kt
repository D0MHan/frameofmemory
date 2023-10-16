package kr.ac.a1015_3rd

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.a1015_3rd.databinding.FragmentRemindBinding
import kr.ac.a1015_3rd.databinding.FragmentTodoListBinding
import androidx.lifecycle.Observer
import kotlin.random.Random
import java.util.*

class RemindFragment : Fragment() {

    private var binding: FragmentRemindBinding? = null
    private val memoViewModel: MemoViewModel by viewModels()
    private val adapter : TodoAdapter by lazy { TodoAdapter(memoViewModel) } // 어댑터 선언
    private val idList = mutableListOf<Int>() // 새로운 배열 선언





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 상단 메뉴 추가
        setHasOptionsMenu(true)
        // 뷰바인딩
        binding = FragmentRemindBinding.inflate(inflater, container, false)

        binding?.remindBtn?.setOnClickListener {
            onFabClicked()
        }

        binding!!.remindRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)
        binding!!.remindRecyclerView.adapter = adapter











        return binding!!.root
    }

    // Fab 클릭시 사용되는 함수
    private fun onFabClicked(){



        memoViewModel.getIdList().observe(viewLifecycleOwner, Observer { idList ->
            if (idList.isNotEmpty()) {
                val random = java.util.Random()
                val randomIndex = random.nextInt(idList.size)
                val randomId = idList[randomIndex]
                memoViewModel.readRandomData(randomId).observe(viewLifecycleOwner, Observer {
                    adapter.setData(it)
                })
                Log.d("YourNewFragment", "Random ID: $randomId")
            } else {
                Log.d("YourNewFragment", "ID 목록이 비어 있습니다.")
                Toast.makeText(context, "일기를 먼저 작성해주세요.", Toast.LENGTH_SHORT).show()
            }
        })


    }






}