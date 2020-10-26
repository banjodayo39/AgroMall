package com.banjodayo.agromall.views

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.banjodayo.agromall.R
import com.banjodayo.agromall.adapter.FarmerPaginatedAdapter
import com.banjodayo.agromall.data.Farmer
import com.banjodayo.agromall.data.GeoCoordinate
import com.banjodayo.agromall.databinding.FragmentFarmerListBinding
import com.banjodayo.agromall.utils.EXTRA_LOG_OUT
import com.banjodayo.agromall.utils.Status
import com.banjodayo.agromall.viewmodel.FarmerViewModel
import kotlinx.android.synthetic.main.activity_farmer.*
import kotlinx.android.synthetic.main.activity_loading.*
import kotlinx.android.synthetic.main.content_farmer.view.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FarmerList : Fragment() {

    private lateinit var binding: FragmentFarmerListBinding
    private  val viewModel : FarmerViewModel by sharedViewModel()
    private  val farmerAdapter = FarmerPaginatedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_farmer_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences(
            getString(R.string.shared_pref_file_key), Context.MODE_PRIVATE)

        viewModel.farmerLiveData.observe(viewLifecycleOwner){
            farmerAdapter.submitList(it)
        }
        binding.farmerListAdapter.apply {
            adapter = farmerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        farmerAdapter.setOnItemClick(object: FarmerPaginatedAdapter.PaginatedAdapterListener{
            override fun openMap(farmer: Farmer) {
               requireActivity().layout_content.progress_bar.visibility = View.VISIBLE
                    getGeoLocation(farmer)
            }
        })

        binding.logoutButton.setOnClickListener {
            with(sharedPrefs.edit()) {
                putBoolean("isLogin", false).apply()
                val intent = Intent(requireContext(), LoadingActivity::class.java)
                intent.putExtra(EXTRA_LOG_OUT, 1)
                startActivity(intent)
            }
        }

        if(requireActivity().fab_button != null) {
            requireActivity().fab_button.setOnClickListener {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    private suspend fun getLoc(farmer: Farmer) : Array<Double>{
        var geoArray : Array<Double> = emptyArray()
        val waitFor = CoroutineScope(Dispatchers.IO).async {
                val geoCoder = Geocoder(context, Locale.getDefault())
                Log.e("address given", farmer.address)
                try {
                    var addressList = geoCoder.getFromLocationName(farmer.address + " Nigeria", 1)
                    if (addressList == null) {
                        addressList = geoCoder.getFromLocationName("Nigeria", 1)
                    }
                    if (addressList.size > 0) {
                        val address = addressList[0] as Address
                        Log.e("address", address.toString())
                        latitude = address.latitude
                        longitude = address.longitude
                        geoArray = arrayOf(latitude, longitude)
                        return@async geoArray
                    } else {
                        print("This is null")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }
        waitFor.await()
        return geoArray
    }

    private fun getGeoLocation(farmer: Farmer){
        GlobalScope.launch(Dispatchers.Main) {
            val value =
                withContext(Dispatchers.IO) {
                    getLoc(farmer)
                }
            requireActivity().layout_content.progress_bar.visibility = View.GONE
            if(value.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.err_message), Toast.LENGTH_SHORT).show()
            } else if (value.isNotEmpty()) {
                val geoCoordinate = GeoCoordinate(value[0], value[1])
                val intent = MapActivity.newIntent(requireContext(), farmer, geoCoordinate)
                startActivity(intent)
            }
        }
    }

    companion object{
        private var latitude : Double = 0.0
        private var longitude : Double = 0.0
    }
}