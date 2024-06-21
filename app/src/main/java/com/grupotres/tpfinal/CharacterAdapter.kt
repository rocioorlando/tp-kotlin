import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grupotres.tpfinal.Character
import com.grupotres.tpfinal.R

class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.textName.text = character.name
        holder.textStatus.text = "Status: ${character.status}"
        holder.textSpecies.text = "Species: ${character.species}"
        holder.textType.text = "Type: ${character.type}"
        holder.textGender.text = "Gender: ${character.gender}"

        // Cargar la imagen utilizando Glide
        Glide.with(holder.itemView)
            .load(character.image)
            .into(holder.imageCharacter)
    }


    override fun getItemCount(): Int {
        return characters.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)
        val textSpecies: TextView = itemView.findViewById(R.id.textSpecies)
        val textType: TextView = itemView.findViewById(R.id.textType)
        val textGender: TextView = itemView.findViewById(R.id.textGender)
        val imageCharacter: ImageView = itemView.findViewById(R.id.imageCharacter)
    }

}
