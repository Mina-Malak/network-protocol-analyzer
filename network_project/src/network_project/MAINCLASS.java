package network_project;


/**
 *
 * @author Mina Malak
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;
import net.sourceforge.jpcap.util.AsciiHelper;
import net.sourceforge.jpcap.util.HexHelper;




public class MAINCLASS 
{
    
    static  NewJFrame  gui=new NewJFrame();
  private static final int INFINITE = 1;
  private static final int PACKET_COUNT = INFINITE; 
  boolean flag1=true,flag=false;
  String [] Interfaces=lookupDevices();
  PacketCapture pcap = new PacketCapture();
  int pkt_num;
  byte[] pkt_data;
  String [] pkt_info=new String[10000];
  String [] pkt_hex=new String[10000];
  
  
 
  public MAINCLASS() throws Exception {
    
      
  
    gui.setVisible(true);
    System.out.println("Available interfaces: ");
   
    JComboBox<String> inter_combo=gui.ret_box();
    inter_combo.removeAllItems();
 for(int x=0; x<Interfaces.length; x++) 
    {
        System.out.println(x+" -> "+Interfaces[x]);
        inter_combo.addItem(Interfaces[x]);
    }
 //int choice = Integer.parseInt(getInput("Choose interface (0,1..): "));
      
      
    
      JButton start = gui.ret_start();
      JButton stop = gui.ret_stop();
      JTextArea info_pkt=gui.ret_list1();
      JTextArea hex_pkt=gui.ret_list2();
      JTable table=gui.ret_tab();
     DefaultTableModel model = (DefaultTableModel) table.getModel();
    
 
    //String device = Interfaces[choice];
    String device = (String)inter_combo.getSelectedItem();
    pcap.open(device,65535 ,false,20);
    pcap.setFilter("", true);
    PacketHandler handler=new PacketHandler();
    pcap.addPacketListener(handler);
    HexHelper hex=new HexHelper();
    AsciiHelper as=new AsciiHelper();
    
    while(flag1)
    { 
        start.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            flag=true;
            
            stop.enable();
            start.disable();//To change body of generated methods, choose Tools | Templates.
        }
    });
    stop.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            flag=false;
            
            start.enable();
            stop.disable();//To change body of generated methods, choose Tools | Templates.
        }
    });
        
    
    while(flag)
    {        // Start capturing packets...
    pcap.capture(PACKET_COUNT);
    pkt_num = P_Type.i;
    
//        System.out.println(AsciiHelper.toString(PacketHandler.p_data));
    if(PacketHandler.p_data != null)
    {   
   pkt_data=PacketHandler.p_data;
    }
    
    if( (pkt_data!=null) )
   { pkt_info[pkt_num-1]=AsciiHelper.toString(pkt_data);
    pkt_hex[pkt_num-1]=HexHelper.toString(pkt_data);
   }
    int row=table.getSelectedRow();
    
    if(row>0)
    { 
    int row2=(int) model.getValueAt(row, 0);
    info_pkt.setText(pkt_info[row2]);
    hex_pkt.setText(pkt_hex[row2]);
    }
    }
    int row=table.getSelectedRow();
    
    
    if(row>0)
    { 
    int row2=(int) model.getValueAt(row, 0);
    info_pkt.setText(pkt_info[row2]);
    hex_pkt.setText(pkt_hex[row2]);
    }
        
    
    }
    
    
    
  }

  
  
  public static String getInput(String q)
{ String input = "";
 System.out.print(q);
 BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
 try { input = bufferedreader.readLine(); }
 catch(IOException ioexception) 
 {
     System.out.println(ioexception);
 } 
 return input; 

}
  
  public static String[] lookupDevices() throws CaptureDeviceLookupException {
    String [] deviceList; 
    ArrayList<String> jDevsList = new ArrayList<String>(); 
    try { 
      Enumeration e = NetworkInterface.getNetworkInterfaces(); 
      while( e.hasMoreElements()) {
        NetworkInterface ni = (NetworkInterface)e.nextElement();
        jDevsList.add(ni.getName());  
        System.err.println(ni.getName());
      }
    } 
    catch (Exception e) {
    } 
    deviceList = jDevsList.toArray(new String[jDevsList.size()]); 
    return deviceList;
  }
  
  
  
  public static void main(String[] args)throws Exception {
      
    try {
      MAINCLASS MAIN = new MAINCLASS();
      
    } catch(Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}


class PacketHandler implements PacketListener 
{
    
    NewJFrame gui=MAINCLASS.gui;
  public static byte[] p_data;
   

    
  public void packetArrived(Packet packet) {
    try {
        
        
        
        P_Type packet_type;
        packet_type = new P_Type(gui);
        
        p_data =packet_type.receivePacket(packet);
   
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }
}

