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
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Usuario GetUserFromDatabase(int id) {

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
                        rs.getString("steamid"));

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


    public static SQLiteDatabase GetLocalDB(@Nullable Context c){
        return new LocalDatabaseManager(c).getWritableDatabase();
    }

    public static int RegistarUsuario(Usuario usuario){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.users (email, username, " +
                    "password, steamid) VALUES" +
                    "(?,?,?,?) RETURNING id");
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getSteamid());

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
                PlayedGamesFriends partida = new PlayedGamesFriends(rs.getInt("game_id"), "pepe",rs.getInt("friend_id"),rs.getString("played_time"),rs.getBoolean("winned"), rs.getString("time") );
                listaGames.add(partida);
            }

            System.out.println("Algo paso 2");
            return listaGames;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int RegistrarPartida(PlayedGamesFriends playedGamesFriends){
        try{
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.user_interactions (user_id, friend_id, " +
                    "game_id, played_time, winned, time) VALUES" +
                    "(?,?,?,?,?,?) RETURNING user_id");
            stmt.setInt(1, CurrentSession.getUsuario().getId());
            stmt.setInt(2, playedGamesFriends.getFriend_id());
            stmt.setInt(3, playedGamesFriends.getGame_id());
            stmt.setInt(4, Integer.parseInt(playedGamesFriends.getHours()));
            stmt.setBoolean(5, playedGamesFriends.getLoseWin());
            stmt.setString(6, playedGamesFriends.getFecha());

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



    public static void SaveLoginRemember(@Nullable Context c, Usuario usuario) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.execSQL("INSERT INTO login (id, email, password) VALUES (1, '"+usuario.getCorreo()+"', '"+usuario.getPassword()+"')");
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



}
