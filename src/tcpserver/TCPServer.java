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

    public static MainFrame serverUi = new MainFrame();

    public static Comandos comandos = new Comandos();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {

        //        String temperatura = comandos.getTemperatura("-29.698472", "-52.436200");
//        System.out.println(temperatura);
        Servidor server = new Servidor();

        serverUi.setVisible(true);
        serverUi.lblIpWireless.setText(server.getIpWireless());
        serverUi.lblPorta.setText(String.valueOf(server.getPorta()));

        // Apos popular o ip, inicia o servidor
        server.iniciarServidor();

    }

}
