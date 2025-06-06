package edu.unikom.suweorajamuhamid.ui.barang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.unikom.suweorajamuhamid.databinding.FragmentBarangBinding // Create this layout
import edu.unikom.suweorajamuhamid.viewmodel.BarangViewModel

@AndroidEntryPoint
class BarangFragment : Fragment() {

    private var _binding: FragmentBarangBinding? = null
    private val binding get() = _binding!!

    private val barangViewModel: BarangViewModel by viewModels()
    private lateinit var barangAdapter: BarangAdapter // Create this Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        barangViewModel.allBarang.observe(viewLifecycleOwner) { barangList ->
            barangList?.let {
                barangAdapter.submitList(it)
            }
        }

        binding.fabTambahBarang.setOnClickListener { // Assuming a FAB with this ID
            // Navigate to BarangFormFragment to add a new item
            // Pass -1 or no argument to indicate new item
            val action = BarangFragmentDirections.actionBarangFragmentToBarangFormFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        barangAdapter = BarangAdapter { barang ->
            // Handle item click: Navigate to BarangFormFragment to edit
            val action = BarangFragmentDirections.actionBarangFragmentToBarangFormFragment(barangId = barang.id)
            findNavController().navigate(action)
        }
        binding.recyclerViewBarang.apply { // Assuming a RecyclerView with this ID
            adapter = barangAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}