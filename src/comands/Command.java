package comands;

import com.google.gson.Gson;

public class Command {

    public static final int GET_PERSONS = 1;
    public static final int UPDATE_PERSONS = 2;
    public static final int GET_PERSONS_BY_ID = 3;
    public static final int DEL_PERSON = 4;
    public static final int ADD_PERSON = 5;
    
    private String data;
    private int comandType;

    public Command(ICommand o) {
        comandType = o.getType();
        Gson gson = new Gson();
        data = gson.toJson(o);
    }

    public String getStringData() {
        return data;
    }

    static public Command deserialize(String s) {
        Gson gson = new Gson();
        Command com = gson.fromJson(s, Command.class);
        return com;
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType() {
        return comandType;
    }
}
