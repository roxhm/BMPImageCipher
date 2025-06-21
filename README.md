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
Make sure you have Java installed (JDK 8+).
### Linux 
1. Make bin directory 
``` 
$ mkdir -p bin
```
2. Compile all Java files
``` 
$ javac -d bin $(find src -name "*.java")
```
3. Run
``` 
$ java -cp bin Main
```
### Windows 
1. Make bin directory 
``` 
mkdir bin
```
2. Compile all Java files
```
javac -d bin src\Main.java src\gui\AppWindow.java src\crypto\ImageEncryptor.java src\image\BMPHandler.java
```
3. Run
```
java -cp bin Main
```

## Notes
- The key and IV must be exactly 8 bytes long.
- Only `.bmp` images are supported at this time.
- `ECB` does not require an IV.

## Technologies Used
- Implemented using Java Swing, AWT, and crypto packages.
