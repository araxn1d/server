/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp;

/**
 *
 * @author Жека
 */
import comands.Command;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.websocket.WebSocket;
import threetierapp.person.Person;
import threetierapp.person.PersonFactory;

public class ChatSocket implements WebSocket, WebSocket.OnFrame,
        WebSocket.OnBinaryMessage, WebSocket.OnTextMessage, WebSocket.OnControl {

    private int prevCommand;
    private ArrayList<Person> pers;
    protected FrameConnection connection;
    private ChatServer server;

    public ChatSocket(ChatServer server) {
        this.server = server;
    }

    public FrameConnection getConnection() {
        return connection;
    }

    public void onOpen(Connection connection) {

        System.out.printf("%s#onOpen %s\n", this.getClass().getSimpleName(),
                connection);
    }

    public void onHandshake(FrameConnection connection) {
        System.out.printf("%s#onHandshake %s %s\n", this.getClass().getSimpleName(), connection, connection.getClass().getSimpleName());
        this.connection = connection;
        //server.getChatClients().add(this);
    }

    public void onClose(int code, String message) {
        System.out.printf("%s#onDisonnect %d %s\n", this.getClass().getSimpleName(), code, message);
        //server.getChatClients().remove(this);
    }

    public boolean onFrame(byte flags, byte opcode, byte[] data, int offset,
            int length) {
        System.out.printf("%s#onFrame %s|%s %s\n", this.getClass().getSimpleName(), TypeUtil.toHexString(flags), TypeUtil.toHexString(opcode), TypeUtil.toHexString(data, offset, length));
        return false;
    }

    public boolean onControl(byte controlCode, byte[] data, int offset,
            int length) {
        System.out.printf("%s#onControl  %s %s\n", this.getClass().getSimpleName(), TypeUtil.toHexString(controlCode), TypeUtil.toHexString(data, offset, length));
        return false;
    }

    public void onMessage(String data) {
        System.out.printf("%s#onMessage     %s\n", this.getClass().getSimpleName(), data);
        if (data.equals("1")) {
            prevCommand = 1;
            System.out.println("GET PERSONS");
            sendPersons();
        }
        if (data.equals("4")) {
            prevCommand = 4;
        }
        if (data.equals("5")) {
            prevCommand = 5;
        } else {
            if (prevCommand == Command.ADD_PERSON) {
                System.out.println("ADD PERSONS");
                String[] str = data.split(";");
                Person p = new Person(Integer.valueOf(str[0]), str[1], str[2], str[3],
                        str[4], str[5], str[6]);
                addNewPerson(p);
            }
            if (prevCommand == Command.DEL_PERSON) {
                try {
                    System.out.println("DEL PERSONS");
                    ArrayList<Person> pers = PersonFactory.getInstance().getIPers().getPersons();
                    for (Person p : pers) {
                        if (p.getId() == Integer.valueOf(data)) {
                            DelPerson(p);
                        }
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    public void onMessage(byte[] data, int offset, int length) {
        System.out.printf("%s#onMessage     %s\n", this.getClass().getSimpleName(), TypeUtil.toHexString(data, offset, length));
    }

    public void sendPersons() {
        try {
            pers = PersonFactory.getInstance().getIPers().getPersons();
            for (Person p : pers) {
                StringBuilder builder = new StringBuilder();
                builder.append(String.valueOf(p.getId())).append(";");
                builder.append(p.getFirstName()).append(";").append(p.getLastName()).append(";");
                builder.append(p.getBirthDate()).append(";").append(p.getPhoneNum()).append(";");
                builder.append(p.geteMail()).append(";").append(p.getUrl()).append(";");
                System.out.println(builder.toString().trim());
                sendString(builder.toString().trim());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewPerson(Person p) {
        try {
            PersonFactory.getInstance().getIPers().updatePerson(p);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void DelPerson(Person p) {
        try {
            PersonFactory.getInstance().getIPers().delPerson(p);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendCommand(String commandString) {
        try {
            this.connection.sendMessage(commandString);
        } catch (IOException ex) {
            Logger.getLogger(ChatSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void sendString(String commandString) {
        try {
            if (commandString == null) {
                this.connection.sendMessage("_");
            }
            this.connection.sendMessage(commandString);
        } catch (IOException ex) {
            Logger.getLogger(ChatSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
