import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Toolkit.*;
import javax.swing.event.*;
import java.net.*;
 
public class Browser extends JFrame implements HyperlinkListener,ActionListener
{
public static void main(String[] args)
{
if(args.length==0)
new Browser("http://google.com");
else
new Browser(args[0]);
}
private JTextField urlfield;
private JEditorPane htmlpane;
private String initialurl;
public Browser(String initialurl)
{
super("Simple Swing Browser");
this.initialurl=initialurl;
setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
JPanel topanel=new JPanel();
topanel.setBackground(Color.gray);
JLabel urllabel=new JLabel("URL:");
urlfield=new JTextField(30);
urlfield.setText(initialurl);
urlfield.addActionListener(this);
topanel.add(urllabel);
topanel.add(urlfield);
getContentPane().add(topanel,BorderLayout.NORTH);
try
{
htmlpane=new JEditorPane(initialurl);
htmlpane.setEditable(false);
htmlpane.addHyperlinkListener(this);
JScrollPane scrollpane=new JScrollPane(htmlpane);
getContentPane().add(scrollpane,BorderLayout.CENTER);
}
catch(IOException ioe)
{
warnUser("can't build HTML page for "+ initialurl+":"+ioe);
}
Toolkit t=getToolkit();
Dimension ScreenSize=t.getScreenSize();
int width=ScreenSize.width*8/10;
int height=ScreenSize.height*8/10;
setBounds(width/8,height/8,width,height);
setVisible(true);
}
public void actionPerformed(ActionEvent e)
{
String url;
if(e.getSource()==urlfield)
url=urlfield.getText();
else
url=initialurl;
try
{
htmlpane.setPage(new URL(url));
urlfield.setText(url);
}
catch(IOException ioe)
{
warnUser("Can't follow link to"+url+":"+ioe);
}
}
public void hyperlinkUpdate(HyperlinkEvent h)
{
if(h.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
{
try{
htmlpane.setPage(h.getURL());
urlfield.setText(h.getURL().toExternalForm());
}
catch(IOException ioe)
{
warnUser("can't follow link to"+h.getURL().toExternalForm()+":"+ioe);
}
}
}
public void warnUser(String message)
{
JOptionPane.showMessageDialog(this,message,"ERROR",JOptionPane.ERROR_MESSAGE);
}
}
