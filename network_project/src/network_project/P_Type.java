
package network_project;

/**
 *
 * @author Mina Malak
 * @author Mina Abdelfady
 */

import java.awt.Color;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jpcap.net.*;
import net.sourceforge.jpcap.util.AsciiHelper;
import net.sourceforge.jpcap.util.HexHelper;


public class P_Type {
   
    static int i = 0;
    static PrintWriter writer;
    String ourfilter;
    
   
    
    String protocoll[] = {"HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST", "TCP", "CBT", "EGP", "IGP", "BBN",
                            "NV2", "PUP", "ARGUS", "EMCON", "XNET", "CHAOS", "UDP", "mux" ,"http" ,"https","  "};
            String srcIp, dstIp;
            int srcPort=0, dstPort=0;
            String info="";
            int pktlen=0;
           static byte[] data;
            HexHelper hex=new HexHelper();
            AsciiHelper as=new AsciiHelper();
            BufferedReader bufferReader;
            
            boolean write_flag=true;
            
    public byte[] receivePacket(Packet packet) throws UnsupportedEncodingException {
      try { if (packet != null) {
          if (i==0)
          {writer=new PrintWriter("out.txt");  
          }
          
        writer.append(packet.toColoredString(true) + "\n");
        System.out.println(packet.toColoredString(true) + "\n");
        writer.append(AsciiHelper.toString(packet.getData()) + "\n");
        System.out.println(AsciiHelper.toString(packet.getData()) + "\n");    
         writer.append("packet number " + i + " :" + "\n");
        System.out.println("packet number " + i + " :" + "\n");
        
        
        if(packet instanceof IPPacket)
        {
      IPPacket ippacket=(IPPacket)packet;
 

     
int ppp=ippacket.getIPProtocol();
String proto=protocoll[ppp];
srcIp=ippacket.getSourceAddress();
dstIp=ippacket.getDestinationAddress();
pktlen=ippacket.getLength();


 writer.append(" \n destination ip is :"+dstIp);
 
System.out.println(" \n destination ip is :"+dstIp);
 writer.append("\n source ip :"+srcIp);
System.out.println("\n source ip :"+srcIp);
 writer.append(" \npacket length :"+pktlen);
System.out.println(" \npacket length :"+pktlen);
 writer.append("**********************************************************************");
System.out.println("**********************************************************************");

writer.append("Packet's Type");
System.out.println("Packet's Type");
writer.append("******************************************************************************");
System.out.println("******************************************************************************");
             
                if (proto.equals(("TCP"))) {
                    writer.append(" \n TCP packet");
                    System.out.println(" \n TCP packet");
                    TCPPacket tcpPkt = (TCPPacket) packet;
                    srcPort = tcpPkt.getSourcePort();
                    dstPort = tcpPkt.getDestinationPort();
                    data=tcpPkt.getData();
                    
                   String isodata=new String(data,"US-ascii");
                   writer.append("destination port  :" + dstPort);
                    System.out.println("destination port  :" + dstPort);
                    writer.append("source port  :" + srcPort);
                    System.out.println("source port  :" + srcPort);
                    writer.append("tcp data :" + HexHelper.toString(data));
                    System.out.println("tcp data :" + HexHelper.toString(data));
                    writer.append("tcp data in letters :" +AsciiHelper.toString(data));
                    System.out.println("tcp data in letters :" +AsciiHelper.toString(data));
                    writer.append("tcp time :" + tcpPkt.getTimeval());
                    System.out.println("tcp time :" + tcpPkt.getTimeval());
                    
                     //add row
                    
                    
                    
                }
                
                else{writer.append(proto + "Packet"); 
                    System.out.println(proto + "Packet");
                    
                     //add row
                }
                
                String http="";
                
                if (srcPort == 80 || srcPort == 8080 || dstPort == 80 || dstPort == 8080 ){
                    writer.append("HTTP");    
                    System.out.println("HTTP");
                    http="http";
                    }else if(srcPort == 443 || dstPort == 443 ){
                      writer.append("HTTPS");
                        System.out.println("HTTPS");
                        http="https";
                    }
                
                  //add row
                
              writer.append("******************************************************");
                System.out.println("******************************************************");

            }
    
    
    
  
   
    }}
            catch(Exception io)
            {
                System.err.println(io);
            }
      
   
    i++;
    
    return data;
    }


public byte[] get_pkt_data()
{
    
    return data;
}

public int get_pkt_num()
{
    
    return i;
}

}
