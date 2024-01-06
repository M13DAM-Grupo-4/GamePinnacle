package m13dam.grupo4.gamepinnacle.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import m13dam.grupo4.gamepinnacle.Classes.SteamWebApi.Games;
import m13dam.grupo4.gamepinnacle.R;

public class SearchGamesAdapter extends RecyclerView.Adapter<SearchGamesAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Games> juegos;

    public SearchGamesAdapter(Context context, ArrayList<Games> juegos) {
        this.context = context;
        this.juegos = juegos;
    }

    @NonNull
    @Override
    public SearchGamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_recently_played_games, parent, false);
        return new SearchGamesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchGamesAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return juegos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
