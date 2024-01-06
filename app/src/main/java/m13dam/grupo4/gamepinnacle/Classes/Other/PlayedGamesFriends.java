package m13dam.grupo4.gamepinnacle.Classes.Other;




public class PlayedGamesFriends {
    public int getGame_id() {
        return Game_id;
    }

    public int getFriend_id() {
        return Friend_id;
    }

    public String getGame_name() {
        return Game_name;
    }

    public String getFriend_name() {
        return Friend_name;
    }

    public String getHours() {
        return hours;
    }

    public Boolean getLoseWin() {
        return loseWin;
    }

    public String getFecha() {
        return fecha;
    }

    int Game_id;
    int Friend_id;
    String Game_name;
    String Friend_name;
    String hours;
    Boolean loseWin;
    String fecha;

    public PlayedGamesFriends(int game_id, int friend_id, String hours, Boolean loseWin, String fecha) {
        Game_id = game_id;
        Friend_id = friend_id;
        this.hours = hours;
        this.loseWin = loseWin;
        this.fecha = fecha;
    }

    public PlayedGamesFriends(int game_id, String friend_name, String hours, Boolean loseWin, String fecha) {
        Game_id = game_id;
        Friend_name = friend_name;
        this.hours = hours;
        this.loseWin = loseWin;
        this.fecha = fecha;
    }
    public PlayedGamesFriends(int game_id, String game_name, int friend_id, String hours, Boolean loseWin, String fecha) {
        Game_id = game_id;
        Friend_id = friend_id;
        Game_name = game_name;
        this.hours = hours;
        this.loseWin = loseWin;
        this.fecha = fecha;
    }
}
