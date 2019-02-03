package com.jpmorgan.crs.pdf;

import org.apache.pdfbox.io.RandomAccessFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class PDFTextExtractor {
    private static Logger logger = LoggerFactory.getLogger(PDFTextExtractor.class);
    public static void main(String[] args) {
        String resourceDirectory = PDFTextExtractor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        logger.debug(resourceDirectory);

        File pdfFile = new File(resourceDirectory + "France.pdf");
        RandomAccessFile pdfObjectFile = null;
        try {
            pdfObjectFile = new RandomAccessFile(pdfFile, "r");
        }
        catch (FileNotFoundException e){
            logger.error("File not found object is not PDF File.");
                logger.error(pdfFile.toString());
            logger.error("fileexception", e);
        }
        PDFReader pdfReader = new PDFReader(pdfObjectFile);

        String pdfText = pdfReader.extractPDFText();
        if(pdfText == null)
            logger.error("Could not retrieve text from pdf file.");
        else
            logger.info("An exception occurred with message: {}", pdfText.length());
    }
}
