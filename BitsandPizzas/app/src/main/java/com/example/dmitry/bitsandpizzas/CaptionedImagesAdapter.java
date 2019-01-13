package com.example.dmitry.bitsandpizzas;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptionedImagesAdapter extends
    RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

  private String[] captions;
  private int[] imageIds;

  public CaptionedImagesAdapter(String[] captions, int[] imageIds) {

    this.captions = captions;
    this.imageIds = imageIds;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.card_captioned_image, viewGroup, false);
    return new ViewHolder(cv);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    CardView cardView = viewHolder.cardView;
    ImageView imageView = cardView.findViewById(R.id.info_image);
    Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
    imageView.setImageDrawable(drawable);
    imageView.setContentDescription(captions[position]);
    TextView textView = cardView.findViewById(R.id.info_text);
    textView.setText(captions[position]);
  }

  @Override
  public int getItemCount() {

    return captions.length;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private CardView cardView;

    public ViewHolder(CardView v) {
      super(v);
      cardView = v;
    }
  }
}
