package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import m13dam.grupo4.gamepinnacle.Classes.Other.FilterOption;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.AddGamesMenu;
import m13dam.grupo4.gamepinnacle.Fragments.Menus.GameInfo;
import m13dam.grupo4.gamepinnacle.R;

public class FilterGamesAdapter extends RecyclerView.Adapter<FilterGamesAdapter.ViewHolder> {

    private Context mContext_jvm;
    public static View view;
    private ArrayList<FilterOption> filterOptions;

    public static boolean steamFilter = true;
    public static boolean igdbFilter = true;

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

                        AddGamesMenu addGamesMenu = new AddGamesMenu();
                        FragmentManager fragmentManager = ((AppCompatActivity) v.getContext()).getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_fragment_container, addGamesMenu)
                                .addToBackStack(null)
                                .commit();

            });
        }

        if (filterOptions.get(position).getName().equals("Steam")) {

            holder.toggleButton.setTextOn(filterOptions.get(position).getName()+": ON");
            holder.toggleButton.setTextOff(filterOptions.get(position).getName()+": OFF");
            holder.toggleButton.setChecked(holder.checked);

            holder.toggleButton.setOnClickListener(v -> {
                steamFilter = holder.toggleButton.isChecked();
                updateGameList(igdbFilter, steamFilter);
                reloadGameList();
            });
        }

        if (filterOptions.get(position).getName().equals("IGDB")) {

            holder.toggleButton.setTextOn(filterOptions.get(position).getName()+": ON");
            holder.toggleButton.setTextOff(filterOptions.get(position).getName()+": OFF");
            holder.toggleButton.setChecked(holder.checked);

            holder.toggleButton.setOnClickListener(v -> {
                igdbFilter = holder.toggleButton.isChecked();
                updateGameList(igdbFilter, steamFilter);
                reloadGameList();
            });
        }

        updateGameList(igdbFilter, steamFilter);
        reloadGameList();

    }

    private void reloadGameList(){
        RecentlyPlayedGamesAdapter recycleview_jvm = new RecentlyPlayedGamesAdapter(view.getContext(), RecentlyPlayedGamesAdapter.listaJuegos, "all");
        RecyclerView recyclerView = view.findViewById(R.id.reciclePrueba);

        recyclerView.setAdapter(recycleview_jvm);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext_jvm));
    }

    private void updateGameList(boolean igdb, boolean steam){
        ArrayList<Juego> jSteam = new ArrayList<>();
        ArrayList<Juego> jIgdb = new ArrayList<>();

        if (igdb) {
            jIgdb = RecentlyPlayedGamesAdapter.listaJuegosIgdb;
        }

        if (steam) {
            jSteam = RecentlyPlayedGamesAdapter.listaJuegosSteam;
        }

        ArrayList<Juego> juegosTotales = (ArrayList<Juego>) Stream.concat(jIgdb.stream(), jSteam.stream()).collect(Collectors.toList());

        RecentlyPlayedGamesAdapter.listaJuegos = juegosTotales;
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
