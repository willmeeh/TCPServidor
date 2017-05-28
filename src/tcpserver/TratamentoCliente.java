/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static tcpserver.TCPServer.serverUi;

/**
 *
 * @author will
 */
public class TratamentoCliente implements Runnable {

    Socket socketCliente;
    String ipCliente = "";

    public TratamentoCliente(Socket socket, String ipCliente) {
        this.socketCliente = socket;
        this.ipCliente = ipCliente;
    }

    @Override
    public void run() {
        try {

            Scanner entrada = new Scanner(socketCliente.getInputStream());
            PrintStream saida = new PrintStream(this.socketCliente.getOutputStream());

            while (entrada.hasNextLine()) {
                String resposta = entrada.nextLine();

                Servidor.putMsgInfo("O ip " + ipCliente + " solicitou o comando " + entrada.nextLine());

                if (resposta.equals(Comandos.COMMAND_KEY_AUTORES)) {
                    saida.println(Comandos.getAutores());
                    Servidor.putMsgInfo("Enviando lista de autores para " + ipCliente);
                } else {
                    String token[] = new String[10];
                    token = resposta.split("\\?");
                    System.out.println(""+token[0] + token[1]);
                    if (token[0].equals(Comandos.COMMAND_KEY_TEMPERATURA)) {
                        
                        
                        String token2[] = token[1].split("coordenadas=");
                        saida.println(Comandos.getTemperatura(token2[1]));
                        Servidor.putMsgInfo("Enviando temperatura para " + ipCliente);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TratamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
