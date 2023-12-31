package kr.ac.a1015_3rd

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 뷰모델은 DB에 직접 접근하지 않아야함. Repository 에서 데이터 통신.
class MemoViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<Memo>>

    private val repository : MemoRepository





    // get set
    private var _currentData = MutableLiveData<List<Memo>>()
    val currentData : LiveData<List<Memo>>
        get() = _currentData

    init{
        val memoDao = MemoDatabase.getDatabase(application)!!.memoDao()
        repository = MemoRepository(memoDao)
        readAllData = repository.readAllData.asLiveData()

    }

    fun addMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMemo(memo)
        }
    }

    fun updateMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(memo)
        }
    }

    fun deleteMemo(memo : Memo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMemo(memo)
        }
    }



    fun readDateData(year : Int, month : Int, day : Int): LiveData<List<Memo>> {
        return repository.readDateData(year, month, day).asLiveData()
    }

    fun readRandomData(id : Int): LiveData<List<Memo>>{
        return  repository.readRandomData(id).asLiveData()
    }

    fun getIdList(): LiveData<List<Int>> {
        return repository.getIdList()
    }

//    fun readDateData(year : Int, month : Int, day : Int) {
//        viewModelScope.launch(Dispatchers.IO) {
////            val tmp = repository.readDateData(year, month, day)
//            _currentData.postValue(tmp)
//        }
//    }

    fun searchDatabase(searchQuery: String): LiveData<List<Memo>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}