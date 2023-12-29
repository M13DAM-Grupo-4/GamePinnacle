package m13dam.grupo4.gamepinnacle.Classes.Other;




public class PlayedGamesFriends {

    int Game_id;
    int Friend_id;
    String Game_name;
    String Friend_name;
    String hours;
    String loseWin;
    String fecha;

    public PlayedGamesFriends(int game_id, String friend_name, String hours, String loseWin, String fecha) {
        Game_id = game_id;
        Friend_name = friend_name;
        this.hours = hours;
        this.loseWin = loseWin;
        this.fecha = fecha;
    }
    public PlayedGamesFriends(String game_name, int friend_id, String hours, String loseWin, String fecha) {
        Friend_id = friend_id;
        Game_name = game_name;
        this.hours = hours;
        this.loseWin = loseWin;
        this.fecha = fecha;
    }
}
