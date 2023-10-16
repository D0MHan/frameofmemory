package kr.ac.a1015_3rd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,

    val content : String,
    val year : Int,
    val month : Int,
    val day : Int,
    val image: ByteArray

)
