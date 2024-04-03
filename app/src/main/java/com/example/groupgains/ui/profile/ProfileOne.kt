package com.example.groupgains.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.groupgains.R
import com.example.groupgains.data.Workout
import com.example.groupgains.databinding.Profile1Binding
import android.content.Intent
import android.app.Activity
import android.widget.ImageView
import com.example.groupgains.databinding.FragmentImageListBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.databinding.ItemImageBinding
import android.widget.RadioGroup

class ProfileOne: Fragment() {
    private var myImage: ImageView? = null

    private var _binding: Profile1Binding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by activityViewModels()

    companion object {
        private const val PICK_IMAGE_REQUEST = 100
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            myImage?.setImageURI(imageUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = Profile1Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val parentActivity = requireActivity()
        viewModel.initializeActivity(parentActivity)

        binding.buttonChooseImage.setOnClickListener {
            openFileChooser()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val workoutLinearLayout: LinearLayout = binding.workoutLinearLayout

        myImage = view.findViewById(R.id.image_view)

        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> myImage?.setImageResource(R.drawable.image1)
                R.id.radioButton2 -> myImage?.setImageResource(R.drawable.image2)
                R.id.radioButton3 -> myImage?.setImageResource(R.drawable.image3)
            }
        }
    }
    
}

class ImageListFragment : Fragment(), ImageListAdapter.OnImageClickListener {
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val imageList = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3) // replace with your actual images

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImageListAdapter(imageList, this)
        binding.recyclerView.adapter = adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun onImageClick(imageResId: Int) {
        // handle image click
        // you can use a shared ViewModel or a callback interface to pass the selected image back to ProfileOne fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ImageListAdapter(private val imageList: List<Int>, private val listener: OnImageClickListener) : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {
    interface OnImageClickListener {
        fun onImageClick(imageResId: Int)
    }

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.imageView.setImageResource(imageList[position])
        holder.itemView.setOnClickListener { listener.onImageClick(imageList[position]) }
    }

    override fun getItemCount() = imageList.size
}