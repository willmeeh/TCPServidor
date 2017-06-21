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
import static tcpserver.TCPServer.serverUi;

/**
 *
 * @author will
 */
public class Servidor {

    private String ipWireless = "";
    private int porta = 8000;

    public Servidor() {
        this.ipWireless = Utils.obtainWirelessIp();
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

            putMsgInfo("Servidor iniciado com sucesso, aguardando requisições!");
            ServerSocket servidor = new ServerSocket(porta);

            while (true) {
                Socket cliente = servidor.accept();
                // Exibe no log da interface, a nova conexao.
                putMsgInfo("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

                // Instancia uma nova thread para realizar o tratamento do cliente que se conectou
                Runnable r = new TratamentoCliente(cliente, cliente.getInetAddress().getHostAddress());
                new Thread(r).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIpWireless() {
        return ipWireless;
    }

    public int getPorta() {
        return porta;
    }

    
    /**
     * A partir do metodo estatico que esta na classe MainFrame, 
     * adiciona uma mensagem de texto no log.
     * @param msg 
     */
    public static void putMsgInfo(String msg) {
        String logText = "";

        logText = "[ " + Utils.getCurrentDate() + " - " + Utils.getCurrentTime() + " ] INFO: ";
        logText += msg;
        serverUi.putTextLog(logText);
    }
}
