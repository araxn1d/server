/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import threetierapp.person.Person;
import threetierapp.person.PersonFactory;

/**
 *
 * @author Жека
 */
public class ThreeTierApp {

    protected Vector<ServerThread> serverThreads;
    InetAddress myAddress;
    int Port;
    protected ServerSocket mServerSocket;

    public ThreeTierApp(int port, InetAddress address) throws IOException {
        try {
            myAddress = address;
            serverThreads = new Vector<ServerThread>();
            Port = port;
            mServerSocket = new ServerSocket(port, 0, myAddress);
            while (true) {
                Socket nSocket = mServerSocket.accept();
                System.out.println("There is connection");
                serverThreads.add(new ServerThread(this, nSocket));
            }


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            mServerSocket.close();
        }
    }

    public ServerThread getServerThreadById(int id) {
        return serverThreads.get(id);
    }

    public void deleteServerThread(ServerThread toDel) {
        serverThreads.remove(toDel);
    }

    public static void clearDB() throws SQLException {
        byte[] b = {0};
        ArrayList<Person> pers = PersonFactory.getInstance().getIPers().getPersons();
        for (Person p : pers) {
            p.setFile(b);
            p.setImgData(b);
        }
        PersonFactory.getInstance().getIPers().updatePerson(pers);
    }

    public static void main(String[] args) throws SQLException {
        //clearDB();
        try {
            
            ThreeTierApp testServer = new ThreeTierApp(3225, InetAddress.getByName("localhost"));
            ChatServer server = new ChatServer(8080);
            server.start();
            server.join();
            System.out.println("Starting server...Damn");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
