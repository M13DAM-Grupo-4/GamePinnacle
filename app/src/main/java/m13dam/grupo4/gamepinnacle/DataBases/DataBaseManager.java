package m13dam.grupo4.gamepinnacle.DataBases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.Amigos;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
import m13dam.grupo4.gamepinnacle.Classes.Other.Juego;
import m13dam.grupo4.gamepinnacle.Classes.Other.PlayedGamesFriends;
import m13dam.grupo4.gamepinnacle.Classes.Other.Usuario;

public class DataBaseManager {
    public static Connection CreateConnection(){

        String DB_DATABASE = BuildConfig.dbdatabase;
        String DB_HOST = BuildConfig.dbhost;
        int DB_PORT = Integer.parseInt(BuildConfig.dbport);
        String jdbcUrl = "jdbc:postgresql://"+ DB_HOST +":"+DB_PORT+"/"+ DB_DATABASE;
        String user = BuildConfig.dbuser;
        String password = BuildConfig.dbpassword;
        String sslMode = "require";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("sslmode", sslMode);

        try {
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int Login(String mail, String pass){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.users WHERE email=? AND password=?");
            stmt.setString(1, mail);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Usuario GetUserFromDatabase(int id) {

        if (id == -1){
            return null;
        }

        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM public.users WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("steamid"),
                        rs.getString("steamapikey"));

                return usuario;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String usuario(String mail, String pass){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT username FROM public.users WHERE email=? AND password=?");
            stmt.setString(1, mail);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String mySteamId(String mail){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT steamid FROM public.users WHERE email=?");
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean comprobarCorreo(String mail){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT COUNT(email) FROM public.users WHERE email=?");
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getIdFromMail(String mail){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.users WHERE email=?");
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static SQLiteDatabase GetLocalDB(@Nullable Context c){
        return new LocalDatabaseManager(c).getWritableDatabase();
    }

    public static int RegistarUsuario(Usuario usuario, String steamApiKey){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.users (email, username, " +
                    "password, steamid, steamapikey) VALUES" +
                    "(?,?,?,?,?) RETURNING id");
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getSteamid());
            stmt.setString(5, steamApiKey);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt(1);
            }

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int ActualizarContraseña(int id, String nuevaContraseña){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE public.users SET password=? WHERE id=?");
            stmt.setString(1, nuevaContraseña);
            stmt.setInt(2, id);

            stmt.executeUpdate();

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int RegistrarAmigo(Amigos amigo, int id){
        try{
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.friends (name, f_surname, " +
                    "s_surname, picture, user_id) VALUES" +
                    "(?,?,?,?,?) RETURNING user_id");
            stmt.setString(1, amigo.getNombre());
            stmt.setString(2, amigo.getApellidoUno());
            stmt.setString(3, amigo.getApellidoDos());
            stmt.setString(4, amigo.getPicture());
            stmt.setInt(5, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt("user_id");
            }

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public static int listaJuegosRegistrados (String nombre) {

        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.games WHERE name=? and id_user=?");
            stmt.setString(1, nombre);
            stmt.setInt(2,CurrentSession.getUsuario().getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public static ArrayList<Juego> listaJuegosIGDB () {
        ArrayList<Juego> listaJuego = new ArrayList<>();
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id, name, horas, imagen FROM public.games WHERE id_user=? AND imagen IS NOT NULL AND imagen <> ''");
            stmt.setInt(1,CurrentSession.getUsuario().getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Juego juego = new Juego (rs.getInt("id"),rs.getString("name"),rs.getString("horas"), rs.getString("imagen"));
                listaJuego.add(juego);

            }
            return listaJuego;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static int RegistrarJuego(Juego juego){
        try{
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.games (name, " +
                    "horas, id_user, imagen) VALUES" +
                    "(?,?,?,?) RETURNING id");
            stmt.setString(1, juego.getNombre());
            stmt.setString(2, horasTotalesPartidas(juego.getId()));
            stmt.setInt(3,CurrentSession.getUsuario().getId());
            stmt.setString(4,juego.getImagen());

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt("id");
            }

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int actualizarNombreUsuario(String nuevoNombre) {
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE public.users SET username = ? WHERE username = ?");
            stmt.setString(1, nuevoNombre);
            stmt.setString(2, CurrentSession.getUsuario().getUsuario());
            stmt.executeUpdate();

            stmt.close();
            c.close();

            // Verificar si la actualización fue exitosa (si se afectó al menos una fila)
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
    public static int actualizarContraseñaUsuario(String nuevaContraseña) {
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("UPDATE public.users SET password = ? WHERE password = ?");
            stmt.setString(1, nuevaContraseña);
            stmt.setString(2, CurrentSession.getUsuario().getPassword());
            stmt.executeUpdate();


            stmt.close();
            c.close();

            // Verificar si la actualización fue exitosa (si se afectó al menos una fila)
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    public static String getSteamApiKey(int userID) {

        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT steamapikey FROM public.users WHERE id=?");
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return rs.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList <Amigos> getFriendList(int userID) {
        System.out.println("Algo paso normal");
        ArrayList <Amigos> listaAmigos = new ArrayList<>();
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt0 = c.prepareStatement("SELECT name, f_surname, s_surname, user_id, picture, friend_id from public.friends where user_id=" + userID);
            ResultSet rs = stmt0.executeQuery();
            System.out.println("Algo paso 1");

            while (rs.next()){
                Amigos amigo = new Amigos(rs.getString("name"),rs.getString("f_surname"),rs.getString("s_surname"),rs.getInt("friend_id") );
                amigo.setPicture(rs.getString("picture"));
                listaAmigos.add(amigo);
            }

            System.out.println("Algo paso 2");
            return listaAmigos;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<PlayedGamesFriends> partidasAmigos(int amigoId) {
        System.out.println("Algo paso normal");
        ArrayList <PlayedGamesFriends> listaGames = new ArrayList<>();
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt0 = c.prepareStatement("SELECT a.game_id, a.friend_id, a.played_time, a.winned, a.time, b.name FROM public.user_interactions AS a LEFT JOIN public.games AS b ON a.game_id = b.id WHERE friend_id = ?");
            stmt0.setInt(1, amigoId);
            ResultSet rs = stmt0.executeQuery();
            System.out.println("Algo paso 1");

            while (rs.next()){
                PlayedGamesFriends partida = new PlayedGamesFriends(rs.getInt("game_id"), rs.getString("name"),rs.getInt("friend_id"),rs.getString("played_time"),rs.getBoolean("winned"), rs.getString("time") );
                listaGames.add(partida);
            }

            System.out.println("Algo paso 2");
            return listaGames;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static ArrayList<PlayedGamesFriends> partidasJuegos (int gameId) {
        System.out.println("Algo paso normal");
        ArrayList <PlayedGamesFriends> listaGames = new ArrayList<>();
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt0 = c.prepareStatement("SELECT a.game_id, a.friend_id, a.played_time, a.winned, a.time, b.name, c.name as friend_name " +
                    "FROM public.user_interactions AS a LEFT JOIN public.games AS b ON a.game_id = b.id LEFT JOIN public.friends AS c ON a.friend_id=c.friend_id WHERE game_id = ?");
            stmt0.setInt(1, gameId);
            ResultSet rs = stmt0.executeQuery();
            System.out.println("Algo paso 1");

            while (rs.next()){
                PlayedGamesFriends partida = new PlayedGamesFriends(rs.getInt("game_id"), rs.getString("name"),rs.getInt("friend_id"),rs.getString("played_time"),rs.getBoolean("winned"), rs.getString("time") );
                partida.setFriend_name(rs.getString("friend_name"));
                listaGames.add(partida);
            }

            System.out.println("Algo paso 2");
            return listaGames;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static String horasTotalesPartidas (int juegoId) {
        System.out.println("Algo paso normal");
        int horas = 0;
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt0 = c.prepareStatement("SELECT played_time FROM public.user_interactions  WHERE game_id = ?");
            stmt0.setInt(1, juegoId);
            ResultSet rs = stmt0.executeQuery();
            System.out.println("Algo paso 1");

            while (rs.next()){
                 horas += Integer.parseInt(rs.getString("played_time"));

            }

            System.out.println("Algo paso 2");
            return String.valueOf(horas);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int RegistrarPartida(Juego juegoPartida){

        try{
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.user_interactions (user_id, friend_id, " +
                    "game_id, played_time, winned, time) VALUES" +
                    "(?,?,?,?,?,?) RETURNING user_id");
            stmt.setInt(1, CurrentSession.getUsuario().getId());
            stmt.setInt(2, juegoPartida.getFriend_id());
            stmt.setInt(3, idJuego(Juego.getNombreJuego()));
            stmt.setInt(4, juegoPartida.getPlayTime());
            stmt.setBoolean(5, juegoPartida.getWinLose());
            stmt.setString(6, String.valueOf(juegoPartida.getPlayTime()));

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return rs.getInt("user_id");
            }

            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    public static int idJuego (String nombre) {

        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.games WHERE name=? and id_user=?");
            stmt.setString(1, nombre);
            stmt.setInt(2,CurrentSession.getUsuario().getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int removeFriend(int friendId) {
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("DELETE FROM public.friends WHERE friend_id = ?");
            stmt.setInt(1, friendId);
            int rowsAffected = stmt.executeUpdate();

            stmt.close();
            c.close();

            // Verificar si la actualización fue exitosa (si se afectó al menos una fila)
            return rowsAffected > 0 ? 1 : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



    public static int LoginRemember(@Nullable Context c) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            Cursor cr = dbl.rawQuery("SELECT email,password FROM login WHERE id=1", null);

            if (cr.moveToFirst()) {
                do {
                    String email = cr.getString(0);
                    String password = cr.getString(1);

                    int userId = DataBaseManager.Login(email, password);

                    return userId;
                } while (cr.moveToNext());
            }

            cr.close();
            dbl.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static String sqlitemail(@Nullable Context c) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            Cursor cr = dbl.rawQuery("SELECT mail FROM login WHERE remember=true AND id=1", null);

            if (cr.moveToFirst()) {
                do {
                    // Passing values
                    String mail = cr.getString(0);
                    System.out.println(mail);

                    cr.close(); // Close the cursor before closing the database
                    dbl.close(); // Close the database

                    return mail;
                } while (cr.moveToNext());
            }

            cr.close(); // Close the cursor before closing the database
            dbl.close(); // Close the database

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void SaveLoginRemember(@Nullable Context c, Usuario usuario, String TwitchToken) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.execSQL("INSERT INTO login (id, email, password, twitch_token) VALUES (1, '"+usuario.getCorreo()+"', '"+usuario.getPassword()+"', '"+TwitchToken+"')");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void unLoginRemember(@Nullable Context c) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sqliteTwitchToken(@Nullable Context c) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            Cursor cr = dbl.rawQuery("SELECT twitch_token FROM login WHERE id=1", null);

            if (cr.moveToFirst()) {
                do {
                    // Passing values
                    String twitch_token = cr.getString(0);
                    System.out.println(twitch_token);

                    cr.close(); // Close the cursor before closing the database
                    dbl.close(); // Close the database

                    return twitch_token;
                } while (cr.moveToNext());
            }

            cr.close(); // Close the cursor before closing the database
            dbl.close(); // Close the database

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
