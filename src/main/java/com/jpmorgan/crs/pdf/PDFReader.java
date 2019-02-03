package com.jpmorgan.crs.pdf;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * PDFReader Class accpets a pdfFile and provides features to do text extraction and other
 * readonly capabilities.
 * It provides various utilities for the PDF
 */
public class PDFReader {

    private static Logger logger = LoggerFactory.getLogger(PDFReader.class);

    private RandomAccessFile pdfFile;
    private String pdfText;

    /**
     * @param pdfFile : A RandomAccessFile which holds a pdf file
     *                to be set to variable in the PDFReader object.
     *                Constructor for PDFReader.
     */
    PDFReader(RandomAccessFile pdfFile) {
        this.pdfFile = pdfFile;
    }

    /**
     * @return This function extracts PDF Text by using pdfBox objects.
     * Once text is extracted its assigned to pdfText variable and the same
     * is returned back to the caller. In case of password error or IO error
     * it will set the text content as null and return the same.
     */
    String extractPDFText(){

        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        try {
            PDFParser parser = new PDFParser(pdfFile);
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            pdfStripper.setStartPage(1);
            pdfStripper.setEndPage(pdfStripper.getEndPage());
            pdfText = pdfStripper.getText(pdDoc);
            logger.debug(pdfText);
        } catch (InvalidPasswordException e) {
            logger.error("PDF FIle password invalid exception.");
            if (pdfFile != null) {
                logger.error(pdfFile.toString());
            }
            logger.error("passworderror", e);
            pdfText = null;
            return null;
        }
        catch (IOException e) {
            logger.error("File not found object is not PDF File.");
            if (pdfFile != null) {
                logger.error(pdfFile.toString());
            }
            logger.error("ioexception", e);
            pdfText = null;
            return null;
        }
        return pdfText;
    }

    public RandomAccessFile getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(RandomAccessFile pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getPdfText() {
        return pdfText;
    }
}