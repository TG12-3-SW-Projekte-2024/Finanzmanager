package Test;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class MainGUI extends JFrame {
	
	
	//Assoziation
	Steuerung  dieSteuerung;
	ZeichneGraph derGraph;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfEinkommen;
	private JTextField tfAusgaben;
	private JTextField tfKonto;
	JButton btnZeit; 
	JLabel lbStatus;
	JButton btnAdd;
	JList<String> AnzeigeEin;
	JList<String> AnzeigeAusg;
	
	//Arrays for the equation
	ArrayList<Double> ListeEin = new ArrayList<Double>(Arrays.asList());
	ArrayList<Double> ListeAusg = new ArrayList<Double>(Arrays.asList());
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainGUI() {
		
		dieSteuerung = new Steuerung(this);
		//derGraph = new ZeichneGraphen(this);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelEingabe = new JPanel();
		contentPane.add(panelEingabe);
		panelEingabe.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Einkommen");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(79, 133, 91, 28);
		panelEingabe.add(lblNewLabel);
		
		tfEinkommen = new JTextField();
		tfEinkommen.setBounds(10, 65, 214, 65);
		panelEingabe.add(tfEinkommen);
		tfEinkommen.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ausgaben");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(79, 268, 91, 22);
		panelEingabe.add(lblNewLabel_1);
		
		tfAusgaben = new JTextField();
		tfAusgaben.setBounds(10, 201, 214, 65);
		panelEingabe.add(tfAusgaben);
		tfAusgaben.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Saldo");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(79, 407, 91, 28);
		panelEingabe.add(lblNewLabel_2);
		
		tfKonto = new JTextField();
		tfKonto.setBounds(10, 341, 214, 65);
		panelEingabe.add(tfKonto);
		tfKonto.setColumns(10);
		
		
		
		//JListen
		DefaultListModel<String> model1 = new DefaultListModel<>();
		JList<String> AnzeigeEin = new JList<>(model1);
		AnzeigeEin.setBounds(318, 62, 148, 165);
		model1.addElement("Einkommen:");
		panelEingabe.add(AnzeigeEin);
		
		DefaultListModel<String> model2 = new DefaultListModel<>();
		JList<String> AnzeigeAusg = new JList<>(model2);
		AnzeigeAusg.setBounds(318, 225, 148, 210);
		model2.addElement("Ausgaben:");
		panelEingabe.add(AnzeigeAusg);
		
		JPanel panelButtons = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panelButtons, BorderLayout.SOUTH);
		
		
		
		//Button Add & Zeit
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() 
		{
		public void actionPerformed(ActionEvent e) 
		{
			if(!tfEinkommen.getText().isBlank()) 
			{
				model1.addElement(tfEinkommen.getText());
				String strEinkommen = tfEinkommen.getText();
				double Einkommen = Double.parseDouble(strEinkommen);
				ListeEin.add(Einkommen);
				tfEinkommen.setText(null);
			}
			if(!tfAusgaben.getText().isBlank()) 
			{
				model2.addElement(tfAusgaben.getText());
				String strAusgaben = tfAusgaben.getText();
				double Ausgaben = Double.parseDouble(strAusgaben);
				ListeAusg.add(Ausgaben);
				tfAusgaben.setText(null);
			}
				
		}
		});
		
		btnZeit = new JButton("Zeit");
		btnZeit.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				monthPassed();	
				//derGraph = new ZeichneGraph();
				}
			}
		);

			
		panelButtons.add(btnAdd);
		panelButtons.add(btnZeit);
		
	}




	protected void monthPassed() 
	{
		System.out.println("Zeit vergeht");
		
		//Einkommen Array als Gesamtbetragt	
		double REinkommen = ListeEin.stream().mapToDouble(Double::doubleValue).sum();
		
		//Ausgaben Array als Gesamtbetrag
		double RAusgaben = ListeAusg.stream().mapToDouble(Double::doubleValue).sum();
		
		//Konto auslesen
		String strKontoinhalt = tfKonto.getText();
		
		//Konto Text in Zahl konvertieren
		double Konto = Double.parseDouble(strKontoinhalt);
		
		//Berechnung der Summe
		double summe = dieSteuerung.berechneSumme(REinkommen, RAusgaben, Konto);
		//derGraph = new ZeichneGraph();
		
		//Summe anzeigen
		tfKonto.setText(""+summe);
		
	}
	
	protected void dayPassed() {
		System.out.println("HALLO :D");
	}
	
	protected void weekPassed() {
		
	}
}
