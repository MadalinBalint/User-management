package com.mendelin.usersmanagement.presentation.users_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.usersmanagement.databinding.FragmentUsersListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersListFragment : Fragment() {

    private var binding: FragmentUsersListBinding? = null
    private val viewModel: UsersListViewModel by viewModels()
    private lateinit var usersAdapter: UsersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupUi() {
        usersAdapter = UsersListAdapter()

        binding?.recyclerUsers?.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            isNestedScrollingEnabled = true
        }

        viewModel.fetchRandomUsersList()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    binding?.progressBar?.visibility =
                        if (state.isLoading) View.VISIBLE else View.INVISIBLE
                    val (hasFailed, message) = state.isFailed

                    if (hasFailed) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usersListState.collect { state ->
                    usersAdapter.submitData(state)
                }
            }
        }
    }
}