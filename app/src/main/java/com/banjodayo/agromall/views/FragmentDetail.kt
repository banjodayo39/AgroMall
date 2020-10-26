package com.banjodayo.agromall.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.banjodayo.agromall.R
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.databinding.FragmentDetailBinding
import com.banjodayo.agromall.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_farmer.*
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FragmentDetail : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private  val viewModel : FarmerViewModel by sharedViewModel()
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
        set(value) {
            field.cancel("Reset scope")
            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().fab_button.visibility = View.GONE

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.layoutFarmerDetail.saveButton.setOnClickListener {
            val run = false
            with(binding.layoutFarmerDetail){
                val name  = farmerNameEditTv.text.toString()
                val surname = farmerSurnameEditTv.text.toString()
                val location = farmerLocationEditTv.text.toString()
                val state = farmerStateEditTv.text.toString()
                val random = Random(10)
                val id = name + surname  + random.nextInt().toString()
                when {
                    name.isEmpty() -> {
                        showToast("First Name")
                    }
                    surname.isEmpty() -> {
                        showToast("Surname Name")
                    }
                    location.isEmpty() -> {
                        showToast("Surname Name")
                    }
                    state.isEmpty() -> {
                        showToast("Surname Name")
                    }
                    else -> {
                        val farmer = Farmer(id, name, surname, location, state, state, "")
                        scope = CoroutineScope(Dispatchers.IO)
                        scope.launch {
                            viewModel.saveDb(farmer)
                        }
                        Toast.makeText(context, "$name information saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

     private fun showToast(name: String){
         Toast.makeText(context, "$name is missing", Toast.LENGTH_SHORT).show()
     }
}