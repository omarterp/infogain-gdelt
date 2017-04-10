package io.infogains.gdelt.file;

import com.google.gson.Gson;
import io.infogains.gdelt.aws.es.ElasticSearchService;
import io.infogains.gdelt.aws.s3.S3Service;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by omart_000 on 3/3/2017.
 *
 * Process GDELT Files
 */
public class Processor {

    private static final int BATCH_SIZE = 1000;
    private static boolean pushToES = true;

    /**
     * Return list of Path objects which will have a handle on the files to be processed
     * @param dir
     * @return A list of Path objects containing all of the paths for the set of files to be processed
     * @throws IOException
     */
    public static List<Path> listSourceFiles(Path dir) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.zip")) {
            for (Path entry: stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encountered during the iteration
            throw ex.getCause();
        }
        return result;
    }

    /**
     * Extracts contents of zip file and process GDELT data
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    public static void readZipFile(Path file) throws IOException, ParseException {
        ZipFile zipFile = new ZipFile(file.toFile());
        Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Gson gson = new Gson();
        StringBuffer esBatch = new StringBuffer();


        // Index marking end index, which removes .zip from the file name
        int endIndex = zipFile.getName().length() - 4;
        String outFile = zipFile.getName().substring(0, endIndex) + ".json";

        System.out.println(outFile);

        try(RandomAccessFile raf = new RandomAccessFile(outFile, "rw")) {

        // GDELT files are tab delimited; split and parse tokens and populate pojo
        while (zipEntries.hasMoreElements()) {
            ZipEntry zipEntry = zipEntries.nextElement();

            try (BufferedReader buf = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipEntry)))) {
                String line;
                int lineCounter = 0;

                    while ((line = buf.readLine()) != null) {
                        VersionOne v1 = new VersionOne();
                        String[] tokens = line.split("\t");
                        //System.out.println(line);
                        //System.out.println(tokens.length);

                        // Check tokens for null and improper length
                        if(tokens == null || tokens.length < 57)
                            continue;

                        // Increment lineCounter
                        lineCounter++;

                        v1.setGeid(tokens[0]);

                        // ignore date format exception - this will be used to add @timestamp for elastic search analytics
                        try {
                            v1.setEDt(sdf.parse(tokens[1]));
                        } catch(ParseException pe) {
                            //TODO remove print for do nothing - key will not be added
                            pe.printStackTrace();
                        }

                        if(tokens[2].length() > 0)
                            v1.setEMthYr(Integer.parseInt(tokens[2]));
//                        if(tokens[3].length() > 0)
//                            v1.setYear(Integer.parseInt(tokens[3]));
                        if(tokens[4].length() > 0)
                            v1.setFractionDate(Double.parseDouble((tokens[4])));

                        v1.setA1Code(tokens[5]);
                        v1.setA1Name(tokens[6]);
                        v1.setA1Country(tokens[7]);
                        v1.setA1Group(tokens[8]);
                        v1.setA1Ethnicity(tokens[9]);
                        v1.setA1Religion1(tokens[10]);
                        v1.setA1Religion2(tokens[11]);
                        v1.setA1Type1(tokens[12]);
                        v1.setA1Type2(tokens[13]);
                        v1.setA1Type3(tokens[14]);
                        v1.setA2Code(tokens[15]);
                        v1.setA2Name(tokens[16]);
                        v1.setA2Country(tokens[17]);
                        v1.setA2Group(tokens[18]);
                        v1.setA2Ethnicity(tokens[19]);
                        v1.setA2Religion1(tokens[20]);
                        v1.setA2Religion2(tokens[21]);
                        v1.setA2Type1(tokens[22]);
                        v1.setA2Type2(tokens[23]);
                        v1.setA2Type3(tokens[24]);
                        v1.setIsRootEvent(Integer.parseInt(tokens[25]));
                        v1.setEventCode(tokens[26]);
                        v1.setEventBaseCode(tokens[27]);
                        v1.setEventRootCode(tokens[28]);

                        if (tokens[29].length() > 0)
                            v1.setQuadClass(Integer.parseInt(tokens[29]));

                        if (tokens[30].length() > 0)
                            v1.setGoldsteinScale(Double.parseDouble(tokens[30]));
                        if (tokens[31].length() > 0)
                            v1.setNumMentions(Double.parseDouble(tokens[31]));
                        if (tokens[32].length() > 0)
                            v1.setNumSources(Double.parseDouble(tokens[32]));
                        if (tokens[33].length() > 0)
                            v1.setNumArticles(Double.parseDouble(tokens[33]));
                        if (tokens[34].length() > 0)
                            v1.setAvgTone(Double.parseDouble(tokens[34]));

                        if (tokens[35].length() > 0)
                            v1.setA1GeoType(Integer.parseInt(tokens[35]));

                        v1.setA1GeoName(tokens[36]);
                        v1.setA1GeoCountry(tokens[37]);
                        v1.setA1GeoAdm(tokens[38]);

                        // Check Lat and Long for empty string
                        if (tokens[39].length() > 0)
                            v1.setA1Lat(Double.parseDouble(tokens[39]));

                        if (tokens[40].length() > 0)
                            v1.setA1Long(Double.parseDouble(tokens[40]));

                        v1.setA1GeoFeature(tokens[41]);

                        if (tokens[42].length() > 0)
                            v1.setA2GeoType(Integer.parseInt(tokens[42]));

                        v1.setA2GeoName(tokens[43]);
                        v1.setA2GeoCountry(tokens[44]);
                        v1.setA2GeoAdm(tokens[45]);

                        // Check Lat and Long for empty string
                        if (tokens[46].length() > 0)
                            v1.setA2Lat(Double.parseDouble(tokens[46]));

                        if (tokens[47].length() > 0)
                            v1.setA2Long(Double.parseDouble(tokens[47]));

                        v1.setA2GeoFeature(tokens[48]);

                        if (tokens[49].length() > 0)
                            v1.setActionGeoType(Integer.parseInt(tokens[49]));

                        v1.setActionGeoName(tokens[50]);
                        v1.setActionCountry(tokens[51]);
                        v1.setActionGeoAdm(tokens[52]);

                        // Check Lat and Long for empty string
                        if (tokens[53].length() > 0)
                            v1.setActionLat(Double.parseDouble(tokens[53]));

                        if (tokens[54].length() > 0)
                            v1.setActionLong(Double.parseDouble(tokens[54]));

                        v1.setActionGeoFeature(tokens[55]);
//                        try {
//                            v1.setDateAdded(sdf.parse(tokens[56]));
//                        } catch(ParseException pe) {
//                            //TODO remove print for do nothing - key will not be added
//                            pe.printStackTrace();
//                        }


                        // Version One - SourceURL
                        if(tokens.length == 58)
                            v1.setSourceUrl(tokens[57]);

                        // Add fileName to doc
                        //v1.setFileName(outFile);

                        // Write to File
                        raf.writeBytes(gson.toJson(v1));
                        raf.writeBytes(System.lineSeparator());




                        if(pushToES) {

                            // Set Location fields
                            v1.setA1Location(v1.getA1Lat(), v1.getA1Long());
                            v1.setA2Location(v1.getA2Lat(), v1.getA2Long());
                            v1.setActionLocation(v1.getActionLat(), v1.getActionLong());

                            // Get es document header
                            esBatch.append(ElasticSearchService.getDocumentHeader("gdelt-2015", "event.v1", v1.getGeid()))
                                    .append(System.lineSeparator())
                                    .append(gson.toJson(v1))
                                    .append(System.lineSeparator());

                            // Send Batch to es
                            if(lineCounter == BATCH_SIZE) {
                                ElasticSearchService.bulkLoad(esBatch);
                                //System.out.println(esBatch.toString());
                                lineCounter = 0;
                                esBatch.delete(0, esBatch.length());
                                System.out.println("Batch Sent");
                            }
                        }



                        //break;
                    }
                }
            }
        }

        // Send leftover
        if(esBatch.length() > 0) {
            ElasticSearchService.bulkLoad(esBatch);
            System.out.println("Batch Sent");
        }
    }

    public static void main(String[] args) throws IOException {

        S3Service s3 = new S3Service();
        Compressor compressor = new Compressor();

//        List<Path> files = new ArrayList<>();
//        try {
//            files = Processor.listSourceFiles(Paths.get("."));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //System.out.println(files);

        Path dir = Paths.get(".");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{zip}")) {
//            for (Path entry : stream) {
//                //System.out.println(entry.getFileName());
//
//                System.out.println(entry.getFileName());
//
//                readZipFile(entry.getFileName());
//
//                //new Compressor(dir.toString(), entry.getFileName().toString()).compress();
//
//                break;
//
//            }
            //File jsonFile = new File("D:/data/in-gdelt/20150210.export.csv.zip");
            //readZipFile(Paths.get(jsonFile.getAbsolutePath()));

            //compressor.compress(jsonFile);
            //s3.pushToS3(jsonFile.getName(), jsonFile);

            try {
                s3.pushToS3("20150210.export.csv.zip.gz", new File("20150210.export.csv.zip.gz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {//catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
