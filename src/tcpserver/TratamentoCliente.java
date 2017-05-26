/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author will
 */
public class TratamentoCliente implements Runnable{
    
    Socket socketCliente;
    
    public TratamentoCliente(Socket socket){
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try {
            
            Scanner entrada = new Scanner(socketCliente.getInputStream());
            
            while (entrada.hasNextLine()) {
                System.out.println(entrada.nextLine());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(TratamentoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
