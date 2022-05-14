package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.listeners.ChatListener;
import com.yom.hospitalmanagementyom.model.Chat;
import com.yom.hospitalmanagementyom.model.Constants;
import com.yom.hospitalmanagementyom.model.Doctor;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder>{

    private final List<Chat> chats;
    private final List<Doctor> doctors;
    private final Context context;
    private final Repository repository;
    private final ChatListener chatListener;

    public ChatAdapter(Context context,List<Chat> chats,List<Doctor> doctors, ChatListener chatListener) {
        this.context = context;
        this.chats = chats;
        this.doctors = doctors;
        this.chatListener = chatListener;
        repository = new Repository(context);
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_chat, parent, false );
        return new ChatHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Chat chat = chats.get( position );
        Doctor doctor = doctors.get(position);

        Picasso.with( context ).load( doctor.getProfile() )
                .placeholder( R.color.teal_700 ).into( holder.profileOfChat );

        holder.nameChat.setText(doctor.getName());
        holder.lastTime.setText(chat.getTime());
        holder.lastMessage.setText(chat.getMessage());

        if(chat.getIdSender().equals(repository.getUser().getUid())) {
            switch (chat.getSeen()) {
                case Constants.NOT_OPEN:
                    holder.seen.setVisibility(View.VISIBLE);
                    holder.seen.setImageResource(R.drawable.no_open);
                    break;
                case Constants.NOT_SEEN:
                    holder.seen.setVisibility(View.VISIBLE);
                    holder.seen.setImageResource(R.drawable.no_seen);
                    break;
                case Constants.SEEN:
                    holder.seen.setVisibility(View.VISIBLE);
                    holder.seen.setImageResource(R.drawable.seen);
                    break;
            }
        }
        else{
            holder.seen.setVisibility(View.GONE);
            if (chat.getNumberOfMessageNotSeen() > 0) {
                String num;
                holder.numberOfMessageNotSeen.setVisibility(View.VISIBLE);
                if (chat.getNumberOfMessageNotSeen() > 99)
                    num = "+99";
                else
                    num = chat.getNumberOfMessageNotSeen() + "";
                holder.numberOfMessageNotSeen.setText(num);
            } else
                holder.numberOfMessageNotSeen.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> chatListener.onClickItem(chat.getId(), doctor));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder{

        CircleImageView profileOfChat;
        TextView nameChat, lastMessage,lastTime,numberOfMessageNotSeen;
        ImageView seen;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            profileOfChat = itemView.findViewById( R.id.profileOfChat );
            nameChat = itemView.findViewById( R.id.nameChat );
            lastMessage = itemView.findViewById( R.id.lastMessage );
            lastTime = itemView.findViewById( R.id.lastTime );
            seen = itemView.findViewById( R.id.seen );
            numberOfMessageNotSeen=itemView.findViewById( R.id.numberOfMessageNotSeen );
        }
    }
}
