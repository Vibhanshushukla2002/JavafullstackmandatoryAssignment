package com.StudentLibrary.Studentlibrary.Services;


import com.StudentLibrary.Studentlibrary.Model.Book;
import com.StudentLibrary.Studentlibrary.Model.Payment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;




@Service
public class PdfService {

    public byte[] generateReceipt(Payment payment, Book book) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font title =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD,22);

            Font heading =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD,16);

            Font normal =
                    FontFactory.getFont(FontFactory.HELVETICA,14);

            Paragraph p =
                    new Paragraph("LIBRARY MANAGEMENT SYSTEM",title);

            p.setAlignment(Element.ALIGN_CENTER);

            document.add(p);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("---------------------------------------------"));

            document.add(new Paragraph(
                    "Receipt No : LMS-" + payment.getId(),
                    heading));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Student : " + payment.getUsername(),
                    normal));

            document.add(new Paragraph(
                    "Book : " + book.getName(),
                    normal));

            document.add(new Paragraph(
                    "Author : " +
                            (book.getAuthor() != null ? book.getAuthor().getName() : "Unknown"),
                    normal));

            document.add(new Paragraph(
                    "Genre : " + book.getGenre(),
                    normal));

            document.add(new Paragraph(
                    "Amount : ₹" + payment.getAmount(),
                    normal));

            document.add(new Paragraph(
                    "Status : " + payment.getStatus(),
                    normal));

            if(payment.getPaymentDate()!=null){

                document.add(new Paragraph(
                        "Payment Date : " +

                                payment.getPaymentDate()

                                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")),
                        normal));
            }

            document.add(new Paragraph(
                    "Order Id : " + payment.getRazorpayOrderId(),
                    normal));

            document.add(new Paragraph(
                    "Payment Id : " + payment.getRazorpayPaymentId(),
                    normal));

            document.add(new Paragraph(" "));

            Paragraph thanks =
                    new Paragraph("THANK YOU",title);

            thanks.setAlignment(Element.ALIGN_CENTER);

            document.add(thanks);

            document.close();

            return outputStream.toByteArray();

        }

        catch(Exception e){

            throw new RuntimeException(e);

        }

    }

}
