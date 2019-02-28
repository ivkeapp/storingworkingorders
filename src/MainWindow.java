
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Korisnik Nepo System
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    String date;
    String homePath;
    String pathTo;
    boolean theresFile;
    int currentID;

    public void gettingDesktopPath() {
        //getting Desktop path independed from operating system
        FileSystemView filesys = FileSystemView.getFileSystemView();
        File[] roots = filesys.getRoots();
        homePath = filesys.getHomeDirectory().toString();
        System.out.println(homePath);
        //checking if file existing on that location
        pathTo = homePath + "\\SpisakReversa.xlsx";
    }

    public boolean isFileThere() {
        return theresFile = new File(pathTo).exists();
    }

    public void gettingID() {
        if (theresFile) {
            try {
                FileInputStream myxls = new FileInputStream(pathTo);
                Workbook workbook = WorkbookFactory.create(myxls);
                //getting the sheet at index zero
                Sheet sheet = workbook.getSheetAt(0);
                int lastRow = sheet.getLastRowNum();
                Row row = sheet.getRow(lastRow);
                Cell cell = row.getCell(0);
                Double doubleID = cell.getNumericCellValue();
                currentID = doubleID.intValue() + 1;
                System.out.println("currentID: " + currentID);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncryptedDocumentException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            currentID = 1;
        }
    }

    public void parsingExcel() {
        //adding values to existing Excel sheet NEED TO FINISH
        theresFile = new File(pathTo).exists();
        if (theresFile) {
            try {
                FileInputStream myxls = new FileInputStream(pathTo);
                Workbook workbook = WorkbookFactory.create(myxls);
                //getting the sheet at index zero
                Sheet sheet = workbook.getSheetAt(0);
                int lastRow = sheet.getLastRowNum();
                System.out.println("currentID: " + currentID);
                Row row2 = sheet.createRow(lastRow + 1);
                Data data = new Data(currentID++, jTextField1.getText(), jTextField2.getText(),
                        jTextFieldModel.getText(), jTextFieldSerialN.getText(),
                        Integer.parseInt(jTextFieldComments.getText()), jTextAreaDescription.getText());

                for (int i = 0; i < 7; i++) {
                    Cell cell4 = row2.createCell(i);
                    switch (i) {
                        case 0:
                            cell4.setCellValue(data.getID());
                            break;
                        case 1:
                            cell4.setCellValue((String) data.getName());
                            break;
                        case 2:
                            cell4.setCellValue((String) data.getDevice());
                            break;
                        case 3:
                            cell4.setCellValue((String) data.getDeviceModel());
                            break;
                        case 4:
                            cell4.setCellValue((String) data.getSerialNumber());
                            break;
                        case 5:
                            cell4.setCellValue((int) data.getOptional());
                            break;
                        case 6:
                            cell4.setCellValue((String) data.getDecription());
                            break;
                    }

                }
                FileOutputStream outputStream = new FileOutputStream(pathTo);
                workbook.write(outputStream);
                workbook.close();
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncryptedDocumentException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (jTextFieldComments.getText().equals("") || jTextFieldComments.getText().equals(null)) {
                JOptionPane.showMessageDialog(null, "Morate uneti količinu");
            } else {
                try {
                    Data data = new Data(1, jTextField1.getText(), jTextField2.getText(),
                            jTextFieldModel.getText(), jTextFieldSerialN.getText(),
                            Integer.parseInt(jTextFieldComments.getText()), jTextAreaDescription.getText());
                    writtingExcelFile(makingExcelWorkbook(data));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

//        try {
//            FileInputStream myxls = new FileInputStream(pathTo);
//            HSSFWorkbook studentsSheet = new HSSFWorkbook(myxls);
//            HSSFSheet worksheet = studentsSheet.getSheetAt(0);
//            int lastRow = worksheet.getLastRowNum();
//            System.out.println(lastRow);
//       Row row = worksheet.createRow(++lastRow);
//       row.createCell(1).setCellValue("Dr.Hola");
//       myxls.close();
//       FileOutputStream output_file =new FileOutputStream(new File("poi-testt.xls"));  
//       //write changes
//       studentsSheet.write(output_file);
//       output_file.close();
//       System.out.println(" is successfully written");
//        } catch (Exception e) {
//        }
    }

    public MainWindow() {
        initComponents();
        this.setLocationRelativeTo(null);
        gettingDesktopPath();
        isFileThere();
        gettingID();
        String pattern = "dd.MM.yyyy.";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate now = LocalDate.now();
        date = now.toString();
        date = now.format(formatter);

        jTextField1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jTextField1.setText("");
                jTextField1.removeMouseListener(this);
            }
        });

        jTextField2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jTextField2.setText("");
                jTextField2.removeMouseListener(this);
            }
        });

        jTextFieldModel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jTextFieldModel.setText("");
                jTextFieldModel.removeMouseListener(this);
            }
        });

        jTextFieldSerialN.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jTextFieldSerialN.setText("");
                jTextFieldSerialN.removeMouseListener(this);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabelNsme = new javax.swing.JLabel();
        jLabelDevice = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jLabelModel = new javax.swing.JLabel();
        jTextFieldModel = new javax.swing.JTextField();
        jLabelSerijskiBr = new javax.swing.JLabel();
        jTextFieldSerialN = new javax.swing.JTextField();
        jLabelComment = new javax.swing.JLabel();
        jTextFieldComments = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));

        jTextField1.setText("Unesite ime mušterije");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabelNsme.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNsme.setText("Ime i prezime klijenta:");

        jLabelDevice.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDevice.setText("Broj telefona:");

        jTextField2.setText("Unesite broj telefona");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Opis radova:");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jScrollPane1.setViewportView(jTextAreaDescription);

        jLabelModel.setForeground(new java.awt.Color(255, 255, 255));
        jLabelModel.setText("Uređaj:");

        jTextFieldModel.setText("Unesite model uređaja");

        jLabelSerijskiBr.setForeground(new java.awt.Color(255, 255, 255));
        jLabelSerijskiBr.setText("Zahtev klijenta:");

        jTextFieldSerialN.setToolTipText("");

        jLabelComment.setForeground(new java.awt.Color(255, 255, 255));
        jLabelComment.setText("Količina:");

        jButton1.setText("Sačuvaj");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Štampaj");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jTextField2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                    .addComponent(jLabelModel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldModel)
                    .addComponent(jLabelSerijskiBr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldSerialN)
                    .addComponent(jLabelComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldComments)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNsme)
                            .addComponent(jLabelDevice)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabelNsme)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDevice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelModel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSerijskiBr)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSerialN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelComment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        //keyPressed(e);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jTextFieldComments.getText().equals("") || jTextFieldComments.getText().equals(null)) {
            JOptionPane.showMessageDialog(null, "Morate uneti količinu");
        } else {
            Data data = new Data(currentID, jTextField1.getText(), jTextField2.getText(),
                    jTextFieldModel.getText(), jTextFieldSerialN.getText(),
                    Integer.parseInt(jTextFieldComments.getText()), jTextAreaDescription.getText());

            String pattern = "yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate now = LocalDate.now();
            String date2 = now.toString();
            date2 = now.format(formatter);

            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("line.separator"));
            sb.append("DULE VIKLER");
            sb.append(System.getProperty("line.separator"));
            sb.append("------------------------------------------------------------------------------------------------------------------");
            sb.append(System.getProperty("line.separator"));
            sb.append("Datum: " + date + "                                                                    Revers broj: " + date2 + "/" + data.getID());
            sb.append(System.getProperty("line.separator"));
            sb.append("------------------------------------------------------------------------------------------------------------------");
            sb.append(System.getProperty("line.separator"));
            sb.append("Klijent: " + jTextField1.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("Broj telefona: " + jTextField2.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("------------------------------------------------------------------------------------------------------------------");
            sb.append(System.getProperty("line.separator"));
            sb.append("Uređaj: " + jTextFieldModel.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("Zahtev klijenta: " + jTextFieldSerialN.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("Količina: " + jTextFieldComments.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("------------------------------------------------------------------------------------------------------------------");
            sb.append(System.getProperty("line.separator"));
            sb.append("Izvršeni radovi: " + jTextAreaDescription.getText());
            sb.append(System.getProperty("line.separator"));
            sb.append("------------------------------------------------------------------------------------------------------------------");
            sb.append(System.getProperty("line.separator"));
            sb.append(System.getProperty("line.separator"));
            sb.append("Dule Vikler                                                        Preuzeo:_______________________");

            JTextPane jtp = new JTextPane();
            jtp.setBackground(Color.white);
            jtp.insertIcon(new ImageIcon("C:\\Users\\Korisnik Nepo System\\Desktop\\rotor.jpg"));
            try {
                jtp.getDocument().insertString(1, sb.toString(), null);
            } catch (BadLocationException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean show = true;
            try {
                jtp.print(null, null, show, null, null, show);
            } catch (java.awt.print.PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        parsingExcel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private XSSFWorkbook makingExcelWorkbook(Data data) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Report");
        Row row1 = sheet.createRow(0);
        Cell cell2 = row1.createCell(0);
        cell2.setCellValue("ID");
        Cell cell3 = row1.createCell(1);
        cell3.setCellValue("Ime i prezime");
        Cell cell4 = row1.createCell(2);
        cell4.setCellValue("Telefon");
        Cell cell5 = row1.createCell(3);
        cell5.setCellValue("Uređaj");
        Cell cell6 = row1.createCell(4);
        cell6.setCellValue("Zahtev klijenta");
        Cell cell7 = row1.createCell(5);
        cell7.setCellValue("Količina");
        Cell cell8 = row1.createCell(6);
        cell8.setCellValue("Opis radova");
        Row row = sheet.createRow(1);

        for (int i = 0; i < 7; i++) {
            Cell cell = row.createCell(i);
            switch (i) {
                case 0:
                    cell.setCellValue((int) data.getID());
                    break;
                case 1:
                    cell.setCellValue((String) data.getName());
                    break;
                case 2:
                    cell.setCellValue((String) data.getDevice());
                    break;
                case 3:
                    cell.setCellValue((String) data.getDeviceModel());
                    break;
                case 4:
                    cell.setCellValue((String) data.getSerialNumber());
                    break;
                case 5:
                    cell.setCellValue((int) data.getOptional());
                    break;
                case 6:
                    cell.setCellValue((String) data.getDecription());
                    break;
            }

        }
        return workbook;
    }

    private void writtingExcelFile(XSSFWorkbook workbook) throws FileNotFoundException {

        try {
            FileOutputStream outputStream = new FileOutputStream(pathTo);
            workbook.write(outputStream);
            workbook.close();
            System.out.println("File written successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("FAILED! The process cannot access the file because it is being used by another process or file is missing.");
        } catch (IOException e) {
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new MainWindow().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelComment;
    private javax.swing.JLabel jLabelDevice;
    private javax.swing.JLabel jLabelModel;
    private javax.swing.JLabel jLabelNsme;
    private javax.swing.JLabel jLabelSerijskiBr;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaDescription;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldComments;
    private javax.swing.JTextField jTextFieldModel;
    private javax.swing.JTextField jTextFieldSerialN;
    // End of variables declaration//GEN-END:variables

}
