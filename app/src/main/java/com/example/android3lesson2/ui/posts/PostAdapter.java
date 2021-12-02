package com.example.android3lesson2.ui.posts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android3lesson2.data.models.Post;
import com.example.android3lesson2.databinding.ItemViewBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosts(List<Post> posts) {
        this.list = posts;
        notifyDataSetChanged();
    }

    public Post getItem(int pos){
        return list.get(pos);
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewBinding binding = ItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int pos) {
        list.remove(pos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemViewBinding binding;

        public ViewHolder(@NonNull ItemViewBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(v -> {
                onItemClickListener.OnClick(getAdapterPosition());
            });

            binding.getRoot().setOnLongClickListener(v -> {
                onItemClickListener.OnLongClick(getAdapterPosition());
                return true;
            });

        }

        public void onBind(Post post) {
            binding.userIdTv.setText(String.valueOf(post.getId()));
            binding.titleTv.setText(post.getTitle());
            binding.descriptionTv.setText(post.getDescription());
        }
    }
    public interface OnItemClickListener{
        void OnClick(int pos);
        void OnLongClick(int pos);
    }
}
