package Test;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import LinienDiagramm.GraphGUI;
import Steuerung.Steuerung;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class MainGUI extends JFrame {

    // Assoziation
    Steuerung dieSteuerung;
    GraphGUI dieGgui;

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

    // Arrays for the equation
    ArrayList<Double> ListeEin = new ArrayList<Double>(Arrays.asList());
    ArrayList<Double> ListeAusg = new ArrayList<Double>(Arrays.asList());

    DefaultListModel<String> model1 = new DefaultListModel<>();
    DefaultListModel<String> model2 = new DefaultListModel<>();
    private JButton btnNewButton;

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
      dieGgui = new GraphGUI(); // Initialisierung des Graphen


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

        // JListen
        AnzeigeEin = new JList<>(model1);
        AnzeigeEin.setBounds(318, 62, 148, 165);
        model1.addElement("Einkommen:");
        panelEingabe.add(AnzeigeEin);

        AnzeigeAusg = new JList<>(model2);
        AnzeigeAusg.setBounds(318, 225, 148, 210);
        model2.addElement("Ausgaben:");
        panelEingabe.add(AnzeigeAusg);

        JPanel panelButtons = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panelButtons.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        contentPane.add(panelButtons, BorderLayout.SOUTH);

        /**
         * Wird der Button add gedrückt, werden die Werte in TFs in Arrays gespeichert
         */
        // Button Add & Zeit
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!tfEinkommen.getText().isBlank()) {
                    model1.addElement(tfEinkommen.getText());
                    String strEinkommen = tfEinkommen.getText();
                    double Einkommen = Double.parseDouble(strEinkommen);
                    ListeEin.add(Einkommen);
                    tfEinkommen.setText(null);
                }
                if (!tfAusgaben.getText().isBlank()) {
                    model2.addElement(tfAusgaben.getText());
                    String strAusgaben = tfAusgaben.getText();
                    double Ausgaben = Double.parseDouble(strAusgaben);
                    ListeAusg.add(Ausgaben);
                    tfAusgaben.setText(null);
                }
            }
        });

        /**
         * Wird der btn Zeit gedrückt, so wird ein Zeit vergehen simuliert
         */
        btnZeit = new JButton("Zeit");
        btnZeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthPassed();
                for (int i = 0; i < 4; i++) {
                    weekPassed();
                }
                for (int i = 0; i < 30; i++) {
                    dayPassed();
                }
            }
        });
        
        btnNewButton = new JButton("Eingabe löschen");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        panelButtons.add(btnNewButton);

        panelButtons.add(btnAdd);
        panelButtons.add(btnZeit);

        /**
         * Stellt den gespeicherten Zustand aus Dateien wieder her.
         */
        restoreState();

        /**
         * Fügt einen WindowListener hinzu, um den Zustand beim Schließen des Fensters zu speichern.
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveState();
            }
        });
    }

    /**
     * Ein Monat ist vergangen
     * 
     * Alle E/A werden zu eine Summer gezählt und mit Saldo verrechnet
     * 
     * Dieser Saldo wird daraufhin zum neuen Kontobetrag
     */
    protected void monthPassed() {
        System.out.println("Zeit vergeht");

        // Einkommen Array als Gesamtbetragt
        double REinkommen = ListeEin.stream().mapToDouble(Double::doubleValue).sum();

        // Ausgaben Array als Gesamtbetrag
        double RAusgaben = ListeAusg.stream().mapToDouble(Double::doubleValue).sum();

        // Konto auslesen
        String strKontoinhalt = tfKonto.getText();

        // Konto Text in Zahl konvertieren
        double Konto = Double.parseDouble(strKontoinhalt);

        // Berechnung der Summe
        double summe = dieSteuerung.berechneSumme(REinkommen, RAusgaben, Konto);
        // derGraph = new ZeichneGraph();

        // Summe anzeigen
        tfKonto.setText("" + summe);

        // Save summe to file
        saveSumme(summe);
        // Aktualisiere das Diagramm
        dieGgui.updateGraph(summe);
        
    }

    protected void dayPassed() {
        System.out.println("HALLO :D");
    }

    protected void weekPassed() {
    	System.out.println("Trauma");
    }

    /**
     * Speichert den Saldo in eine Datei.
     * 
     * @param summe Der zu speichernde Saldo.
     */
    private void saveSumme(double summe) {
        try (PrintWriter out = new PrintWriter(new FileWriter("Saldo.txt"))) {
            out.println(summe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt den gespeicherten Saldo aus der Datei und setzt das Textfeld tfKonto.
     */
    private void loadSumme() {
        try (BufferedReader br = new BufferedReader(new FileReader("Saldo.txt"))) {
            String line = br.readLine();
            if (line != null) {
                tfKonto.setText(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Speichert die Liste in eine Datei.
     * 
     * @param filename Der Name der Datei.
     * @param list     Die zu speichernde Liste.
     */
    private void saveList(String filename, List<Double> list) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Double d : list) {
                out.println(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lädt eine Liste aus einer Datei.
     */
    private List<Double> loadList(String filename) {
        List<Double> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Double.parseDouble(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Stellt den Zustand der Anwendung aus Dateien wieder her und aktualisiert die Listenfelder.
     */
    private void restoreState() {
        loadSumme();
        ListeEin = new ArrayList<>(loadList("Einkommen.txt"));
        ListeAusg = new ArrayList<>(loadList("Ausgaben.txt"));
        updateListModels();
    }

    /**
     * Speichert den aktuellen Zustand der Anwendung in Dateien.
     */
    private void saveState() {
        saveSumme(Double.parseDouble(tfKonto.getText()));
        saveList("Einkommen.txt", ListeEin);
        saveList("Ausgaben.txt", ListeAusg);
    }

    /**
     * Aktualisiert die ListModels für AnzeigeEin und AnzeigeAusg.
     */
    private void updateListModels() {
        model1.clear();
        model1.addElement("Einkommen:");
        for (Double d : ListeEin) {
            model1.addElement(d.toString());
        }
        
        model2.clear();
        model2.addElement("Ausgaben:");
        for (Double d : ListeAusg) {
            model2.addElement(d.toString());
        }
    }}