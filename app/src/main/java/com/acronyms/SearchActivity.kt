package com.acronyms

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acronyms.adapter.AcronymAdapter
import com.acronyms.api.ApiClient
import com.acronyms.api.ApiInterface
import com.acronyms.model.AcronymResponse
import com.acronyms.utils.isInternetConnected
import com.acronyms.utils.showErrorDialog
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
    }

    /**
     * API call to retrieve list and populate with RecyclerView on success
     */
    private fun getList(term: String) {
        rlProgress.visibility = View.VISIBLE

        val apiInterface = ApiClient.client!!.create(ApiInterface::class.java)

        val call = apiInterface?.getDetails(term)
        call?.enqueue(object : Callback<List<AcronymResponse>> {
            override fun onResponse(
                call: Call<List<AcronymResponse>>,
                response: Response<List<AcronymResponse>>
            ) {
                rlProgress.visibility = View.GONE
                val list = response.body()
                if (list != null && list.isNotEmpty() && list[0].lfs!!.isNotEmpty()) {
                    supportActionBar?.title =
                        getString(R.string.title, getString(R.string.app_name), list[0].sf)
                    setupData(list[0].lfs!!)
                } else {
                    showErrorDialog(getString(R.string.NO_DATA))
                    rvList.visibility = View.GONE
                    llWelcomeScreen.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<AcronymResponse>>, t: Throwable) {
                rlProgress.visibility = View.GONE
                Log.i("TAG", "Error: $t")
                showErrorDialog(getString(R.string.SERVER_ERROR))
            }
        })
    }

    /**
     * Setup RecyclerView with data retrieved from API
     */
    private fun setupData(lfList: List<AcronymResponse.Lf>) {
        llWelcomeScreen.visibility = View.GONE
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        rvList.layoutManager = manager
        rvList.adapter = AcronymAdapter(this, lfList)
        rvList.visibility = View.VISIBLE
    }

    /**
     * Setup menu items on Toolbar
     *
     * @param menu
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.menuSearch)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Type any word..."
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (isInternetConnected()) {
                    getList(query)
                } else {
                    showErrorDialog(getString(R.string.CONNECTION_ERROR))
                }

                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }
}