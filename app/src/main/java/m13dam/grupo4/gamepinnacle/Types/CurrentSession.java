package m13dam.grupo4.gamepinnacle.Types;

public class CurrentSession {
    private static int UserID;
    private static String mail;
    private static String userName;
    private static String steamId;

    public static String getSteamId() {
        return steamId;
    }

    public static void setSteamId(String steamId) {
        CurrentSession.steamId = steamId;
    }

    public static int getUserID() {

        return UserID;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        CurrentSession.mail = mail;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        CurrentSession.userName = userName;
    }

    public static void setUserID(int userID) {
        UserID = userID;
    }
}
