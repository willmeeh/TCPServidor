/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author will
 */
public class Servidor {

    private String ipLocalHost = "";
    private String ipWireless = "";

    public Servidor() {
        obtainLocalIp();
        obtainWirelessIp();
    }

    private void obtainLocalIp() {
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

    private void obtainWirelessIp() {
        try {
            String aux = InetAddress.getLocalHost().toString();
            ipLocalHost = aux.split("/")[1];
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIpLocalHost() {
        return ipLocalHost;
    }

    public String getIpWireless() {
        return ipWireless;
    }
}
