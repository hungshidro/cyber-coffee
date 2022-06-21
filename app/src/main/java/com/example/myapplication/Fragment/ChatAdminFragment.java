package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.AdminChatActivity;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Common.Utils;
import com.example.myapplication.LinearLayoutManagerWrapper;
import com.example.myapplication.Model.ChatListModel;
import com.example.myapplication.Model.ChatModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.ChatListViewHolder;
import com.example.myapplication.databinding.ChatAdminFragmentBinding;
import com.example.myapplication.databinding.ChatFragmentBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatAdminFragment extends Fragment {

    ChatAdminFragmentBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseRecyclerOptions<ChatListModel> options;
    FirebaseRecyclerAdapter<ChatListModel, ChatListViewHolder> adapter;
    LinearLayoutManagerWrapper layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChatAdminFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innit();
    }

    private void innit() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        Query query = reference.child(Common.MESSAGE_LIST_REFERENCE);

        layoutManager = new LinearLayoutManagerWrapper(this.getContext(), LinearLayoutManager.VERTICAL,false);

        options = new FirebaseRecyclerOptions.Builder<ChatListModel>()
                .setQuery(query, ChatListModel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseRecyclerAdapter<ChatListModel, ChatListViewHolder>(options) {
            @NonNull
            @Override
            public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ChatListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_admin, parent,false));
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatListViewHolder holder, int position, @NonNull ChatListModel model) {
                holder.username.setText(model.getUsername());
                holder.lastMess.setText(model.getLastMess());
                holder.lastDate.setText(Utils.timeMillisToDate(model.getLastDate()));
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AdminChatActivity.class);
                        intent.putExtra("uid", model.getUid());
                        intent.putExtra("username", model.getUsername());
                        startActivity(intent);
                    }
                });
            }
        };

        binding.chatRecyclerview.setLayoutManager(layoutManager);
        binding.chatRecyclerview.setAdapter(adapter);
    }
}
