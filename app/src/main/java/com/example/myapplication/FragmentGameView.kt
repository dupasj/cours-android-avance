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

    private var value: Boolean? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get().load(this.game.img).into(image);

        val pref = this.activity!!.getSharedPreferences("navigator", Context.MODE_PRIVATE);
        this.value = pref.getBoolean("value",false);

        this.updateButton();

        button.setOnClickListener{
            this.buttonClick();
        }

        description.text = game.description;
        name.text = game.name;


        super.onViewCreated(view, savedInstanceState)
    }

    fun updateButton(){
        if (this.value!!){
            button.text = getResources().getString(R.string.button_open_navigator);
        }else{
            button.text = getResources().getString(R.string.button_open_webview);
        }
    }

    fun buttonClick(){
        if (this.value!!){
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(game.link)
            )
            startActivity(browserIntent)
        }else{
            val browserIntent = Intent(
                this.context,
                ActivityWebView::class.java
            );

            val bundle = Bundle();
            browserIntent.putExtra("url",Uri.parse(game.link).toString())

            startActivity(browserIntent)
        }

        this.value = !this.value!!;

        this.updateButton();

        val pref = this.activity!!.getSharedPreferences("navigator", Context.MODE_PRIVATE);
        pref.edit().putBoolean("value",!this.value!!).apply();
    }
}
