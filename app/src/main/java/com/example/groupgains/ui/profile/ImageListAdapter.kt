import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R

interface OnImageClickListener {
    fun onImageClick(imageResId: Int)
}
class ImageListAdapter(private val imageList: List<Int>, private val listener: OnImageClickListener) : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {
    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // ...
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
    return ImageViewHolder(view)
}

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.image_view)
        imageView.setImageResource(imageList[position])
    }
}