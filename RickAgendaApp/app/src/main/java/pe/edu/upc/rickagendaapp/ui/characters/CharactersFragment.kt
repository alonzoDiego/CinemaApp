package pe.edu.upc.rickagendaapp.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_characters.*
import pe.edu.upc.rickagendaapp.R
import pe.edu.upc.rickagendaapp.utils.Resource

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharactersAdapter.CharacterItemListener {

    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var adapterCharacter: CharactersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        characters_rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapterCharacter = CharactersAdapter(this@CharactersFragment)
            adapter = adapterCharacter
        }
    }

    private fun setupObservers(){
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    if(!it.data.isNullOrEmpty()) adapterCharacter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                Resource.Status.LOADING -> progress_bar.visibility =View.VISIBLE
            }
        })
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment, bundleOf("id" to characterId)
        )
    }
}