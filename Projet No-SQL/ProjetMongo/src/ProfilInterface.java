
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;


public class ProfilInterface extends JFrame {

	private JPanel contentPane;
	static JTextPane Affichage= new JTextPane();
	public static StyledDocument sDoc = (StyledDocument) Affichage.getDocument();
	public static Style defaut = Affichage.getStyle("default");
	public static Style style1 = Affichage.addStyle("style1", defaut);

	/**
	 * Create the frame.
	 */
	public ProfilInterface() {
		setResizable(false);
		setTitle("Profile de ");
		setBounds(100, 100, 592, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Affichage.setEditable(false);
		Affichage.setToolTipText("");
		Affichage.setBounds(6, 6, 472, 458);
		contentPane.add(Affichage);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 472, 458);
//		scrollPane.getViewport().add(Affichage);
		contentPane.add(scrollPane);
		
		JButton btn_actualiser = new JButton("Actualiser");
		btn_actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Affichage.setText("");
				try {
					MongoDBJDBC.TweetAuteur();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_actualiser.setBounds(481, 6, 111, 47);
		contentPane.add(btn_actualiser);
	}
	public static void affiche1(String s) throws BadLocationException{
		System.out.println(s);
		ProfilInterface.sDoc.insertString(sDoc.getLength(),s,ProfilInterface.style1);
	}
}