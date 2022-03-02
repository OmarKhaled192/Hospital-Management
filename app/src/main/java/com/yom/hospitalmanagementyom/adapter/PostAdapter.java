package com.yom.hospitalmanagementyom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.yom.hospitalmanagementyom.R;
import com.yom.hospitalmanagementyom.model.Post;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//Yousef Shaaban
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
  private final List<Post> posts;
  private final Context context;
  private final FirebaseStorage storage;
  private final StorageReference storageRef;
  private long like,dislike,star;
  private final int red,black,likeOn,likeOff,dislikeOn,dislikeOff,starOn,starOff;

  public PostAdapter( Context context, List<Post> posts) {
    this.posts = posts;
    this.context=context;
    storage = FirebaseStorage.getInstance();
    storageRef = storage.getReference();
    like=0;
    dislike=0;
    star=0;
    red=context.getResources().getColor(R.color.red);
    black=context.getResources().getColor(R.color.black);
    likeOn=R.drawable.like;
    likeOff=R.drawable.like_off;
    dislikeOn=R.drawable.dis_like;
    dislikeOff=R.drawable.dis_like_off;
    starOn=R.drawable.star;
    starOff=R.drawable.star_off;
  }

  @Override
  public int getItemCount() {
    return posts.size();
  }

  @NonNull
  @Override
  public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_post_for_home_patient, parent, false );
    return new PostHolder( view );
  }

  @SuppressLint("RecyclerView")
  @Override
  public void onBindViewHolder(@NonNull PostHolder holder, int position) {
    Post post = posts.get(position);
    like = post.getLike();
    dislike = post.getDisLike();
    star = post.getStar();

    holder.profilePostForHomePatient.setImageResource(R.drawable.doctor);
    holder.namePostForHomePatient.setText(post.getName());
    holder.timePostForHomePatient.setText(post.getTime());
    if (!post.getPost().equals("")) {
      holder.postForHomePatient.setText(post.getPost());
      holder.postForHomePatient.setVisibility(View.VISIBLE);
    } else {
      holder.postForHomePatient.setVisibility(View.GONE);
    }
    if (!post.getImage().equals("")) {
      storage.getReference(post.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
          Picasso.with(context).load(uri).into(holder.imagePostForHomePatient);
          holder.imagePostForHomePatient.setVisibility(View.VISIBLE);
        }
      });
    } else {
      holder.imagePostForHomePatient.setVisibility(View.GONE);
    }
    if (!post.getVideo().equals("")) {
      storageRef.child(post.getName() + "/" + post.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
        @Override
        public void onSuccess(Uri uri) {
          holder.videoPostForHomePatient.setVideoURI(uri);
          holder.videoPostForHomePatient.setVisibility(View.VISIBLE);
        }
      });
    } else {
      holder.imagePostForHomePatient.setVisibility(View.GONE);
    }
    holder.numLikePostDoctor.setText(like + " Like");
    holder.numDisLikePostDoctor.setText(dislike + " Dislike");
    holder.numStarPostForHomePatient.setText(star + " Star");
    holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(likeOff, 0, 0, 0);
    holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(dislikeOff, 0, 0, 0);
    holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(starOff, 0, 0, 0);

    holder.likePostForHomePatient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (holder.likePostForHomePatient.getCurrentTextColor() == black && holder.disLikePostForHomePatient.getCurrentTextColor() == red) {
          holder.disLikePostForHomePatient.setTextColor(black);
          holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(dislikeOff, 0, 0, 0);
          holder.likePostForHomePatient.setTextColor(red);
          holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(likeOn, 0, 0, 0);
          like=Integer.parseInt(holder.numLikePostDoctor.getText().toString());
          dislike=Integer.parseInt(holder.numDisLikePostDoctor.getText().toString());
          like++;
          dislike--;
          holder.numLikePostDoctor.setText(like+ " Like");
          holder.numDisLikePostDoctor.setText(dislike+ " Dislike");
        } else if (holder.likePostForHomePatient.getCurrentTextColor() == black) {
          like++;
          holder.likePostForHomePatient.setTextColor(red);
          holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(likeOn, 0, 0, 0);
          holder.numLikePostDoctor.setText(like + " Like");
        } else if (holder.likePostForHomePatient.getCurrentTextColor() == red) {
          like--;
          holder.likePostForHomePatient.setTextColor(black);
          holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(likeOff, 0, 0, 0);
          holder.numLikePostDoctor.setText(like + " Like");
        }
      }
    });


    holder.disLikePostForHomePatient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (holder.disLikePostForHomePatient.getCurrentTextColor() == black && holder.likePostForHomePatient.getCurrentTextColor() == red) {
          holder.likePostForHomePatient.setTextColor(black);
          holder.likePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(likeOff, 0, 0, 0);
          holder.disLikePostForHomePatient.setTextColor(red);
          holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(dislikeOn, 0, 0, 0);
          like--;
          dislike++;
          holder.numLikePostDoctor.setText(like + " Like");
          holder.numDisLikePostDoctor.setText(dislike + " Dislike");
        }
        else if (holder.disLikePostForHomePatient.getCurrentTextColor() == black) {
          dislike++;
          holder.disLikePostForHomePatient.setTextColor(red);
          holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(dislikeOn, 0, 0, 0);
          holder.numDisLikePostDoctor.setText(dislike + " Dislike");
        }
        else if (holder.disLikePostForHomePatient.getCurrentTextColor() == red) {
          dislike--;
          holder.disLikePostForHomePatient.setTextColor(black);
          holder.disLikePostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(dislikeOff, 0, 0, 0);
          holder.numDisLikePostDoctor.setText(dislike + " Dislike");
        }
      }
    });


    holder.starPostForHomePatient.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (holder.starPostForHomePatient.getCurrentTextColor() == black) {
          star++;
          holder.starPostForHomePatient.setTextColor(red);
          holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(starOn, 0, 0, 0);
          holder.numStarPostForHomePatient.setText(star + " Star");
        } else if (holder.starPostForHomePatient.getCurrentTextColor() == red) {
          star--;
          holder.starPostForHomePatient.setTextColor(black);
          holder.starPostForHomePatient.setCompoundDrawablesRelativeWithIntrinsicBounds(starOff, 0, 0, 0);
          holder.numStarPostForHomePatient.setText(star + " Star");
        }
      }
    });
  }

  protected class PostHolder extends RecyclerView.ViewHolder {
    CircleImageView profilePostForHomePatient;
    TextView namePostForHomePatient, timePostForHomePatient,postForHomePatient,numLikePostDoctor,numDisLikePostDoctor,numStarPostForHomePatient;
    ImageView imagePostForHomePatient;
    VideoView videoPostForHomePatient;
    Button likePostForHomePatient,disLikePostForHomePatient,starPostForHomePatient;

    PostHolder(View itemView) {
      super( itemView );
      profilePostForHomePatient = itemView.findViewById( R.id.profilePostForHomePatient );
      namePostForHomePatient = itemView.findViewById( R.id.namePostForHomePatient );
      timePostForHomePatient = itemView.findViewById( R.id.timePostForHomePatient );
      postForHomePatient = itemView.findViewById( R.id.postForHomePatient );
      imagePostForHomePatient = itemView.findViewById( R.id.imagePostForHomePatient );
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
