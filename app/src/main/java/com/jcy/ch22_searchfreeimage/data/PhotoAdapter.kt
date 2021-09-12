package com.jcy.ch22_searchfreeimage.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jcy.ch22_searchfreeimage.R
import com.jcy.ch22_searchfreeimage.data.models.PhotoResponse
import com.jcy.ch22_searchfreeimage.databinding.ItemPhotoBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var photos: List<PhotoResponse> = emptyList()

    var onClickPhoto: (PhotoResponse)->Unit={}
    inner class ViewHolder(
        private val binding : ItemPhotoBinding
        ):  RecyclerView.ViewHolder(binding.root){

            init {
                binding.root.setOnClickListener {
                    onClickPhoto(photos[adapterPosition])
                }
            }
        fun bind(photo: PhotoResponse){

            val dimenstionRatio = photo.height / photo.width.toFloat()
            //스크린의 가로 사이즈 가져오기
            val targetWidth = binding.root.resources.displayMetrics.widthPixels -
            (binding.root.paddingStart + binding.root.paddingEnd)
            val targetHeight = (targetWidth * dimenstionRatio).toInt()

            binding.contentsContainer.layoutParams =
                binding.contentsContainer.layoutParams.apply {
                    height = targetHeight
                }

            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .thumbnail(
                    Glide.with(binding.root)
                        .load(photo.urls?.thumb)
                        .transition(DrawableTransitionOptions.withCrossFade())
                )
                .into(binding.photoImageView)
            Glide.with(binding.root)
                .load(photo.user?.profileImageUrls?.small)
                .placeholder(R.drawable.shape_profile_placeholder)
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.profileImageView)

            if(photo.user?.name.isNullOrBlank()){
                binding.authorTv.visibility = View.GONE
            }else{
                binding.authorTv.visibility = View.VISIBLE
                binding.authorTv.text = photo.user?.name
            }

            if(photo.description.isNullOrBlank()){
                binding.descriptionTv.visibility = View.GONE
            }else{
                binding.descriptionTv.visibility = View.VISIBLE
                binding.descriptionTv.text = photo.description
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size
}