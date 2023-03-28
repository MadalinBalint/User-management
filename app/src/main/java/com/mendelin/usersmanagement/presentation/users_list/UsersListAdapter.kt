package com.mendelin.usersmanagement.presentation.users_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.usersmanagement.UserItemBinding
import com.mendelin.usersmanagement.domain.model.User

class UsersListAdapter :
    PagingDataAdapter<User, UsersListAdapter.UsersListViewHolder>(UsersListDiffCallBack()) {

    inner class UsersListViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    class UsersListDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        return UsersListViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}