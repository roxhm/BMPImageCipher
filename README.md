# BMP Image Encryption/Decryption with DES

This is a Java desktop application for encrypting and decrypting BMP images using the DES algorithm with various modes of operation. 

Users select an image, specify the mode of operation (ECB, CBC, CFB, OFB), and provide an 8-byte key along with an 8-byte initialization vector.

## Features
Select a `.bmp` image from your system.
- Choose between **encryption** or **decryption**.
- Input an 8-byte key (DES requirement).
- Choose the mode of operation: `ECB`, `CBC`, `CFB`, or `OFB`.
- For non-ECB modes, input an 8-byte initialization vector (IV).
- Process the image and export the result to a new `.bmp` file.

## Output: 

The output file will have the same name as the input file with an appended letter **e/d** depending on whether it was encrypted or decrypted, as well as the selected  mode of operation in CAPITAL LETTERS.

##### Example: 
If we encrypt image `image.bmp` with the CBC mode of operation, encrypted image (output file) will be name like `image_eCBC.bmp`. 

If we decrypt image `image_eCBC.bmp` with the CBC mode of operation, decrypted image (output file) will be named like: `image_eCBC_dCBC.bmp`

## How to Run
1. Make sure you have Java installed (JDK 8+).
2. Compile the project:
``` 
$ javac BMPCipher.java
```
3. Run 
```
$ java BMPCipher
```

## Notes
- The key and IV must be exactly 8 bytes long.
- Only `.bmp` images are supported at this time.
- `ECB` does not require an IV.

## Technologies Used
- Implemented using Java Swing, AWT, and crypto packages.
