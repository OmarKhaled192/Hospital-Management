package com.yom.hospitalmanagementyom.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.database.Repository;
import com.yom.hospitalmanagementyom.listeners.PostsListener;
import com.yom.hospitalmanagementyom.model.Doctor;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

//Yousef Shaaban
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
  private final List<Post> posts;
  private final Context context;
  private final Repository repository;
  private final PostsListener postsListener;
  private List<Boolean> likesList, dislikesList, starrList;

  public PostAdapter(Context context, List<Post> posts, PostsListener postsListener) {
    this.context = context;
    this.posts = posts;
    this.postsListener = postsListener;
    repository = new Repository(context);
    likesList = new ArrayList<>();
    dislikesList = new ArrayList<>();
    starrList = new ArrayList<>();
  }

  @Override
  public int getItemCount() {
    return posts.size();
  }

  @NonNull
  @Override
  public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_post_for_home_patient, parent, false );
    return new PostHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull PostHolder holder, int position) {
    Post post = posts.get(position);

    Picasso.with(context).load(post.getProfileDoctor()).error(R.color.teal_700).into(holder.profilePostForHomePatient);
    holder.namePostForHomePatient.setText(post.getNameDoctor());
    holder.timePostForHomePatient.setText(post.getTime());

    if (!post.getPost().equals("")) {
      holder.postForHomePatient.setText(post.getPost());
      holder.postForHomePatient.setVisibility(View.VISIBLE);
    } else {
      holder.postForHomePatient.setVisibility(View.GONE);
    }
    if (!post.getImage().equals("")) {
      holder.progressBarPost.setVisibility(View.VISIBLE);
      holder.imagePostForHomePatient.setVisibility(View.VISIBLE);
      Picasso.with(context).load(post.getImage()).error(R.color.hint)
              .into(holder.imagePostForHomePatient);
    } else {
      holder.imagePostForHomePatient.setVisibility(View.GONE);
    }
    if (!post.getVideo().equals("")) {
      holder.progressBarPost.setVisibility(View.VISIBLE);
      holder.videoPostForHomePatient.setVisibility(View.VISIBLE);
      holder.videoPostForHomePatient.setVideoURI(Uri.parse(post.getVideo()));
    } else {
      holder.videoPostForHomePatient.setVisibility(View.GONE);
    }
    holder.progressBarPost.setVisibility(View.GONE);

    String Like = post.getLikes().size() + context.getString(R.string.like);
    holder.numLikePostDoctor.setText(Like);
    String DisLike = post.getDisLikes().size() + context.getString(R.string.disLike);
    holder.numDisLikePostDoctor.setText(DisLike);
    String Star = post.getStars().size() + context.getString(R.string.star);
    holder.numStarPostForHomePatient.setText(Star);

    holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_off, 0, 0, 0);

    holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dis_like_off, 0, 0, 0);
    holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.star_off, 0, 0, 0);


    boolean likeExist = repository.checkExistId(post.getLikes(),repository.getUser().getUid());
    if(likeExist)
      holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like, 0, 0, 0);

    boolean disLikeExist = repository.checkExistId(post.getDisLikes(),repository.getUser().getUid());
    if(disLikeExist)
      holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dis_like, 0, 0, 0);

    boolean starExist = repository.checkExistId(post.getStars(),repository.getUser().getUid());
    if(starExist)
      holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.star, 0, 0, 0);

    likesList.add(likeExist);
    dislikesList.add(disLikeExist);
    starrList.add(starExist);

    holder.likePostForHomePatient.setOnClickListener(v -> {
      if(likesList.get(position)) {
        postsListener.onCancelLikePost(position, post.getId());
//        holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_off, 0, 0, 0);
//        String Like1 = post.getLikes().size()-1 + context.getString(R.string.like);
//        holder.numLikePostDoctor.setText(Like1);
//        likesList.add(position,false);
      }
      else {
        postsListener.onClickLikePost(position, post.getId());
//        holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
//        String Like1 = post.getLikes().size()+1 + context.getString(R.string.like);
//        holder.numLikePostDoctor.setText(Like1);
//        likesList.add(position,true);
      }
    });


    holder.disLikePostForHomePatient.setOnClickListener(v -> {
      if(dislikesList.get(position)) {
        postsListener.onCancelDisLikePost(position, post.getId());
//        holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dis_like_off, 0, 0, 0);
//        String Like1 = post.getDisLikes().size() - 1 + context.getString(R.string.disLike);
//        holder.numDisLikePostDoctor.setText(Like1);
//        dislikesList.add(position,false);
      }
      else{
        postsListener.onClickDisLikePost(position, post.getId());
//        holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.dis_like, 0, 0, 0);
//        String Like1 = post.getDisLikes().size() + 1 + context.getString(R.string.disLike);
//        holder.numDisLikePostDoctor.setText(Like1);
//        dislikesList.add(position,true);
      }
    });


    holder.starPostForHomePatient.setOnClickListener(v -> {
      if(starrList.get(position)){
        postsListener.onCancelStarPost(position, post.getId());
//        holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.star_off, 0, 0, 0);
//        String Like1 = post.getStars().size() - 1 + context.getString(R.string.star);
//        holder.numStarPostForHomePatient.setText(Like1);
//        starrList.add(position,false);
      }
      else {
        postsListener.onClickStarPost(position, post.getId());
//        holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.star, 0, 0, 0);
//        String Like1 = post.getStars().size() + 1 + context.getString(R.string.disLike);
//        holder.numStarPostForHomePatient.setText(Like1);
//        starrList.add(position,true);
      }
    });
  }

  protected static class PostHolder extends RecyclerView.ViewHolder {
    private final CircleImageView profilePostForHomePatient;
    private final TextView namePostForHomePatient, timePostForHomePatient,postForHomePatient,numLikePostDoctor,numDisLikePostDoctor,numStarPostForHomePatient;
    private final ImageView imagePostForHomePatient;
    private final VideoView videoPostForHomePatient;
    private final Button likePostForHomePatient,disLikePostForHomePatient,starPostForHomePatient;
    private final ProgressBar progressBarPost;

    PostHolder(View itemView) {
      super( itemView );
      profilePostForHomePatient = itemView.findViewById( R.id.profilePostForHomePatient );
      namePostForHomePatient = itemView.findViewById( R.id.namePostForHomePatient );
      timePostForHomePatient = itemView.findViewById( R.id.timePostForHomePatient );
      postForHomePatient = itemView.findViewById( R.id.postForHomePatient );
      imagePostForHomePatient = itemView.findViewById( R.id.imagePostForHomePatient );
      progressBarPost = itemView.findViewById(R.id.progressBarPost);
      videoPostForHomePatient = itemView.findViewById( R.id.videoPostForHomePatient );
      numLikePostDoctor = itemView.findViewById( R.id.numLikePostForHomePatient );
      numDisLikePostDoctor=itemView.findViewById( R.id.numDisLikePostForHomePatient );
      numStarPostForHomePatient = itemView.findViewById( R.id.numStarPostForHomePatient );
      likePostForHomePatient = itemView.findViewById( R.id.likePostForHomePatient );
      disLikePostForHomePatient=itemView.findViewById( R.id.disLikePostForHomePatient );
      starPostForHomePatient = itemView.findViewById( R.id.starPostForHomePatient );
    }
  }
}
