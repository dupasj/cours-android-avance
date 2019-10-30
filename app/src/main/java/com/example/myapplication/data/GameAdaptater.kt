package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_game.view.*
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


class ReposAdapter(private val communication: GameAdaptaterInterface) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        ViewHolder(holder.itemView).bind(communication.games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(game: Game){
            itemView.setOnClickListener {
                this@ReposAdapter.communication.open(game);
            }

            itemView.name.text = game.name
            Picasso.get().load(game.img).into(itemView.image);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposAdapter.ViewHolder {
        val element = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(element)
    }

    override fun getItemCount() = this.communication.games.size
}