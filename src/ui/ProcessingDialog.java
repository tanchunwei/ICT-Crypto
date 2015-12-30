/* Project Title:	IT3119 Information Security Case Study
 * Project Group:	05
 * Author:			Tan Chun Wei
 */

package ui;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class ProcessingDialog extends JDialog implements Runnable {
    
    private Thread t;
    private Runnable r;
    private JLabel l;
    private boolean done;
    
    public void setDone(boolean b){
    	done = b;
    }
    
    public ProcessingDialog( Frame owner, String title, boolean modal, Runnable r ) {
        super( owner, title, modal );
        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        this.r = r;
        l = new JLabel( "Starting.... /" );
        JPanel p = new JPanel();
        p.add( l );
        getContentPane().add( p );
        
        setSize( 100, 100 );
        setLocationRelativeTo(null);
        t = new Thread( this );
        done = false;
        t.start();
        
        setVisible( true );
    }
    
    public void run() {
        Thread processing = new Thread( new Runnable() {
            public void run() {
                Thread t = new Thread( r );
                t.start();                
                /*try {
                    t.join();
                }
                catch ( InterruptedException x ) {
                    x.printStackTrace();
                }*/
                done = true;
            }
        } );
        processing.start();
        while ( !done ) {
            String text = l.getText();
            char c = text.charAt( text.length() - 1 );
            char newC = 0;
            switch ( c ) {
                case '/' :
                    newC = '-';
                    break;
                case '-' :
                    newC = '\\';
                    break;
                case '\\':
                    newC = '|';
                    break;
                default:
                    newC = '/';
                    break;
            }
            text = text.replace( c, newC );
            l.setText( text );
/*            try {
                Thread.sleep( 100 );
            }
            catch ( InterruptedException x ) {
                x.printStackTrace();
            }*/
        }
        
        l.setText( "Startup... done!" );
        /*try {
            Thread.sleep( 2000 );
        }
        catch ( InterruptedException x ) {
            x.printStackTrace();
        }*/
        dispose();
    }
    
    public static void main(String[] args) {
        final JFrame f = new JFrame( "Processing Dialog" );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        JPanel p = new JPanel();
        JButton b = new JButton( "Start" );
        b.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                Runnable r = new Runnable(){ 
                    public void run() {
                        try {
                            Thread.sleep( 10 );
                        }
                        catch ( InterruptedException x ) {
                            x.printStackTrace();
                        }
                    }
                };
                
                new ProcessingDialog( f, "Processing", true, r );
                
            }
        } );
        p.add( b );
        f.getContentPane().add( p );
        f.setSize( 300, 300 );
        f.setVisible( true );
    }
}