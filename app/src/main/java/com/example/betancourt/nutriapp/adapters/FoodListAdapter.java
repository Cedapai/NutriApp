package com.example.betancourt.nutriapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.betancourt.nutriapp.FoodActivity;
import com.example.betancourt.nutriapp.R;
import com.example.betancourt.nutriapp.pojo.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private static final String TAG = "Firelog";
    public List<Food> foodList;
    public Context context;
    public FirebaseStorage storage;
    public StorageReference storageRef;
    private String food_type;

    public FoodListAdapter(Context context, List<Food> foodList, String type){
        this.foodList = foodList;
        this.context = context;
        this.food_type = type;
        storage = FirebaseStorage.getInstance();

        //mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        storageRef = storage.getReferenceFromUrl("gs://nutriapp-c8be7.appspot.com/Food").child(foodList.get(position).getImg_name());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.foodImage.setImageBitmap(bitmap);
            }
        });
        holder.nombreText.setText(foodList.get(position).getName());
        holder.cantidadText.setText(foodList.get(position).getQuantity());

        final String food_id = foodList.get(position).objectId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FoodActivity.class);
                intent.putExtra("id", food_id);
                intent.putExtra("type", food_type);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nombreText, cantidadText;
        public ImageView foodImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nombreText = (TextView) mView.findViewById(R.id.nombre_text);
            cantidadText = (TextView) mView.findViewById(R.id.cantidad_text);
            foodImage = (ImageView) mView.findViewById(R.id.foodImagePrimary);
        }
    }
}
