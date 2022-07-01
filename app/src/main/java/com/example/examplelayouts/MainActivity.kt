package com.example.examplelayouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examplelayouts.databinding.ActivityMainBinding
import com.example.examplelayouts.databinding.CustomAdapterBinding

class MainActivity : AppCompatActivity(), CustomAdapter.OnItemClick {
    lateinit var binding: ActivityMainBinding
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tv.setOnClickListener {
                if (rv.visibility == View.GONE) {
                    flag = true
                    rv.visibility = View.VISIBLE
                } else {
                    flag = false
                    rv.visibility = View.GONE
                }

                val adapter = CustomAdapter(this@MainActivity)
                rv.layoutManager = LinearLayoutManager(this@MainActivity)
                rv.adapter = adapter

                menu.setOnFocusChangeListener { view, b ->
                    if (b) {

                    } else {

                    }
                }
            }
        }
    }

    override fun onClickItem(position: Int) {
        binding.tv.setText(resources.getString(R.string.app_name) + " " + position)
        binding.menu.isHintEnabled = true
    }
}

class CustomAdapter(private val listener: OnItemClick) : RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = CustomAdapterBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_adapter, parent, false)
        )

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            listener.onClickItem(position)
        }
    }

    override fun getItemCount(): Int = 10

    interface OnItemClick {
        fun onClickItem(position: Int)
    }
}

class CustomViewHolder(itemView: CustomAdapterBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    val binding = itemView
}