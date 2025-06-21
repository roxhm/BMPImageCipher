// Carga, extrae y reconstruye imágenes

package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class BMPHandler {

    public static byte[] leerImagen(String rutaArchivo) throws Exception {
        BufferedImage imagen = ImageIO.read(new File(rutaArchivo));
        if(imagen == null) {
            throw new Exception("ERROR: No se pudo leer la imagen BMP.");
        }

        int height = imagen.getHeight();
        int width = imagen.getWidth();

        byte[] datosRGB = new byte[width * height * 3];

        int indice = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                Color c = new Color(imagen.getRGB(j, i));
                byte r = (byte) c.getRed();
                byte g = (byte) c.getGreen();
                byte b = (byte) c.getBlue();

                datosRGB[indice + 0] = r;
                datosRGB[indice + 1] = g;
                datosRGB[indice + 2] = b;

                indice += 3;
            }

        return datosRGB;
    }

    public static void guardarImagen(String rutaOriginal, byte[] datosRGB, String modo, String operacion) throws Exception {
        BufferedImage imagenOriginal = ImageIO.read(new File(rutaOriginal));

        int height = imagenOriginal.getHeight();
        int width = imagenOriginal.getWidth();

        BufferedImage imagenProcesada = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        int indice = 0;
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                int r = Byte.toUnsignedInt(datosRGB[indice + 0]);
                int g = Byte.toUnsignedInt(datosRGB[indice + 1]);
                int b = Byte.toUnsignedInt(datosRGB[indice + 2]);
                indice += 3;

                Color nuevo_color = new Color(r, g, b);
                imagenProcesada.setRGB(j, i, nuevo_color.getRGB());
            }

        String nombreBase = rutaOriginal.substring(0, rutaOriginal.lastIndexOf('.'));
        String sufijo = operacion.equals("Cifrar") ? "_e" : "_d";
        String rutaSalida = nombreBase + sufijo + modo + ".bmp";

        File archivoSalida = new File(rutaSalida);
        ImageIO.write(imagenProcesada, "bmp", archivoSalida);
        }
}
