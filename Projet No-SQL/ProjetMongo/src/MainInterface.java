
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
public class MainInterface extends JFrame {
	private JPanel contentPane;
	static JTextPane Affichage= new JTextPane();
	public static StyledDocument sDoc = (StyledDocument) Affichage.getDocument();
	public static Style defaut = Affichage.getStyle("default");
	public static Style style1 = Affichage.addStyle("style1", defaut);
	/**
	 * Create the frame.
	 */
	public MainInterface() {
		setResizable(false);
		setTitle("Twitter Like");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Affichage.setEditable(false);
		Affichage.setToolTipText("");
		Affichage.setBounds(6, 6, 503, 458);
		contentPane.add(Affichage);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 472, 458);
//		scrollPane.getViewport().add(Affichage);
		contentPane.add(scrollPane);
		JButton btn_twitter = new JButton("Twitter");
		btn_twitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop1 = new JOptionPane();
				String hash = jop1.showInputDialog(null, "Veuillez entrer le hash :", "Configuration", JOptionPane.QUESTION_MESSAGE);
				JOptionPane jop2 = new JOptionPane();
				String message = jop2.showInputDialog(null, "Veuillez entrer votre message :", "Configuration", JOptionPane.QUESTION_MESSAGE);
				if(hash.length()>10){
					hash = hash.substring(0, 10);
				}
				if(message.length()>140){
					message = message.substring(0, 140);
				}
				MongoDBJDBC.insertTweet(MongoDBJDBC.profil,hash,message);
				try {
					affiche0("Tweet ajouter\n");
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_twitter.setBounds(511, 271, 158, 47);
		contentPane.add(btn_twitter);
		JButton btn_rechercher = new JButton("Rechercher");
		btn_rechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search="";
				String keySearch="";
				JOptionPane jop = new JOptionPane();
				String key = jop.showInputDialog(null, "Veuillez entrer le moyen de recherche \n1) par hashtage\n2) par auteur\n3) par message\n4) Afficher tous les tweets", "Configuration", JOptionPane.QUESTION_MESSAGE);
				switch (key) {
				case "1":
					keySearch="hash";
					JOptionPane jop1 = new JOptionPane();
					search = "#"+jop1.showInputDialog(null, "Veuillez entrer la recherche", "Configuration", JOptionPane.QUESTION_MESSAGE);
					break;
				case "2":
					keySearch="auteur";
					JOptionPane jop2 = new JOptionPane();
					search = jop2.showInputDialog(null, "Veuillez entrer la recherche", "Configuration", JOptionPane.QUESTION_MESSAGE);
					break;
				case "3":
					keySearch="message";
					JOptionPane jop3 = new JOptionPane();
					search = jop3.showInputDialog(null, "Veuillez entrer la recherche", "Configuration", JOptionPane.QUESTION_MESSAGE);
					break;
				case "4":
					search="";
					break;
				default:
					break;
				}
				try {
					MongoDBJDBC.rechercheTweet(keySearch, search);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_rechercher.setBounds(511, 343, 158, 47);
		contentPane.add(btn_rechercher);
		JButton btn_afficher = new JButton("Afficher ses tweets");
		btn_afficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProfilInterface frameProfil = new ProfilInterface();
				frameProfil.setTitle("Liste des tweets de "+MongoDBJDBC.profil);
				frameProfil.setVisible(true);
			}
		});
		btn_afficher.setBounds(511, 417, 158, 47);
		contentPane.add(btn_afficher);
		
		JButton btn_taille = new JButton("Nombre de tweet");
		btn_taille.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MongoDBJDBC.nombreTweet();
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_taille.setBounds(511, 203, 158, 47);
		contentPane.add(btn_taille);
	}
	public static void affiche0(String s) throws BadLocationException{
		System.out.println(s);
		MainInterface.sDoc.insertString(sDoc.getLength(),s,MainInterface.style1);
	}
}