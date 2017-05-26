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
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tcpserver.TCPServer.frame;

/**
 *
 * @author will
 */
public class Servidor {

    private String ipWireless = "";
    private String porta = "";

    public Servidor() {
        obtainWirelessIp();
    }

    private void obtainWirelessIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ipWireless = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ipWireless);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo Inicializa o servidor de acordo com a porta informada, e
     * aguarda conexoes com clientes, onde toda vez que um clente se conectar,
     * uma thread eh instanciada para fazer a troca de mensagens. Isto ocorre
     * para que o servidor possa realizar a troca de mensagens com varios
     * clientes ao mesmo tempo.
     */
    public void iniciarServidor() {
        try {
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Porta 12345 aberta!");

            while (true) {
                Socket cliente = servidor.accept();

                // Exibe no log da interface, a nova conexao.
                frame.putTextLog("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                Runnable r = new TratamentoCliente(cliente);
                new Thread(r).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIpWireless() {
        return ipWireless;
    }
}
