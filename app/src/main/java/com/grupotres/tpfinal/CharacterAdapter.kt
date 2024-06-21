import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grupotres.tpfinal.Character
import com.grupotres.tpfinal.R

// Adaptador para el RecyclerView que muestra la lista de personajes
class CharacterAdapter(private val characters: List<Character>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    //Componentes Principales de un RecyclerView
    //RecyclerView: El contenedor principal que contiene y administra las vistas de los elementos de la lista.
    //Adapter: Un adaptador que proporciona las vistas necesarias para los elementos del conjunto de datos. Es responsable de crear nuevas vistas y enlazarlas con los datos.
    //ViewHolder: Un patrón de diseño utilizado para almacenar las referencias a las vistas dentro de un elemento de lista, evitando la búsqueda repetida de vistas y mejorando así el rendimiento.
    //LayoutManager: Controla cómo se organizan los elementos dentro del RecyclerView. Puede ser LinearLayoutManager, GridLayoutManager o StaggeredGridLayoutManager.

    // Inflar el diseño de la tarjeta de personaje y crear un ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_character, parent, false)
        return ViewHolder(view)
    }

    // Enlazar los datos del personaje a las vistas en el ViewHolder
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

    // Devolver el número de personajes en la lista
    override fun getItemCount(): Int {
        return characters.size
    }

    // ViewHolder que contiene las vistas para un solo elemento de personaje
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)      // Vista de texto para el nombre del personaje
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)  // Vista de texto para el estado del personaje
        val textSpecies: TextView = itemView.findViewById(R.id.textSpecies)// Vista de texto para la especie del personaje
        val textType: TextView = itemView.findViewById(R.id.textType)      // Vista de texto para el tipo del personaje
        val textGender: TextView = itemView.findViewById(R.id.textGender)  // Vista de texto para el género del personaje
        val imageCharacter: ImageView = itemView.findViewById(R.id.imageCharacter) // Vista de imagen para la imagen del personaje
    }
}
