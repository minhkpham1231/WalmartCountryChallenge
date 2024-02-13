package com.example.walmartcountrychallenge.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartcountrychallenge.databinding.CountryItemBinding
import com.example.walmartcountrychallenge.model.Country

class CountryListAdapter : ListAdapter<Country, CountryListAdapter.ItemViewholder>(DiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(private val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            binding.countryTitle.text = item.name
            binding.countryCode.text = item.code
            binding.countryCapital.text = item.capital
            item.region?.let {
                val builder = StringBuilder()
                builder.append(item.name).append(", ").append(item.region)
                binding.countryTitle.text = builder.toString()
            }
        }
    }
}



class DiffCallback : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }
}