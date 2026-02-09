package com.example.crypto.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crypto.R
import com.example.crypto.databinding.TopCurrencyLayoutBinding
import com.example.crypto.fragment.WatchListFragmentDirections
import com.example.crypto.models.CryptoCurrency
import androidx.navigation.findNavController
import java.util.Locale

class TopMarketAdapter(var context : Context, val list:List<CryptoCurrency>) :RecyclerView.Adapter<TopMarketAdapter.TopMarketViewHolder>(){

    class TopMarketViewHolder(view:View) : RecyclerView.ViewHolder(view){
        var binding=TopCurrencyLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMarketViewHolder {
       return TopMarketViewHolder(LayoutInflater.from(context).inflate(R.layout.top_currency_layout,parent,false))
    }

    override fun onBindViewHolder(holder: TopMarketViewHolder, position: Int) {

        val item = list[position]

        holder.binding.topCurrencyNameTextView.text = item.name

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.topCurrencyImageView)

        val change = item.quotes[0].percentChange24h

        holder.binding.topCurrencyChangeTextView.setTextColor(
            ContextCompat.getColor(
                context,
                if (change > 0) R.color.green else R.color.red
            )
        )

        holder.binding.topCurrencyChangeTextView.text =
            String.format(
                Locale.US,
                if (change > 0) "+ %.02f%%" else "%.02f%%",
                change
            )

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(
                WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(item)
            )
        }
    }
    override fun getItemCount(): Int {
       return list.size
    }


}