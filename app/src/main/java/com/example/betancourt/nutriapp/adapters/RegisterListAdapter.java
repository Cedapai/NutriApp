package com.example.betancourt.nutriapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.betancourt.nutriapp.R;
import com.example.betancourt.nutriapp.pojo.Register;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class RegisterListAdapter extends RecyclerView.Adapter<RegisterListAdapter.ViewHolder> {

    private static final String TAG = "Firelog";
    public List<Register> registerList;
    public Context context;
    public FirebaseStorage storage;
    public StorageReference storageRef;

    public RegisterListAdapter(Context context, List<Register> registerList){
        this.registerList = registerList;
        this.context = context;
        storage = FirebaseStorage.getInstance();

        //mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public RegisterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_register, parent, false);
        return new RegisterListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RegisterListAdapter.ViewHolder holder, int position) {
        holder.nombreRegister.setText(registerList.get(position).getFood_name());
        holder.typeRegister.setText(registerList.get(position).getFood_type());
        holder.cantidadRegister.setText(registerList.get(position).getQuantity());
        holder.fechaHoraRegister.setText((registerList.get(position).getDate() + " - " + registerList.get(position).getHour()));
        holder.marcaRegister.setText(registerList.get(position).getBrand());
        holder.quienRegister.setText(registerList.get(position).getWho());
        holder.actividadRegister.setText(registerList.get(position).getActivity());

        final String register_id = registerList.get(position).objectId;

        /*holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FoodActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return registerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nombreRegister, typeRegister, cantidadRegister, fechaHoraRegister, marcaRegister, quienRegister, actividadRegister;
        public ImageView foodImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            nombreRegister = (TextView) mView.findViewById(R.id.nombre_register);
            typeRegister = (TextView) mView.findViewById(R.id.type_register);
            cantidadRegister = (TextView) mView.findViewById(R.id.cantidad_register);
            fechaHoraRegister = (TextView) mView.findViewById(R.id.fecha_hora_register);
            marcaRegister = (TextView) mView.findViewById(R.id.marca_register);
            quienRegister = (TextView) mView.findViewById(R.id.quien_resgister);
            actividadRegister = (TextView) mView.findViewById(R.id.actividad_resgister);
        }
    }

}
