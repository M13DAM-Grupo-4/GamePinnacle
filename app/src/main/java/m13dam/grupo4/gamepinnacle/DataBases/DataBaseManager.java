package m13dam.grupo4.gamepinnacle.DataBases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import m13dam.grupo4.gamepinnacle.BuildConfig;
import m13dam.grupo4.gamepinnacle.Classes.Other.CurrentSession;
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
