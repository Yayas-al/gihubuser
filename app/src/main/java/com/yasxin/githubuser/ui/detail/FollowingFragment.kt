package com.yasxin.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasxin.githubuser.R
import com.yasxin.githubuser.databinding.FragmentFolllowBinding
import com.yasxin.githubuser.databinding.FragmentFolllowingBinding
import com.yasxin.githubuser.ui.main.UserAdapter

class FollowingFragment : Fragment(R.layout.fragment_folllowing) {

    private var _binding: FragmentFolllowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUser.EXTRA_USERNAME).toString()

        _binding = FragmentFolllowingBinding.bind(view)

        adapter = UserAdapter()
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbLoading.visibility = View.VISIBLE
        } else {
            binding.pbLoading.visibility = View.GONE
        }
    }
}