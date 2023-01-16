import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class BMPCipher 
{
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

    public static void main(String[] args) throws Exception
    {

        String prueba = "Hola Rox"; 
        String llave = "abcdefgh"; 
        String modo = "ECB"; 
        String vi = "12345678"; 
        byte[] texto_plano = prueba.getBytes("UTF-8");
        byte[] texto_cifrado = cifrar(texto_plano, llave, modo, vi);
        for(int i = 0; i < texto_cifrado.length; i++)
            System.out.println(texto_cifrado[i]);
        
        System.out.printf("\nAqui viene el texto plano\n");
        byte[] texto_plano2 = descifrar(texto_cifrado, llave, modo, vi);
        for(int i = 0; i < texto_plano2.length; i++)
            System.out.printf("%c", texto_plano2[i]);

        /*
        if(args.length < 4)
        {
            System.out.println("Modo de uso: <cifrar/descifrar> <archivo_imagen.bmp> <llave_des> <modo_de_operacion> <vector_inicializacion>");
            System.exit(0);
        }
        String opcion = args[0];
        System.out.println(opcion);
        String archivo_imagen = args[1]; 
        String llave_des = args[2];
        String modo_de_operacion = args[3];
        String vector_inicializacion = args[4]; 

        BufferedImage imagen1 = null;
        try 
        {
            imagen1 = ImageIO.read(new File(archivo_imagen));
        } 
        catch (IOException e) {}

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
        
    
        for(int i = 0; i < rgb_imagen_original.length; i += 3)
            System.out.printf("(%d, %d, %d)\n", Byte.toUnsignedInt(rgb_imagen_original[i]),
                                              Byte.toUnsignedInt(rgb_imagen_original[i+1]),
                                              Byte.toUnsignedInt(rgb_imagen_original[i+2]) 
                                              );
    
        byte[] rgb_imagen_procesada = new byte[rgb_imagen_original.length];
        if(opcion.equals("cifrar"))
        {
            try
            {
                rgb_imagen_procesada = cifrar(rgb_imagen_original, llave_des, modo_de_operacion, vector_inicializacion);
            } catch(Exception e)
            {

            }
        }
        else if(opcion.equals("descifrar"))
        {
            try
            {
                rgb_imagen_procesada = descifrar(rgb_imagen_original, llave_des, modo_de_operacion, vector_inicializacion);
            } catch(Exception e)
            {

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
        if(opcion.equals("cifrar"))
            ruta += nombre_salida + "_e" + modo_de_operacion + ".bmp";
        else if(opcion.equals("descifrar"))
            ruta += nombre_salida + "_d" + modo_de_operacion + ".bmp";

        System.out.println(ruta);
        File output = new File(ruta);
        try
        { 
            ImageIO.write(imagen2, "bmp", output);
        }
        catch (Exception e)
        {

        }
        */
    }
}