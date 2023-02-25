package com.wooriyo.seekhan.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wooriyo.seekhan.databinding.ListHistoryBinding
import com.wooriyo.seekhan.main.GoodsDetailActivity
import com.wooriyo.seekhan.model.HListDTO

class GoodsAdapter(private val dataSet: ArrayList<HListDTO>,val context: Context): RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HListDTO) {
            binding.run {
                tvTitle.text = data.title
                tvGoodsStatus.text = data.goods_status
                tvDate.text = data.regdt

                constraint.setOnClickListener {
                    val intent = Intent(context, GoodsDetailActivity::class.java)
                    intent.putExtra("idx", data.idx)
                    intent.putExtra("title", data.title)
                    intent.putExtra("method", data.method)
                    intent.putExtra("hsCode", data.hscode)
                    intent.putExtra("status", data.goods_status)
                    intent.putExtra("savemethod", data.savemethod)
                    intent.putExtra("weight", data.weight)
                    intent.putExtra("cbm", data.cbm)
                    intent.putExtra("goodstype", data.goodstype)
                    intent.putExtra("cmpname", data.cmpname)
                    intent.putExtra("regdt", data.regdt)
                    context.startActivity(intent)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}