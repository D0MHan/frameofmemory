package kr.ac.a1015_3rd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import kr.ac.a1015_3rd.databinding.ActivityAddDiaryBinding
import java.util.*
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class AddDiary_CalVerisonActivity : AppCompatActivity() {

    private val memoViewModel: MemoViewModel by viewModels()
    val binding by lazy { ActivityAddDiaryBinding.inflate(layoutInflater) }

    // Manifest에서 설정한 권한을 가져옵니다.
    private val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    private val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // 권한 플래그값 정의
    private val FLAG_PERM_CAMERA = 98
    private val FLAG_PERM_STORAGE = 99

    // 카메라와 갤러리를 호출하는 플래그
    private val FLAG_REQ_CAMERA = 101
    private val FLAG_REA_STORAGE = 102



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val selectedYear = intent.getIntExtra("selectedYear", 0)
        val selectedMonth = intent.getIntExtra("selectedMonth", 0)
        val selectedDay = intent.getIntExtra("selectedDay", 0)

        // 화면이 만들어지면서 저장소 권한을 체크합니다.
        // 권한이 승인되어 있으면 카메라를 호출하는 메소드를 실행합니다.
        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
            setViews()
        }

        // 사용자가 입력한 메모와 선택한 날짜 정보를 이용하여 메모를 데이터베이스에 추가
        binding.okButton2.setOnClickListener {
            val diary = binding.memoEditView2.text.toString()

            if (TextUtils.isEmpty(diary)) {
                Toast.makeText(this, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val imageByteArray = imageViewToByteArray(binding.imageView)

                if (imageByteArray != null) {
                    val content: String = diary

                    val memo = Memo(id = 0, content, selectedYear, selectedMonth, selectedDay, imageByteArray)
                    memoViewModel.addMemo(memo)
                    Toast.makeText(this, "추가", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "사진을 저장해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.galleryBtn2.setOnClickListener {
            // 갤러리 호출 메소드
            openGallery()
        }

        binding.cancelButton2.setOnClickListener { finish() }
    }

    private fun setViews() {
        // 카메라 버튼 클릭
        binding.camBtn2.setOnClickListener {
            // 카메라 호출 메소드
            openCamera()
        }
    }

    private fun openCamera() {
        // 카메라 권한이 있는지 확인
        if (checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)) {
            // 권한이 있으면 카메라를 실행시킵니다.
            val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, FLAG_REQ_CAMERA)
        }
    }

    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        // 안드로이드 버전이 마시멜로우 이상일때
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                // 만약 권한이 승인되어 있지 않다면 권한승인 요청을 사용자 화면에 호출합니다.
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, flag)
                    return false
                }
            }
        }
        return true
    }



    fun imageViewToByteArray(imageView: ImageView): ByteArray? {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
        }
        return null
    }

    private fun openGallery() {
        // 저장소 권한이 있는지 확인
        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, FLAG_REA_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                FLAG_REA_STORAGE -> {
                    val imageUri = data?.data
                    if (imageUri != null) {
                        try {
                            // 갤러리에서 선택한 이미지를 비트맵으로 가져와 ImageView에 표시
                            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                            binding.imageView.setImageBitmap(bitmap)

                            // 이미지를 바이트 배열로 변환
                            val imageByteArray = bitmapToByteArray(bitmap)

                            // imageByteArray를 데이터베이스에 저장하거나 다른 용도로 사용
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(this, "이미지 로드 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data.extras!!.get("data") as Bitmap
                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    // 이미지를 바이트 배열로 변환
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
