package com.angelo.pokedex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angelo.pokedex.R
import com.angelo.pokedex.model.Pokemon
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ListaPokemonAdapter(val context:Context):RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>() {


    var viewHolder:ViewHolder? = null
    var itemsPokemon:ArrayList<Pokemon>?=null

    init {
        itemsPokemon = ArrayList()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaPokemonAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent,false)
        viewHolder = ViewHolder(view)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return itemsPokemon!!.size
    }

    override fun onBindViewHolder(holder: ListaPokemonAdapter.ViewHolder, position: Int) {
        val pokemon = itemsPokemon!![position]
        holder.txtPokemonName.text = pokemon.name
        Glide.with(context)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.getNumero()}.png")
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.pokemonImage)

    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val txtPokemonName:TextView = view.findViewById(R.id.txtPokemonName)
        val pokemonImage:ImageView = view.findViewById(R.id.pokemonImage)
    }

    fun addListPokemon(listaPokemon:ArrayList<Pokemon>){
        itemsPokemon!!.addAll(listaPokemon)
        //actualizamos pantalla
        notifyDataSetChanged()
    }

}