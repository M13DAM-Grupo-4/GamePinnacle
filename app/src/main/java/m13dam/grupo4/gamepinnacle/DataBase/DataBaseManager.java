package m13dam.grupo4.gamepinnacle.DataBase;

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
import m13dam.grupo4.gamepinnacle.Types.CurrentSession;
import m13dam.grupo4.gamepinnacle.Types.Usuario;

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

    public static int comprobarCorreo(String mail){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT email FROM public.users WHERE email=?");
            stmt.setString(1, mail);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static SQLiteDatabase GetLocalDB(@Nullable Context c){
        return new LocalDatabaseManager(c).getWritableDatabase();
    }

    public static int RegistarUsuario(Usuario usuario){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.users (email, username, " +
                    "password) VALUES" +
                    "(?,?,?) RETURNING id");
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setString(3, usuario.getPassword());

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
            Cursor cr = dbl.rawQuery("SELECT user_id, mail, username FROM login WHERE remember=true AND id=1", null);

            if (cr.moveToFirst()) {
                do {
                    // Passing values
                    int userId = cr.getInt(0);
                    String mail = cr.getString(1);
                    String username = cr.getString(2);

                    // Set values in CurrentSession
                    CurrentSession.setMail(mail);
                    CurrentSession.setUserName(username);

                    cr.close(); // Close the cursor before closing the database
                    dbl.close(); // Close the database

                    return userId;
                } while (cr.moveToNext());
            }

            cr.close(); // Close the cursor before closing the database
            dbl.close(); // Close the database

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



    public static void SaveLoginRemember(int userid, @Nullable Context c, String mail, String username) {
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.execSQL("INSERT INTO login (id, user_id, remember, mail, username) VALUES (1, " + userid + ", 1, '" + mail + "', '" + username + "')");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
