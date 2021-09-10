package com.jcy.ch22_searchfreeimage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcy.ch22_searchfreeimage.data.PhotoAdapter
import com.jcy.ch22_searchfreeimage.data.Repository
import com.jcy.ch22_searchfreeimage.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scope = MainScope()
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
        fetchRandomPhotos()
    }
    private fun initViews(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        binding.recyclerView.adapter = PhotoAdapter()
    }
    private fun bindViews(){
        binding.searchEditTv.setOnEditorActionListener { editText, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                currentFocus?.let{view->
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputMethodManager?.hideSoftInputFromWindow(view.windowToken,0)

                    view.clearFocus() //editText에 포커스 받고있는 것을 clear해줌으로서 커서 깜박임이 안보이게
                }
                fetchRandomPhotos(editText.text.toString())
            }
            true
            //true - 함수가 소비했으니 더이상 소비하지 말아라
            //false - 함수가 소비하지 않았으니 다른사항에서 소비할 것이있다면 소비하라
        }
        binding.refreshLayout.setOnRefreshListener {
            fetchRandomPhotos(binding.searchEditTv.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
    private fun fetchRandomPhotos(query: String? = null)= scope.launch{
        Repository.getRandomPhotos(query)?.let {photos->
            (binding.recyclerView.adapter as? PhotoAdapter)?.apply {
                this.photos = photos
                notifyDataSetChanged()
            }
            binding.refreshLayout.isRefreshing = false //서치가 끝나면 리프레시 프로그래스 바 숨김
        }
    }
}