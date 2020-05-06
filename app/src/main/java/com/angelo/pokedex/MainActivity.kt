package com.angelo.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angelo.pokedex.adapter.ListaPokemonAdapter
import com.angelo.pokedex.model.PokemonResult
import com.angelo.pokedex.pokedexapi.PokedexapiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit:Retrofit
    private lateinit var recyclerView:RecyclerView
    private lateinit var listaPokemonAdapter: ListaPokemonAdapter
    private var offset:Int ?= null
    private var aptoParaCargar:Boolean?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RW_list_pokemon)
        listaPokemonAdapter = ListaPokemonAdapter(this)
        recyclerView.adapter = listaPokemonAdapter
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(this,3)
        recyclerView.layoutManager = layoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy>0){
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    if((visibleItemCount+pastVisibleItems)>=totalItemCount){
                        aptoParaCargar = false
                        offset = offset!!.plus(20)
                        obtenerDatos(offset!!)
                    }
                }
            }
        })

        retrofit = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        aptoParaCargar = true
        offset = 0
        obtenerDatos(offset!!)
    }

    private fun obtenerDatos(offset:Int) {
        val service = retrofit.create(PokedexapiService::class.java)
        val pokemonRespuestaCall = service.obtenerListaPokemon(offset ,20)
        //Manejar la response
        pokemonRespuestaCall.enqueue(object :Callback<PokemonResult>{
            override fun onFailure(call: Call<PokemonResult>, t: Throwable) {
                aptoParaCargar = true
                Log.d("onFailure: ",t.message!!)
            }

            override fun onResponse(call: Call<PokemonResult>, response: Response<PokemonResult>) {
                aptoParaCargar = true
                if(!response.isSuccessful){
                    Log.d("onResponse: ",response.errorBody().toString())
                }else{
                    //Toast.makeText(applicationContext,"Datos traidos con exito",Toast.LENGTH_LONG).show()
                    val pokemonRespuesta = response.body()
                    val listaPokemon = pokemonRespuesta?.results

                    listaPokemonAdapter.addListPokemon(listaPokemon!!)
                }
            }
        })
    }
}
