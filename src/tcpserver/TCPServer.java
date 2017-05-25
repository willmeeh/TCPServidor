/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author will
 */
public class TCPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {

        try {
            Servidor server = new Servidor();
            
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Porta 12345 aberta!");
            
            
            MainFrame frame = new MainFrame();

            frame.setVisible(true);
            
            frame.putTextLog("Porta 12345 aberta!");
            frame.lblIpServer.setText(server.getIpLocalHost());
            frame.lblIpWireless.setText(server.getIpWireless());

            Socket cliente = servidor.accept();
            frame.putTextLog("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

            Scanner s = new Scanner(cliente.getInputStream());
            while (s.hasNextLine()) {
                System.out.println(s.nextLine());
                frame.putTextLog(s.nextLine());
            }

            s.close();
            servidor.close();
            cliente.close();

        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
