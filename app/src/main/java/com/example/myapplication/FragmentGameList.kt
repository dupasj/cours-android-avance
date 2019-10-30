package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.data.Game
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_list.*
import kotlinx.android.synthetic.main.fragment_game_view.*
import kotlinx.android.synthetic.main.item_game.view.*

class FragmentGameList() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler.layoutManager = LinearLayoutManager(this.activity)

        val queue = Volley.newRequestQueue(this.activity)
        val stringRequest = StringRequest(
            Request.Method.GET, "https://my-json-server.typicode.com/bgdom/cours-android/games",
            Response.Listener<String> { response ->


                Array<Game>::class.java

                val games = Gson().fromJson(response, Array<Game>::class.java);
                val obj = object : GameAdaptaterInterface {
                    override val games: Array<Game> = games

                    override fun open(game: Game) {
                        this@FragmentGameList
                            .fragmentManager!!
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frame,FragmentGameView(game))
                            .commit()
                    }
                }

                recycler.adapter = ReposAdapter(obj)
            },
            Response.ErrorListener {

            })

        queue.add(stringRequest)

        super.onViewCreated(view, savedInstanceState)
    }
}
