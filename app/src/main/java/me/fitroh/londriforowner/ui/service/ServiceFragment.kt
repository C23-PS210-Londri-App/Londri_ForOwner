package me.fitroh.londriforowner.ui.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import me.fitroh.londriforowner.databinding.FragmentHomeBinding
import me.fitroh.londriforowner.databinding.FragmentProfileBinding
import me.fitroh.londriforowner.databinding.FragmentServiceBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.home.HomeAdapter
import me.fitroh.londriforowner.ui.home.HomeViewModel
import me.fitroh.londriforowner.ui.login.LoginActivity
import me.fitroh.londriforowner.ui.profile.ProfileViewModel

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val viewModel by viewModels<ServiceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var isViewCreated = false
    private var token: String? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()

        binding.addFab.setOnClickListener { prosesTambahLayanan() }
    }

    private fun prosesTambahLayanan() {
        TODO("Not yet implemented")
    }

    private fun loadData(){
        viewModel.getSession().observe(viewLifecycleOwner){ session ->
            token = session.token
            val tokenAuth = session.token
            if(!session.isLogin){
                backToLogin()
            }else{
                viewModel.getService(tokenAuth)

                if(isViewCreated){
                    binding.rvService.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                    }

                    viewModel.listLayananItem.observe(viewLifecycleOwner){listData ->

                        if (listData != null) {
                            if (listData.isNotEmpty()) {
//                                binding.ivEmptyOrder.visibility = View.GONE
                                binding.tvEmpty.visibility = View.GONE
                                binding.rvService.adapter = ServiceAdapter(listData)
                            } else {
//                                binding.ivEmptyOrder.visibility = View.VISIBLE
                                binding.tvEmpty.visibility = View.VISIBLE
                                binding.rvService.adapter = null
                            }
                        }
                    }
//                    viewModel.isLoading.observe(viewLifecycleOwner) { load ->
//                        showLoading(load)
//                    }
                }
            }
        }
    }

    private fun backToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}