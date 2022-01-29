package com.yom.hospitalmanagementyom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.java.Post;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//Yousef Shaaban
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private List<Post> posts;
    private Context context;

    public PostAdapter( Context context, List<Post> posts) {
        this.posts = posts;
        this.context=context;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_post_for_home_patient, parent, false );
        return new PostHolder( view );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = posts.get( position );

        holder.profilePostDoctor.setImageResource( R.drawable.doctors_icon );
        holder.namePostDoctor.setText( "Yousef Shaaban" );
        holder.timePostDoctor.setText( "10:00 PM ");
       // holder.postDoctor.setText( "10:00 PM " );
        holder.likePostDoctor.setImageResource( R.drawable.like );
        holder.disLikePostDoctor.setImageResource( R.drawable.dis_like_off );
        holder.commentPostDoctor.setImageResource( R.drawable.comment );
        holder.starPostDoctor.setImageResource( R.drawable.star );
    }

    protected class PostHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePostDoctor;
        TextView namePostDoctor, timePostDoctor,postDoctor;
        ImageButton likePostDoctor,disLikePostDoctor,commentPostDoctor,starPostDoctor;

        PostHolder(View itemView) {
            super( itemView );
            profilePostDoctor = itemView.findViewById( R.id.profilePostForHomePatient );
            namePostDoctor = itemView.findViewById( R.id.namePostForHomePatient );
            timePostDoctor = itemView.findViewById( R.id.timePostForHomePatient );
            postDoctor = itemView.findViewById( R.id.postForHomePatient );
            likePostDoctor = itemView.findViewById( R.id.likePostForHomePatient );
            disLikePostDoctor=itemView.findViewById( R.id.disLikePostForHomePatient );
            commentPostDoctor= itemView.findViewById( R.id.commentPostForHomePatient );
            starPostDoctor = itemView.findViewById( R.id.starPostForHomePatient );
        }
    }
}
