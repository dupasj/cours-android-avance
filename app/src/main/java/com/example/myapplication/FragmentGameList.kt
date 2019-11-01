package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.data.Game
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_game_list.*


class FragmentGameList() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    fun load(){
        val queue = Volley.newRequestQueue(this.activity)
        val stringRequest = StringRequest(
            Request.Method.GET, "https://my-json-server.typicode.com/bgdom/cours-android/games",
            Response.Listener<String> { response ->
                val games = Gson().fromJson(response, Array<Game>::class.java);
                val obj = object : GameAdaptaterInterface {
                    override val games: Array<Game> = games

                    override fun open(game: Game) {
                        this@FragmentGameList
                            .fragmentManager!!
                            .beginTransaction()
                            .addToBackStack(null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .replace(R.id.frame,FragmentGameView(game))
                            .commit()
                    }
                }

                recycler.adapter = ReposAdapter(obj)

                swipe_reload.isRefreshing = false;
            },
            Response.ErrorListener {

            })

        queue.add(stringRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.layoutManager = LinearLayoutManager(this.activity)

        swipe_reload.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                this@FragmentGameList.load();
            }
        })

        this.load();

        super.onViewCreated(view, savedInstanceState)
    }
}
