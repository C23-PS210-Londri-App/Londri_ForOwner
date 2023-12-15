package me.fitroh.londriforowner.ui.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import me.fitroh.londriforowner.databinding.FragmentServiceBinding
import me.fitroh.londriforowner.models.ViewModelFactory
import me.fitroh.londriforowner.ui.login.LoginActivity

class ServiceFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding
    private val viewModel by viewModels<ServiceViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var isViewCreated = false
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        loadData()

        binding.addFab.setOnClickListener {
            val bottomSheetFragment = ServiceAddFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
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
                                binding.ivEmpty.visibility = View.GONE
                                binding.tvEmpty.visibility = View.GONE
                                binding.rvService.adapter = ServiceAdapter(listData, viewModel, tokenAuth)
                            } else {
                                binding.ivEmpty.visibility = View.VISIBLE
                                binding.tvEmpty.visibility = View.VISIBLE
                                binding.rvService.adapter = null
                            }
                        }
                    }
                    viewModel.isLoading.observe(viewLifecycleOwner) { load ->
                        showLoading(load)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun backToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }
}