package com.example.a90531.medicheck;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class templatePDF {
    private Context context;
    private File pdfile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle=new Font(Font.FontFamily.TIMES_ROMAN,20, Font.BOLD);
    private Font fSubTitle=new Font(Font.FontFamily.TIMES_ROMAN,18, Font.BOLD);
    private Font fText=new Font(Font.FontFamily.TIMES_ROMAN,12, Font.BOLD);
    private Font fHighText=new Font(Font.FontFamily.TIMES_ROMAN,15, Font.BOLD, BaseColor.RED);

    public templatePDF() {}

    public templatePDF(Context context) {
        this.context = context;
    }

    public void openDocument(){
        createFile();
        try {
            document=new Document(PageSize.A4);
            pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(pdfile));
            document.open();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void createFile(){
        File folder=new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if(!folder.exists())
            folder.mkdirs();
        pdfile=new File(folder,"templatePDF.pdf");

    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title,String subject,String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addSingleTitle(String sTitle){
        try {
            paragraph=new Paragraph();
            addChildP(new Paragraph(sTitle,fTitle));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTitle(String title, String subTitle, String date){
        try {
            paragraph=new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            addChildP(new Paragraph(subTitle,fSubTitle));
            addChildP(new Paragraph("Tarih : "+date,fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addImage(Image data){
        try {
            data.setAlignment(Element.ALIGN_CENTER);
            document.add(data);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }



    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try {
            paragraph=new Paragraph(text,fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTable(String []header, ArrayList<String[]>clients){
        try {
            paragraph=new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable=new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC=0;
            while (indexC<header.length){
                pdfPCell=new PdfPCell(new Phrase(header[indexC++],fSubTitle));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPTable.addCell(pdfPCell);
            }
            for (int indexR=0;indexR<clients.size();indexR++){
                String[]row=clients.get(indexR);
                for (indexC=0;indexC<header.length;indexC++){
                    pdfPCell=new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }
            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewPDF(){
        Intent i = new Intent(context,MainPageActivity.class);
        i.putExtra("path",pdfile.getAbsoluteFile());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
