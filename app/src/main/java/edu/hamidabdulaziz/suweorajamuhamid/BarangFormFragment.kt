// package edu.unikom.suweorajamuhamid.ui.barang (pastikan package ini benar)

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load // Example using Coil for image loading
import dagger.hilt.android.AndroidEntryPoint
import edu.unikom.suweorajamuhamid.R
import edu.unikom.suweorajamuhamid.data.db.entity.Barang
import edu.unikom.suweorajamuhamid.databinding.FragmentBarangFormBinding // Pastikan binding class ini tergenerasi
import edu.unikom.suweorajamuhamid.viewmodel.BarangViewModel

@AndroidEntryPoint
class BarangFormFragment : Fragment() {

    private var _binding: FragmentBarangFormBinding? = null
    private val binding get() = _binding!!

    private val barangViewModel: BarangViewModel by viewModels()
    private val args: BarangFormFragmentArgs by navArgs() // Membutuhkan Safe Args untuk tergenerasi

    private var currentBarang: Barang? = null
    private var selectedImageUri: Uri? = null

    // ActivityResultLauncher for picking an image
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                binding.imageViewBarangPreview.load(uri) { // Membutuhkan ID 'imageViewBarangPreview' di XML
                    placeholder(R.drawable.ic_placeholder_image) // Pastikan drawable ini ada
                    error(R.drawable.ic_broken_image) // Pastikan drawable ini ada
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarangFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barangId = args.barangId // Membutuhkan argumen 'barangId' di nav_graph.xml
        if (barangId != -1L) { // Penting: Menggunakan -1L karena argumennya tipe Long
            barangViewModel.getBarangById(barangId).observe(viewLifecycleOwner) { barang ->
                barang?.let {
                    currentBarang = it
                    populateFields(it)
                }
            }
            binding.buttonHapus.visibility = View.VISIBLE // Membutuhkan ID 'buttonHapus' di XML
        } else { // Adding new item
            binding.buttonHapus.visibility = View.GONE
        }

        binding.buttonPilihGambar.setOnClickListener { // Membutuhkan ID 'buttonPilihGambar' di XML
            openGallery()
        }

        binding.buttonSimpan.setOnClickListener { // Membutuhkan ID 'buttonSimpan' di XML
            saveBarang()
        }

        binding.buttonHapus.setOnClickListener { // Membutuhkan ID 'buttonHapus' di XML
            currentBarang?.let {
                barangViewModel.deleteBarang(it)
                Toast.makeText(context, "Barang dihapus", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageLauncher.launch(intent)
    }

    private fun populateFields(barang: Barang) {
        binding.editTextNamaBarang.setText(barang.nama) // Membutuhkan ID 'editTextNamaBarang' di XML
        binding.editTextHargaBarang.setText(barang.harga.toString()) // Membutuhkan ID 'editTextHargaBarang' di XML
        barang.gambarUri?.let { uriString ->
            selectedImageUri = Uri.parse(uriString)
            binding.imageViewBarangPreview.load(selectedImageUri) {
                placeholder(R.drawable.ic_placeholder_image)
                error(R.drawable.ic_broken_image)
            }
        }
    }

    private fun saveBarang() {
        val nama = binding.editTextNamaBarang.text.toString().trim()
        val hargaString = binding.editTextHargaBarang.text.toString().trim()

        if (nama.isEmpty() || hargaString.isEmpty()) {
            Toast.makeText(context, "Nama dan Harga tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val harga = hargaString.toDoubleOrNull()
        if (harga == null || harga <= 0) {
            Toast.makeText(context, "Harga tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        val imageUriString = selectedImageUri?.toString()

        if (currentBarang == null) { // New item
            val newBarang = Barang(nama = nama, harga = harga, gambarUri = imageUriString)
            barangViewModel.insertBarang(newBarang)
            Toast.makeText(context, "Barang disimpan", Toast.LENGTH_SHORT).show()
        } else { // Update existing item
            val updatedBarang = currentBarang!!.copy(
                nama = nama,
                harga = harga,
                gambarUri = imageUriString ?: currentBarang!!.gambarUri // Keep old image if new one not selected
            )
            barangViewModel.updateBarang(updatedBarang)
            Toast.makeText(context, "Barang diperbarui", Toast.LENGTH_SHORT).show()
        }
        findNavController().popBackStack() // Go back to the list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}