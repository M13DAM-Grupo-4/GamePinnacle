package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.Other.FilterOption;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Amigos;
import m13dam.grupo4.gamepinnacle.R;

public class FilterGamesAdapter extends RecyclerView.Adapter<FilterGamesAdapter.ViewHolder> {

    private Context mContext_jvm;
    private ArrayList<FilterOption> filterOptions;


    public FilterGamesAdapter(Context context, ArrayList<FilterOption>filterOptions) {
        this.mContext_jvm = context;
        this.filterOptions = filterOptions;

    }

    @NonNull
    @Override
    public FilterGamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext_jvm);
        View view = inflater.inflate(R.layout.filter_games_item, parent, false);
        return new FilterGamesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterGamesAdapter.ViewHolder holder, int position) {

        if (filterOptions.get(position).getName().equals("Añadir Juego")) {

            holder.toggleButton.setText("Añadir Juego");
            holder.toggleButton.setTextOn("Añadir Juego");
            holder.toggleButton.setTextOff("Añadir Juego");
            holder.toggleButton.setOnClickListener(v -> {

            });
            return;
        }

        holder.toggleButton.setTextOn(filterOptions.get(position).getName()+": ON");
        holder.toggleButton.setTextOff(filterOptions.get(position).getName()+": OFF");
        holder.toggleButton.setChecked(holder.checked);
        System.out.println("sdasdasd");

        holder.toggleButton.setOnClickListener(v -> {
            holder.checked = holder.toggleButton.isChecked();
        });

    }

    @Override
    public int getItemCount() {
        return filterOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ToggleButton toggleButton;
        boolean checked = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            toggleButton = itemView.findViewById(R.id.filter_option_button);

        }

    }

}
