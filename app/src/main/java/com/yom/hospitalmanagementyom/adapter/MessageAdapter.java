package com.yom.hospitalmanagementyom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {
    private Context Context;
    private List<Chat> chats;
    private boolean isDelete = false;
    private List<String> messageSelected=new ArrayList<>();

    public MessageAdapter(Context context, List<Chat> chats) {
        this.Context = context;
        this.chats = chats;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chats.get( position );
        if (chat.getIdSender().equals( "1" ))
            return 0;
        return 1;
    }

    // Inflates the appropriate layout according to the ViewType.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == 0) {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_chat_me, parent, false );
            return new SentMessageHolder( view );
        } else if (viewType == 1) {
            view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_chat_other, parent, false );
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chat chat = chats.get( position );
        if (holder.getClass().equals( SentMessageHolder.class ))
            ((SentMessageHolder) holder).bind( chat );
        else
            ((ReceivedMessageHolder) holder).bind( chat );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDelete){
                    if (holder.getClass().equals( SentMessageHolder.class ))
                        selectItem(((SentMessageHolder) holder).deleteMe, holder.itemView, chat.getId());
                    else
                        selectItem(((ReceivedMessageHolder) holder).deleteOther, holder.itemView, chat.getId());

                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (holder.getClass().equals( SentMessageHolder.class ))
                    selectItem(((SentMessageHolder) holder).deleteMe, holder.itemView, chat.getId());
                else
                    selectItem(((ReceivedMessageHolder) holder).deleteOther, holder.itemView, chat.getId());
                return true;
            }
        });
    }

    private void selectItem(ImageView imageView, View itemView, String id) {
        if(imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.GONE);
            itemView.setBackgroundResource(R.color.notSelect);
            messageSelected.remove(messageSelected.size()-1);
            if(messageSelected.size()==0)
                isDelete = false;
        }
        else {
            isDelete = true;
            messageSelected.add(id);
            itemView.setBackgroundResource(R.color.hint);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private static class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageMe, dateMe;
        ImageView deleteMe;

        SentMessageHolder(View itemView) {
            super( itemView );
            messageMe = itemView.findViewById( R.id.messageMe );
            dateMe = itemView.findViewById( R.id.dateMe );
            deleteMe = itemView.findViewById( R.id.deleteMe );
        }

        void bind(Chat chat) {
            messageMe.setText( chat.getMessage() );
            dateMe.setText( chat.getTime() );
            switch (chat.getSeen()) {
                case "Seen":
                    dateMe.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.seen, 0);
                    break;
                case "Not Seen":
                    dateMe.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.no_seen, 0);
                    break;
                case "Not Open":
                    dateMe.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.no_open, 0);
                    break;
            }
        }
    }


    private static class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageOther, dateOther;
        ImageView deleteOther;

        ReceivedMessageHolder(View itemView) {
            super( itemView );
            messageOther = itemView.findViewById( R.id.messageOther );
            dateOther = itemView.findViewById( R.id.dateOther );
            deleteOther = itemView.findViewById( R.id.deleteOther );
        }

        void bind(Chat chat) {
            messageOther.setText( chat.getMessage() );
            dateOther.setText( chat.getTime() );
            //dateOther.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.send, 0);

        }
    }
}
