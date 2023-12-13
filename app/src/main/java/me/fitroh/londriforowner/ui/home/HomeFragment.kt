package me.fitroh.londriforowner.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.fitroh.londriforowner.databinding.FragmentHomeBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.login.LoginActivity
import me.fitroh.londriforowner.ui.profile.ProfileViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel by viewModels<HomeViewModel> {
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()
    }

    private fun loadData(){
        viewModel.getSession().observe(viewLifecycleOwner){ session ->
            token = session.token
            val tokenAuth = session.token
            if(!session.isLogin){
                backToLogin()
            }else{
                viewModel.getOrder(tokenAuth)

                if(isViewCreated){
                    binding.rvOrder.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                    }

                    viewModel.listOrderItemNon.observe(viewLifecycleOwner){listData ->

                        if (listData.isNotEmpty()) {
                            binding.ivEmptyOrder.visibility = View.GONE
                            binding.textHome.visibility = View.GONE
                            binding.rvOrder.adapter = HomeAdapter(listData)
                        } else {
                            binding.ivEmptyOrder.visibility = View.VISIBLE
                            binding.textHome.visibility = View.VISIBLE
                            binding.rvOrder.adapter = null
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