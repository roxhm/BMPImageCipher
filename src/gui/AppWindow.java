// Interfaz Swing

package gui;

import image.BMPHandler;
import crypto.ImageEncryptor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;

public class AppWindow extends JFrame {

    private String nombreArchivo = "";
    private final JComboBox<String> comboOpciones;
    private final JComboBox<String> comboModos;
    private final JTextField campoLlave;
    private final JTextField campoVector;

    public AppWindow() {
        super("Cifrador de imágenes BMP con DES");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new FlowLayout());

        String[] opciones = {"Cifrar", "Descifrar"};
        comboOpciones = new JComboBox<>(opciones);

        String[] modos = {"ECB", "CBC", "CFB", "OFB"};
        comboModos = new JComboBox<>(modos);

        JButton botonArchivo = new JButton("Seleccionar archivo");
        botonArchivo.addActionListener(e -> seleccionarArchivo());

        campoLlave = new JTextField(10);
        campoVector = new JTextField(10);

        JButton botonProcesar = new JButton("Procesar");
        botonProcesar.addActionListener(e -> procesarImagen());

        // Paneles organizadores
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(comboOpciones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(comboModos);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelSeleccion = new JPanel();
        panelSeleccion.add(botonArchivo);
        panel.add(panelSeleccion);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelLlave = new JPanel();
        panelLlave.add(new JLabel("Llave (8 bytes): "));
        panelLlave.add(campoLlave);
        panel.add(panelLlave);

        JPanel panelVector = new JPanel();
        panelVector.add(new JLabel("Vector de Inicialización (8 bytes): "));
        panelVector.add(campoVector);
        panel.add(panelVector);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panelProcesar = new JPanel();
        panelProcesar.add(botonProcesar);
        panel.add(panelProcesar);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void seleccionarArchivo() {
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int resultado = chooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            nombreArchivo = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("Archivo seleccionado: " + nombreArchivo);
        }
    }

    private void procesarImagen() {
        if (nombreArchivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una imagen BMP primero.");
            return;
        }

        String opcion = (String) comboOpciones.getSelectedItem();
        String modo = (String) comboModos.getSelectedItem();
        String llave = campoLlave.getText();
        String vector = campoVector.getText();

        try {
            byte[] datosRGB = BMPHandler.leerImagen(nombreArchivo);
            byte[] datosProcesados;

            if (opcion.equals("Cifrar")) {
                datosProcesados = ImageEncryptor.cifrar(datosRGB, llave, modo, vector);
            } else {
                datosProcesados = ImageEncryptor.descifrar(datosRGB, llave, modo, vector);
            }

            BMPHandler.guardarImagen(nombreArchivo, datosProcesados, modo, opcion);
            JOptionPane.showMessageDialog(this, "¡Imagen procesada con éxito!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
