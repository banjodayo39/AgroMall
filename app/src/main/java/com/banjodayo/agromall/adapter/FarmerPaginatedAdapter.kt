package com.banjodayo.agromall.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.banjodayo.agromall.R
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.databinding.ItemFarmerRecordBinding
import com.banjodayo.agromall.utils.loadCircularImage

class FarmerPaginatedAdapter():
    PagedListAdapter<Farmer, FarmerPaginatedAdapter.FarmerHolder>(DIFF_CALLBACK) {

    private lateinit var listener : PaginatedAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): FarmerHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
      return  FarmerHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_farmer_record,
      parent, false))
    }

    override fun onBindViewHolder(holder: FarmerHolder, position: Int) {
        val farmer : Farmer? = getItem(position)
        holder.bind(farmer!!)
    }

    inner class FarmerHolder(
        private val binding: ItemFarmerRecordBinding
    ) : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(farmer: Farmer){
            val baseImageUrl : String =
                """https://s3-eu-west-1.amazonaws.com/agromall-storage/${farmer.passportPhoto}"""
            binding.root.setOnClickListener {
                listener.openMap(farmer)
            }
            loadCircularImage(
                context, binding.farmerImageView,
                baseImageUrl, R.drawable.ic_profile_image_placeholder
            )
            binding.farmerName.text = context.getString(
                R.string.username,
                farmer.first_name,
                farmer.surname
            )
            binding.farmerLocation.text = context.getString(
                R.string.username,
                farmer.address,
                farmer.state
            )
        }

    }

    fun setOnItemClick(initListener: PaginatedAdapterListener){
        listener = initListener
    }

    interface PaginatedAdapterListener{
        fun openMap(farmer: Farmer)
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Farmer>() {
            override fun areContentsTheSame(oldItem: Farmer, newItem: Farmer): Boolean = oldItem == newItem

            override fun areItemsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
                return oldItem.farmer_id == oldItem.farmer_id

            }
        }
    }

}