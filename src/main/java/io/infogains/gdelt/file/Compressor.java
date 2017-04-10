package io.infogains.gdelt.file;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by omart_000 on 3/23/2017.
 */
public class Compressor {

    private static final int BUFFER = 2048;
    private Path dir;
    private String glob;

    Compressor(String filePath, String glob) {
        this.dir = Paths.get(filePath);
        this.glob = glob;
    }

    Compressor() {}

    public void compress() throws IOException {

        // Archive File
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob)) {
            for(Path entry : stream) {
                String fileName = entry.toAbsolutePath().toString();
                compress(new File(fileName));
//                try(BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(fileName));
//                    FileOutputStream dest = new FileOutputStream(fileName + ".gz");
//                    GZIPOutputStream gzOut = new GZIPOutputStream(dest)) {
//
//                    byte[] data = new byte[BUFFER];
//
//                    System.out.println(fileName);
//
//                    int count;
//                    while((count = buffIn.read(data, 0, BUFFER)) != -1) {
//                        gzOut.write(data, 0, count);
//                    }
//                }
            }
        }
    }

    public void compress(File file) throws IOException {

        // Archive File
//        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob)) {
//            for(Path entry : stream) {
                String fileName = file.getName();
                try(BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(file));
                    FileOutputStream dest = new FileOutputStream(fileName + ".gz");
                    GZIPOutputStream gzOut = new GZIPOutputStream(dest)) {

                    byte[] data = new byte[BUFFER];

                    System.out.println(fileName);

                    int count;
                    while((count = buffIn.read(data, 0, BUFFER)) != -1) {
                        gzOut.write(data, 0, count);
                    }
                }
//            }
//        }
    }

    public static void main(String[] args) {
        Compressor compressor = new Compressor("./output", "*.{json}");

        try {
            compressor.compress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
