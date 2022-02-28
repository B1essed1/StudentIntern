package com.example.studentintern.service.serviceImpl;

import com.example.studentintern.entity.Schedules;
import com.example.studentintern.entity.Student;
import com.example.studentintern.service.PDFService;
import com.example.studentintern.service.ScheduleService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDate;


@Service
public class PDFServiceImpl implements PDFService {

    private  final ScheduleService scheduleService;

    public PDFServiceImpl(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @Override
    public void studentToPDF(Student student,String date) throws FileNotFoundException {


        String path = "C:\\Users\\User\\Desktop\\resume.pdf";

        ImageData imageData1 = ImageDataFactory.create(student.getImage());

        Image image = new Image(imageData1);
        image.setFixedPosition(450 , 700)
                .setHeight(115)
                .setWidth(115);


        PdfWriter pdfWriter = new PdfWriter(path);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Paragraph paragraph  = new Paragraph(student.getUsername()+" "+ student.getSecondName());
        Paragraph paragraph2 = new Paragraph("Somebody with great enthusiasm to his work with great ambitions");
        paragraph2.setFontColor(Color.DARK_GRAY);
        Paragraph paragraph3 = new Paragraph(student.getLocation());
        paragraph3.setFontColor(Color.DARK_GRAY);
        Paragraph paragraph4 = new Paragraph("+998977777777");
        paragraph4.setFontColor(Color.DARK_GRAY);
        paragraph.setFontSize(24);
        paragraph.setBold();

        Paragraph paragraphStack = new Paragraph("Technical stacks ");
        paragraphStack.setFixedPosition(50, 600 ,  150);
        paragraphStack.setBold().setFontSize(18);
        paragraphStack.setFontColor(Color.DARK_GRAY);


        float [] pointColumnWidths = {80F, 80F, 80F};
        Table table = new Table(pointColumnWidths);

        table.addCell(new Cell().add("Date"));
        table.addCell(new Cell().add("Time"));
        table.addCell(new Cell().add("Register"));


         java.util.List<Schedules> schedulesList = scheduleService.getSchedulesByDate(LocalDate.parse(date), student.getId());

        for (Schedules s:  schedulesList) {
            table.addCell(new Cell().add(s.getDate().toString()));
            table.addCell(new Cell().add(s.getTime().toString()));
            table.addCell(new Cell().add(s.getIsWork() ? "Keldi"  : "Ketti"));

        }

        table.setFixedPosition(300,250,220);

        List list = new List();
        list.add("Java Core ");
        list.add("Spring Boot");
        list.add("Postgres SQL");
        list.add("Hibernate ");
        list.add("RabbitMQ ");
        list.add("Elastic Search");
        list.add("Docker");
        list.setFixedPosition(60 ,450 , 150);

        Document document = new Document(pdfDocument);
        document.add(image);
        document.add(table);
        document.add(paragraph);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraphStack);
        document.add(list);



        document.close();

    }
}
