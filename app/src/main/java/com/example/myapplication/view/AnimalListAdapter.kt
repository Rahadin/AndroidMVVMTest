package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemAnimalBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*


class AnimalListAdapter(private val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    //    Untuk update data
    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    class AnimalViewHolder(var view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root)

    //    Menyambungkan item ke list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_animal, parent, false)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent,false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        //  Yang sudah disambungin ITEMnya, maka disambungkan dengan datanya
//        holder.view.animalName.text = animalList[position].name
//        holder.view.animalImage.loadImage(animalList[position].imageUrl,
//            getProgressDrawable(holder.view.context))

        holder.view.animal = animalList[position]

        //  Send Animal detail to Detail Fragment
//        holder.view.animalLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionDetail(animalList[position])
//            Navigation.findNavController(holder.view).navigate(action)
//        }

        // 'this' comes from AnimalClickListener
        holder.view.listener = this
    }

    // Data Binding click listener
    override fun onCLick(v: View) {
        for(animal in animalList) {
            if (v.tag == animal.name) {
                val action = ListFragmentDirections.actionDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }
}