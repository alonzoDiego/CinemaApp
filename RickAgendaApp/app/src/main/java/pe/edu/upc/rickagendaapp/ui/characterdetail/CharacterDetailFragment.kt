package pe.edu.upc.rickagendaapp.ui.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_detail.*
import pe.edu.upc.rickagendaapp.R
import pe.edu.upc.rickagendaapp.data.entities.Character
import pe.edu.upc.rickagendaapp.utils.Resource

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers(){
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    characterBind(it.data!!)
                    progress_detail_bar.visibility = View.GONE
                    character_detail_cl.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    progress_detail_bar.visibility = View.VISIBLE
                    character_detail_cl.visibility = View.GONE
                }
            }
        })
    }

    private fun characterBind(character: Character){
        name_detail_tv.text = character.name
        species_detail_tv.text = character.species
        status_detail_tv.text = character.status
        gender_detail_tv.text = character.gender
        Glide.with(image_detail_iv)
            .load(character.image)
            .transform(CircleCrop())
            .into(image_detail_iv)
    }
}