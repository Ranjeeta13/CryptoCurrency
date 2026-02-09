package com.example.crypto.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crypto.R
import com.example.crypto.databinding.CurrencyItemLayoutBinding
import com.example.crypto.fragment.HomeFragmentDirections
import com.example.crypto.fragment.MarketFragmentDirections
import com.example.crypto.fragment.WatchListFragmentDirections
import com.example.crypto.models.CryptoCurrency
import java.util.Locale

class MarketAdapter(var context: Context, var list: List<CryptoCurrency>,  var type: String) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>(){


     class MarketViewHolder(view: View) : RecyclerView.ViewHolder(view){
         var binding=CurrencyItemLayoutBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        return MarketViewHolder(LayoutInflater.from(context).inflate(R.layout.currency_item_layout,parent,false))
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {

        val item = list[position]

        holder.binding.currencyNameTextView.text = item.name
        holder.binding.currencySymbolTextView.text = item.symbol

        Glide.with(context)
            .load("https://s2.coinmarketcap.com/static/img/coins/64x64/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyImageView)

        Glide.with(context)
            .load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/${item.id}.png")
            .thumbnail(Glide.with(context).load(R.drawable.spinner))
            .into(holder.binding.currencyChartImageView)

        holder.binding.currencyPriceTextView.text =
            String.format(Locale.US, "$%.02f", item.quotes[0].price)

        val change = item.quotes[0].percentChange24h

        holder.binding.currencyChangeTextView.setTextColor(
            ContextCompat.getColor(
                context,
                if (change > 0) R.color.green else R.color.red
            )
        )

        holder.binding.currencyChangeTextView.text =
            String.format(
                Locale.US,
                if (change > 0) "+ %.02f%%" else "%.02f%%",
                change
            )

        holder.itemView.setOnClickListener {
            when (type) {
                "home" -> findNavController(it)
                    .navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item))
                "market" -> findNavController(it)
                    .navigate(MarketFragmentDirections.actionMarketFragmentToDetailsFragment(item))
                else -> findNavController(it)
                    .navigate(WatchListFragmentDirections.actionWatchListFragmentToDetailsFragment(item))
            }
        }
    }


    override fun getItemCount(): Int {
       return list.size
    }

    fun updateData(newData: List<CryptoCurrency>) {
        val diffCallback = MarketDiffCallback(list, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        list = newData
        diffResult.dispatchUpdatesTo(this)
    }

    class MarketDiffCallback(
        private val oldList: List<CryptoCurrency>,
        private val newList: List<CryptoCurrency>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos].id == newList[newPos].id
        }

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos] == newList[newPos]
        }
    }


}