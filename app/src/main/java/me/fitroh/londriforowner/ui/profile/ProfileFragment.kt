package me.fitroh.londriforowner.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import me.fitroh.londriforowner.databinding.FragmentProfileBinding
import me.fitroh.londriforowner.models.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            user.token.let { token ->
                viewModel.getProfile(token)
            }
        }

        viewModel.profileResponse.observe(viewLifecycleOwner) { profile ->
            Log.d("Debug:", "${profile}")

            binding.apply {
                tvName.text = profile.response?.namaLaundry
                Log.d("Debug:", "${profile}")
                tvEmail.text = profile.response?.email
                tvTelepon.text = profile.response?.nomorTelepon
                tvAddress.text = profile.response?.alamat
                Glide
                    .with(this@ProfileFragment)
                    .load(profile.response?.fotoLaundry)
                    .centerCrop()
                    .into(ivProfile)
            }
        }
    }
}