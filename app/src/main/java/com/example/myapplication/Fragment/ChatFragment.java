package com.example.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Common.Common;
import com.example.myapplication.LinearLayoutManagerWrapper;
import com.example.myapplication.Model.ChatListModel;
import com.example.myapplication.Model.ChatModel;
import com.example.myapplication.Model.UserModel;
import com.example.myapplication.R;
import com.example.myapplication.Common.Utils;
import com.example.myapplication.ViewHolder.ChatAdminViewHolder;
import com.example.myapplication.ViewHolder.ChatUserViewHolder;
import com.example.myapplication.databinding.ChatFragmentBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatFragment extends Fragment {

    ChatFragmentBinding binding;

    FirebaseDatabase database;
    DatabaseReference reference;

    UserModel user = Common.currentUser;

    FirebaseRecyclerOptions<ChatModel> options;
    FirebaseRecyclerAdapter<ChatModel, RecyclerView.ViewHolder> adapter;
    LinearLayoutManagerWrapper layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChatFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innit();
    }

    private void innit() {

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child(Common.MESSAGE_REFERENCE);

        Query query = reference.child(user.getUid()).limitToLast(20);
        layoutManager = new LinearLayoutManagerWrapper(this.getContext(), LinearLayoutManager.VERTICAL,false);

        options = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(query, ChatModel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseRecyclerAdapter<ChatModel, RecyclerView.ViewHolder>(options) {

            @Override
            public int getItemViewType(int position) {
                if(adapter.getItem(position).getSenderID().equals(user.getUid())) return 1;
                else return 2;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType == 1){
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_user, parent, false);
                    return new ChatUserViewHolder(view);
                }
                else {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_admin, parent, false);
                    return new ChatAdminViewHolder(view);
                }
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull ChatModel model) {

                if(holder instanceof ChatUserViewHolder){
                    ChatUserViewHolder chatUser = (ChatUserViewHolder) holder;
                    chatUser.date_user_tv.setText(Utils.timeMillisToDate(model.getDateMillis()));
                    chatUser.content_user_tv.setText(model.getText());
                }
                else {
                    ChatAdminViewHolder chatAdmin = (ChatAdminViewHolder) holder;
                    chatAdmin.date_admin_tv.setText(Utils.timeMillisToDate(model.getDateMillis()));
                    chatAdmin.content_admin_tv.setText(model.getText());
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int count = adapter.getItemCount();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if(lastPosition == -1 ||
                        (positionStart >= (count -1) && (lastPosition == positionStart -1))){
                    binding.chatRecyclerview.scrollToPosition(positionStart);
                }
            }
        });

        binding.chatRecyclerview.setLayoutManager(layoutManager);
        binding.chatRecyclerview.setAdapter(adapter);

        binding.sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.edtChat.getText().toString();
                if(text.equals("")) Toast.makeText(getContext(), "Nhập tin nhắn đi bạn :))", Toast.LENGTH_LONG).show();
                else {
                    DatabaseReference chatRef = reference.child(user.getUid());
                    String key = chatRef.push().getKey();
                    Long time = System.currentTimeMillis();
                    ChatModel chat = new ChatModel(key, text, user.getUid(), time);
                    chatRef.child(key).setValue(chat);
                    binding.edtChat.setText("");

                    ChatListModel chatListModel = new ChatListModel(
                            user.getUid(),
                            text,
                            user.getLastName()+" "+ user.getFirtName(),
                            time);
                    database.getReference()
                            .child(Common.MESSAGE_LIST_REFERENCE)
                            .child(user.getUid())
                            .setValue(chatListModel);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null) adapter.startListening();
    }

    @Override
    public void onStop() {
        if(adapter!=null) adapter.stopListening();
        super.onStop();
        super.onStop();
    }
}
