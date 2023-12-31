package kr.ac.a1015_3rd

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.a1015_3rd.databinding.TodoItemBinding

class TodoAdapter(private val memoViewModel: MemoViewModel) : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    private var memoList = emptyList<Memo>()

    class MyViewHolder(private val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var memo: Memo
        lateinit var memoViewModel: MemoViewModel

        val imageView = binding.imageView2
        val imgFeel = binding.imgFeel

        init {
            imageView.visibility = View.GONE // imageView를 화면에서 숨김
        }

        fun bind(currentMemo: Memo, memoViewModel: MemoViewModel) {
            binding.memo = currentMemo
            this.memoViewModel = memoViewModel
            memo = currentMemo

            if (currentMemo.image != null) {
                val bitmap = BitmapFactory.decodeByteArray(currentMemo.image, 0, currentMemo.image.size)
                imageView.setImageBitmap(bitmap)
                imageView.visibility = View.VISIBLE // 이미지가 있을 때 이미지뷰 보이기
            }

            if (currentMemo.selectedImageResourceId != null) {
                imgFeel.setImageResource(currentMemo.selectedImageResourceId)
            }

            binding.deleteButton.setOnClickListener {
                memoViewModel.deleteMemo(currentMemo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(memoList[position], memoViewModel)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    fun setData(memo: List<Memo>) {
        memoList = memo
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
