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

    public static int Login(String user, String pass){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT id FROM public.NombreTabla WHERE usuario=? AND contra=?");
            stmt.setString(1, user);
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
    public static SQLiteDatabase GetLocalDB(@Nullable Context c){
        return new LocalDatabaseManager(c).getWritableDatabase();
    }

    public static int RegistarUsuario(Usuario usuario){
        try {
            Connection c = CreateConnection();
            PreparedStatement stmt = c.prepareStatement("INSERT INTO public.usuarios SET (mail, usuario, " +
                    "contraseña VALUES" +
                    "(?,?,?) RETURNING id");
            stmt.setString(1, usuario.getCorreo());
            stmt.setString(2, usuario.getUsuario());
            stmt.setInt(3, usuario.getPassword());

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

    public static int LoginRemember(@Nullable Context c){
        try {
            SQLiteDatabase dbl = GetLocalDB(c);
            Cursor cr = dbl.rawQuery("SELECT user_id FROM login WHERE remember=true AND id=1", null);
            if (cr.moveToFirst()){
                do {
                    // Passing values
                    int ResId = Integer.parseInt(cr.getString(0));
                    return ResId;
                } while(cr.moveToNext());
            }
            dbl.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void SaveLoginRemember(int userid, @Nullable Context c){
        try{
            SQLiteDatabase dbl = GetLocalDB(c);
            dbl.execSQL("DELETE FROM login");
            dbl.execSQL("INSERT INTO login (id, user_id, remember) VALUES (1,'"+userid+"', true)");
            dbl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
