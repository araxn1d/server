package threetierapp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import com.google.gson.Gson;
import comands.AddPerson;
import comands.Command;
import comands.DelPerson;
import comands.GetPersons;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import threetierapp.person.Person;
import threetierapp.person.PersonFactory;

/**
 * @author Roman Kostyrko 02.03.2012 class provides communication with
 *         ClientThread
 */
public class ServerThread implements Runnable {

    private ArrayList<Person> pers;
    private String name;
    private Socket clientSocket;
    private ThreeTierApp server;
    private Thread playerThread;
    int id;
    /* char symbol of player */

    /**
     * constructor
     * 
     * @param server
     * @param clientSocket
     */
    public ServerThread(ThreeTierApp server, Socket clientSocket) {
        System.out.println("Server starts+"+clientSocket.getInetAddress());
        int iid = server.serverThreads.size();
        name = "Player" + iid; // Default
        this.server = server;
        this.clientSocket = clientSocket;
        playerThread = new Thread(this);
        playerThread.start();
    }

    /**
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            void
     */
    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    /**
     * @param commandString
     *            void Send string to client socket
     */
    private void sendCommand(String commandString) {
        PrintWriter out;
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream())), true);
            out.println(commandString);
            System.out.println("SEND COMMAND: threadID=" + this.server.serverThreads.indexOf(this) + " :  " + commandString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPersons() {
        try {
            pers = PersonFactory.getInstance().getIPers().getPersons();
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Command com = new Command(new GetPersons(pers));
        sendCommand(com.serialize());
    }
    
     public void addNewPerson(Person p) {
        try {
            PersonFactory.getInstance().getIPers().updatePerson(p);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Command com = new Command(new GetPersons(pers));
        sendCommand(com.serialize());
    }
     
     public void DelPerson(Person p){
          try {
            PersonFactory.getInstance().getIPers().delPerson(p);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    /**
     * @param getcommand
     *            void Define type of received command from client and call
     *            processing methods
     */
    private void operateCommand(String getcommand) {
        Gson gson = new Gson();
        System.out.println(getcommand);
        Command command = Command.deserialize(getcommand);
        System.out.println("GET COMMAND: threadID=" + this.server.serverThreads.indexOf(this) + " :COMMAND_TYPE: " + command.getType() + " " + command.getStringData());
        switch (command.getType()) {
//		case Command.REQUEST_GAME: {
//			
//			RequestGame requestgame = gson.fromJson(command.getStringData(),
//					RequestGame.class);
//			requestNewGametoThread(requestgame.getCurId());
//			break;
//		}
            case Command.GET_PERSONS: {
                sendPersons();
                break;
            }
                case Command.ADD_PERSON: {
                AddPerson add=gson.fromJson(command.getStringData(), AddPerson.class);
                addNewPerson(add.getPerson());
                break;
            }
                    
                case Command.DEL_PERSON: {
                DelPerson del=gson.fromJson(command.getStringData(),DelPerson.class);
                DelPerson(del.getDelPerson());
                break;
            }
            default:
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run() main thread method, where listening of
     * socket if performed
     */
    @Override
    public void run() {

        while (true) {
            Scanner in = null;
            try {
                in = new Scanner(new InputStreamReader(
                        clientSocket.getInputStream()));
            } catch (IOException e1) {
                server.serverThreads.remove(this);
                e1.printStackTrace();
            }

            String getStringCommand = "";
            try{
            getStringCommand = in.nextLine();  }
            catch(NoSuchElementException ex){
                System.out.print("Disconect client");
            }
            operateCommand(getStringCommand);
        }
    }
}
