package pe.edu.upc.rickagendaapp.ui.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.fragment_characters.view.*
import kotlinx.android.synthetic.main.prototype_character.view.*
import pe.edu.upc.rickagendaapp.R
import pe.edu.upc.rickagendaapp.data.entities.Character

class CharactersAdapter(private val  listener: CharacterItemListener): RecyclerView.Adapter<CharacterViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private val items = ArrayList<Character>()

    fun setItems(items: ArrayList<Character>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.prototype_character,parent,false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) = holder.bind(items[position])

}

class CharacterViewHolder(itemView: View, private val listener: CharactersAdapter.CharacterItemListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var character: Character

    init{
        itemView.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Character){
        this.character = item
        itemView.name_tv.text = item.name
        itemView.species_and_status_tv.text = """${item.species} - ${item.status}"""

        Glide.with(itemView)
                .load(item.image)
                .transform(CircleCrop())
                .into(itemView.image_iv)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id)
    }
}
