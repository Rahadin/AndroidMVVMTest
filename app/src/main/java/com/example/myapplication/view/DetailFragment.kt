package com.example.myapplication.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.model.Animal
import com.example.myapplication.model.AnimalPallete
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    var animal: Animal? = null
    //  to enable Data Binding class, add
    //  buildFeatures {
    //      dataBinding true
    //  }
    //  In build.gradle ref https://stackoverflow.com/questions/39483094/data-binding-class-not-generated
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //   Receive Animal detail data
        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).Animal
        }

//        context?.let {
//            dataBinding.animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
//        }

//        animalName.text = animal?.name
//        animalLocation.text = animal?.location
//        animalLifespan.text = animal?.lifeSpan
//        animalDiet.text = animal?.diet

        animal?.imageUrl?.let {
            setupBackgroundColor(it)
        }

        dataBinding.animal = animal
    }

    fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //  Library to apply Android Design, on this function it create background color on detail fragment equal to image shown
                    Palette.from(resource)
                        .generate() {palette: Palette? ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
//                            dataBinding.animalLayout.setBackgroundColor(intColor)
                            dataBinding.palette = AnimalPallete(intColor)
                        }
                }

            })
    }
}