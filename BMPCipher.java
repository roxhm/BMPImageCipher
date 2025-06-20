import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;

public class BMPCipher 
{
    public static void procesar_imagen(String opcion, String archivo_imagen, String llave_des, String modo_de_operacion, String vector_inicializacion)
    {
        BufferedImage imagen1 = null;
        try 
        {
            imagen1 = ImageIO.read(new File(archivo_imagen));
        } 
        catch (IOException e) 
        {
            System.out.printf("ERROR: No se pudo abrir la imagen.\n%s\n", e.getMessage());
            System.exit(1);
        }

        int height = imagen1.getHeight();
        int width = imagen1.getWidth();

        System.out.println("Ancho: " + width); 
        System.out.println("Alto: " + height);
        byte[] rgb_imagen_original = new byte[width * height * 3]; 

        int indice = 0; 
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
            {
                Color c = new Color(imagen1.getRGB(j, i));
                byte r = (byte) c.getRed();
                byte g = (byte) c.getGreen();
                byte b = (byte) c.getBlue();

                rgb_imagen_original[indice + 0] = r;
                rgb_imagen_original[indice + 1] = g; 
                rgb_imagen_original[indice + 2] = b; 

                indice += 3;
            }
        
        byte[] rgb_imagen_procesada = new byte[rgb_imagen_original.length];
        if(opcion.equals("Cifrar"))
        {
            try
            {
                rgb_imagen_procesada = cifrar(rgb_imagen_original, llave_des, modo_de_operacion, vector_inicializacion);
            } catch(Exception e)
            {
                System.out.printf("ERROR: No se pudo cifrar.\n%s\n", e.getMessage());
                System.exit(1);
            }
        }
        else if(opcion.equals("Descifrar"))
        {
            try
            {
                rgb_imagen_procesada = descifrar(rgb_imagen_original, llave_des, modo_de_operacion, vector_inicializacion);
            } catch(Exception e)
            {
                System.out.printf("ERROR: No se pudo descifrar.\n%s\n", e.getMessage());
                System.exit(1);
            }

        }

        BufferedImage imagen2 = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        indice = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
            {
                int r = Byte.toUnsignedInt(rgb_imagen_procesada[indice + 0]);
                int g = Byte.toUnsignedInt(rgb_imagen_procesada[indice + 1]);
                int b = Byte.toUnsignedInt(rgb_imagen_procesada[indice + 2]);
                indice += 3;

                Color nuevo_color = new Color(r, g, b);
                imagen2.setRGB(j, i, nuevo_color.getRGB());
            }
        
        String nombre_salida = archivo_imagen.substring(0, archivo_imagen.lastIndexOf('.'));
        System.out.println(nombre_salida);
        
        String ruta = "";
        if(opcion.equals("Cifrar"))
            ruta += nombre_salida + "_e" + modo_de_operacion + ".bmp";
        else if(opcion.equals("Descifrar"))
            ruta += nombre_salida + "_d" + modo_de_operacion + ".bmp";

        System.out.println(ruta);
        File output = new File(ruta);
        try
        { 
            ImageIO.write(imagen2, "bmp", output);
        }
        catch (Exception e)
        {
            System.out.printf("ERROR: No se pudo escribir la imagenn\n%s\n", e.getMessage());
            System.exit(1);
        }

    }

    public static byte[] cifrar(byte[] texto_plano, String llave_des, String modo_de_operacion, String vector_inicializacion) throws Exception
    {
        byte[] vi = vector_inicializacion.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES/" + modo_de_operacion + "/NoPadding");
        SecretKeySpec key = new SecretKeySpec(llave_des.getBytes("UTF-8"), "DES");

        if(modo_de_operacion.equals("ECB"))
        {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        else
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(vector_inicializacion.getBytes("UTF-8")));
        return cipher.doFinal(texto_plano);
    }

    public static byte[] descifrar(byte[] texto_cifrado, String llave_des, String modo_de_operacion, String vector_inicializacion) throws Exception
    {
        byte[] vi = vector_inicializacion.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES/" + modo_de_operacion + "/NoPadding");
        SecretKeySpec key = new SecretKeySpec(llave_des.getBytes("UTF-8"), "DES");

        if(modo_de_operacion.equals("ECB"))
        {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        else
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(vector_inicializacion.getBytes("UTF-8")));
        return cipher.doFinal(texto_cifrado);
    }

    static String nombre_archivo; 
    
    public static void main(String[] args) throws Exception
    {
        JFrame ventana = new JFrame("Cifrador de imagenes BMP con DES");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());
        ventana.setSize(600, 400); 
        String opciones[] = {"Cifrar", "Descifrar"};
        JComboBox<String> combobox_opciones = new JComboBox<>(opciones); 
        String modos_de_operacion[] = { "ECB", "CBC", "CFB", "OFB"};
        JComboBox<String> combobox_modos_de_operacion = new JComboBox<>(modos_de_operacion);
        JButton seleccionar_archivo = new JButton("Seleccionar archivo");

        seleccionar_archivo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Se presiono el boton");
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int r = j.showSaveDialog(null);
                if (r == JFileChooser.APPROVE_OPTION)
                {
                    nombre_archivo = j.getSelectedFile().getAbsolutePath();
                    System.out.println(nombre_archivo);
                }
            }
        });
        JPanel panel_llave = new JPanel();
        JLabel label_llave = new JLabel(); 
        label_llave.setText("Llave (DES= 8 bytes) : ");
        panel_llave.add(label_llave); 
        // panel_llave.add(Box.createRigidArea(new Dimension(120, 0)));
        JTextField campo_llave = new JTextField(10);
        panel_llave.add(campo_llave); 

        JPanel panel_vector = new JPanel(); 
        JLabel label_vector = new JLabel(); 
        label_vector.setText("Vector de Inicialización (C0) (DES=8 bytes) : ");
        panel_vector.add(label_vector);
        JTextField campo_vector_de_inicializacion = new JTextField(10);
        panel_vector.add(campo_vector_de_inicializacion); 

        JButton procesar = new JButton("Procesar");

        procesar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                procesar_imagen((String) combobox_opciones.getSelectedItem(), nombre_archivo, campo_llave.getText(), (String) combobox_modos_de_operacion.getSelectedItem(), campo_vector_de_inicializacion.getText());
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(combobox_opciones);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(combobox_modos_de_operacion);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panel_seleccionador = new JPanel(); 
        panel_seleccionador.add(seleccionar_archivo); 
        panel.add(panel_seleccionador);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(panel_llave);
        panel.add(panel_vector);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panel_procesador = new JPanel(); 
        panel_procesador.add(procesar); 
        panel.add(panel_procesador);
        ventana.add(panel);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}