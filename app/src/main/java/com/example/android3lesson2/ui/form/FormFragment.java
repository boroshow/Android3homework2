package com.example.android3lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android3lesson2.App;
import com.example.android3lesson2.R;
import com.example.android3lesson2.data.models.Post;
import com.example.android3lesson2.databinding.FragmentFormBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {

    private FragmentFormBinding binding;
    private boolean isUpdate = false;
    private Post post;


    public FormFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            post = (Post) getArguments().getSerializable("post");
            isUpdate = true;
            setPost();
        }

        binding.btnCreatePost.setOnClickListener(v -> {
            if (!isUpdate){
                create();
            } else {
                update();
            }
        });
    }

    private void setPost() {
        binding.etTitle.setText(post.getTitle());
        binding.etUserId.setText(String.valueOf(post.getUser()));
        binding.etDescription.setText(post.getDescription());
    }

    private Post getPost(){
        Post post = new Post(
                binding.etTitle.getText().toString(),
                binding.etDescription.getText().toString(),
                Integer.parseInt(String.valueOf(binding.etUserId.getText())),
                35);
        return post;
    }

    private void create() {
        App.api.createPost(getPost()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigateUp();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void update() {
        App.api.putPost(getPost().getUser(), getPost()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    Log.e("TAG", "Update  " + post.getTitle());
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigateUp();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }
        });
    }


}