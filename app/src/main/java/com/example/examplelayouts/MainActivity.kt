package com.example.examplelayouts

import android.content.res.ColorStateList
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
    private var flag = false
    private val continentsList =
        listOf<String>("Asia", "Africa", "Antarctica", "South America", "North America", "Europe")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tv.setOnClickListener {
                setAdapter()
            }

            menu.setEndIconOnClickListener {
                setAdapter()
            }
        }
    }

    private fun setAdapter() {
        binding.apply {
            if (rv.visibility == View.GONE) {
                flag = true
                rv.visibility = View.VISIBLE
                menu.endIconDrawable =
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_down_24)
                menu.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.purple_700)))
            } else {
                flag = false
                rv.visibility = View.GONE
                menu.endIconDrawable =
                    resources.getDrawable(R.drawable.ic_baseline_arrow_drop_up_24)
            }

            val adapter = CustomAdapter(this@MainActivity, continentsList)
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = adapter
        }
    }

    override fun onClickItem(position: Int, list: List<String>) {
        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
        binding.tv.setText(list[position] + " is at position: " + position)
        binding.menu.hintTextColor = ColorStateList.valueOf(resources.getColor(R.color.purple_700))
        binding.menu.hint = "Continents"
        binding.rv.visibility = View.GONE
    }
}

class CustomAdapter(
    private val listener: OnItemClick,
    private val list: List<String>
) :
    RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = CustomAdapterBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_adapter, parent, false)
        )

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            listener.onClickItem(position, list)
        }
        holder.onBind(position, list)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClick {
        fun onClickItem(position: Int, list: List<String>)
    }
}

class CustomViewHolder(itemView: CustomAdapterBinding) :
    RecyclerView.ViewHolder(itemView.root) {

    val binding = itemView
    fun onBind(position: Int, list: List<String>) {
        binding.textView.text = list[position]
    }
}