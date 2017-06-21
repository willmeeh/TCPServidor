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

/**
 * Thread responsavel por controlar as mensagens que o cliente enviou
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

            // looping para monitorar as mensagens que o cliente envia
            while (entrada.hasNextLine()) {
                // obtem a mensagem que o cliente enviou
                String resposta = entrada.nextLine();

                // adiciona uma mensagem ao log da interface
                Servidor.putMsgInfo("O ip " + ipCliente + " solicitou o comando " + entrada.nextLine());

                // verifica qual foi o comando que o cliente enviu, e da o retorno de acordo
                if (resposta.equals(Comandos.COMMAND_KEY_AUTORES)) {
                    saida.println(Comandos.getAutores());
                    Servidor.putMsgInfo("Enviando lista de autores para " + ipCliente);
                } else if (resposta.equals(Comandos.COMMAND_KEY_NOTICIAS)) {
                    String noticia = Comandos.getNoticia();
                    if (noticia.equals("")) {
                        saida.println("Nenhuma notícia encontrada!");
                    } else {
                        saida.println(noticia);
                    }
                    Servidor.putMsgInfo("Enviando uma notícia aleatória para " + ipCliente);
                }  
                else {
                    String token[] = new String[10];
                    token = resposta.split("\\?");
                    System.out.println(""+token[0] + token[1]);
                    if (token[0].equals(Comandos.COMMAND_KEY_TEMPERATURA)) {
                        
                        
                        String token2[] = token[1].split("coordenadas=");
                        Servidor.putMsgInfo("Enviando temperatura para " + ipCliente);
                        saida.println(Comandos.getTemperatura(token2[1]));
                    } else {
                        Servidor.putMsgInfo(ipCliente + "Solicitou um comando inválido");
                        saida.println("Comando inválido");
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(TratamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
