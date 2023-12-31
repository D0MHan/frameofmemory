package kr.ac.a1015_3rd


import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class MemoRepository(private val memoDao: MemoDao) {
   val readAllData : Flow<List<Memo>> = memoDao.readAllData()



    suspend fun addMemo(memo: Memo){
        memoDao.addMemo(memo)
    }

    suspend fun updateMemo(memo: Memo){
        memoDao.updateMemo(memo)
    }

    suspend fun deleteMemo(memo: Memo){
        memoDao.deleteMemo(memo)
    }



    fun readDateData(year : Int, month : Int, day : Int): Flow<List<Memo>> {
        return memoDao.readDateData(year, month, day)
    }

    fun readRandomData(id :Int): Flow<List<Memo>>{
        return  memoDao.readRandomData(id)
    }

    fun getIdList(): LiveData<List<Int>> {
        return memoDao.getIdList()
    }

//    fun readDateData(year : Int, month : Int, day : Int): List<Memo> {
//        return memoDao.readDateData(year, month, day)
//    }

    fun searchDatabase(searchQuery: String): Flow<List<Memo>> {
      return memoDao.searchDatabase(searchQuery)
    }
}