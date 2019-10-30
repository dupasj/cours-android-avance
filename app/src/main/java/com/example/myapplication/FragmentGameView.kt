package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.data.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_game_view.*


class FragmentGameView(private val game: Game) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get().load(this.game.img).into(image);

        val pref = this.activity!!.getSharedPreferences("navigator", Context.MODE_PRIVATE);
        val value = pref.getBoolean("value",false);

        pref.edit().putBoolean("value",!value).apply();

        description.text = game.description;
        name.text = game.name;



        if (value){
            button.text = getResources().getString(R.string.button_open_navigator);
            button.setOnClickListener{
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(game.link)
                )
                startActivity(browserIntent)
            }
        }else{
            button.text = getResources().getString(R.string.button_open_webview);
            button.setOnClickListener{
                val browserIntent = Intent(
                    this.context,
                    ActivityWebView::class.java
                );

                val bundle = Bundle();
                browserIntent.putExtra("url",Uri.parse(game.link).toString())

                startActivity(browserIntent)
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }
}
