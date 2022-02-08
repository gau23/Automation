///*     */ package com.test.Tests1;
///*     */ 
///*     */ import java.awt.BorderLayout;
///*     */ import java.awt.Insets;
///*     */ import java.awt.event.ActionEvent;
///*     */ import java.awt.event.ActionListener;import java.io.BufferedReader;import java.io.File;import java.io.FileInputStream;import java.io.FileOutputStream;import java.io.IOException;import java.io.InputStreamReader;import java.net.URL;import java.util.ArrayList;import java.util.List;import javax.swing.ImageIcon;import javax.swing.JButton;import javax.swing.JFileChooser;import javax.swing.JFrame;import javax.swing.JLabel;import javax.swing.JOptionPane;import javax.swing.JPanel;import javax.swing.JTextArea;import javax.swing.JTextField;import javax.swing.SwingUtilities;import javax.swing.UIManager;import org.apache.poi.ss.usermodel.Cell;import org.apache.poi.ss.usermodel.CellStyle;import org.apache.poi.ss.usermodel.Row;import org.apache.poi.xssf.usermodel.XSSFFont;import org.apache.poi.xssf.usermodel.XSSFSheet;import org.apache.poi.xssf.usermodel.XSSFWorkbook;
///*     */ public class CreateDDTExcelTemplate extends JPanel implements ActionListener {
///*     */   public static final long serialVersionUID = 1L;
///*     */   
///*     */   public static final String newline = "\n";
///*     */   
///*     */   JButton openButton;
///*     */   
///*     */   JButton createButton;
///*     */   
///*     */   JButton SelectFolderButton;
///*     */   
///*     */   JLabel selectFileLabel;
///*     */   
///*     */   JLabel selectDestinationLabel;
///*     */   
///*     */   JTextField selectFileTextField;
///*     */   
///*     */   JTextField destinationFolderTextField;
///*     */   
///*     */   JTextArea log;
///*     */   
///*     */   JFileChooser fc;
///*     */   
///*     */   public String strFilePath;
///*     */   
///*     */   public String strFileName="C:\\Users\\EX889DG\\Downloads\\Framework-20210830T101420Z-001\\Framework\\MAIN\\Script\\SONATA_BUSINESS_PROCESS\\WEALTH_MANAGEMENT\\CREATE_ACCOUNT_MAIN_DDT.svb";
///*     */   
///*     */   public String strFolderPath;
///*     */   
///*     */   File file;
///*     */   
///*     */   File folder;
///*     */   
///*     */   public List<String> main_field_list;
///*     */   
///*     */   public List<String> data_field_list;
///*     */   
///*     */   public static void main(String[] args) {
///*  45 */     SwingUtilities.invokeLater(new Runnable() {
///*     */ 
///*     */           public void run() {
///*  48 */             UIManager.put("swing.boldMetal", Boolean.FALSE);
///*  49 */             CreateDDTExcelTemplate.createAndShowGUI();
///*     */           }
///*     */         });
///*     */   }
///*     */ 
///*     */   public CreateDDTExcelTemplate() {
///*  55 */     super(new BorderLayout());
///*     */ 
///*     */ 
///*     */ 
///*  59 */     this.log = new JTextArea(2, 20);
///*  60 */     this.log.setMargin(new Insets(10, 10, 10, 10));
///*  61 */     this.log.setEditable(false);
///*     */ 
///*     */ 
///*  64 */     this.fc = new JFileChooser();
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*  69 */     this.selectFileLabel = new JLabel("Select Business Process Script (SVB): ");
///*  70 */     this.selectFileTextField = new JTextField(50);
///*  71 */     this.selectDestinationLabel = new JLabel("Select Destination Folder : ");
///*  72 */     this.destinationFolderTextField = new JTextField(50);
///*  73 */     this.destinationFolderTextField.setText(System.getProperty("user.dir"));
///*     */ 
///*  75 */     this.openButton = new JButton("Browse ...");
///*  76 */     this.openButton.addActionListener(this);
///*     */ 
///*  78 */     this.SelectFolderButton = new JButton("Browse ...");
///*  79 */     this.SelectFolderButton.addActionListener(this);
///*     */ 
///*  81 */     this.createButton = new JButton("Create Excel File Format");
///*  82 */     this.createButton.addActionListener(this);
///*     */ 
///*     */ 
///*  85 */     JPanel panel1 = new JPanel();
///*  86 */     JPanel panel2 = new JPanel();
///*  87 */     JPanel panel3 = new JPanel();
///*  88 */     panel1.add(this.selectFileLabel);
///*  89 */     panel1.add(this.selectFileTextField);
///*  90 */     panel1.add(this.openButton);
///*  91 */     panel2.add(this.selectDestinationLabel);
///*  92 */     panel2.add(this.destinationFolderTextField);
///*  93 */     panel2.add(this.SelectFolderButton);
///*  94 */     panel3.add(this.createButton);
///*     */ 
///*     */ 
///*  97 */     add(panel1, "First");
///*  98 */     add(panel2, "Center");
///*  99 */     add(panel3, "Last");
///*     */   }
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */   public void actionPerformed(ActionEvent e) {
///* 109 */     if (e.getSource() == this.openButton) {
///* 110 */       int returnVal = this.fc.showOpenDialog(this);
///*     */ 
///* 112 */       if (returnVal == 0) {
///* 113 */         File file = this.fc.getSelectedFile();
///*     */ 
///*     */ 
///* 116 */         this.strFilePath = file.getAbsolutePath();
///* 117 */         this.selectFileTextField.setText(this.strFilePath);
///*     */       } else {
///* 119 */         this.log.append("Open command cancelled by user.\n");
///*     */       } 
///* 121 */       this.log.setCaretPosition(this.log.getDocument().getLength());
///*     */ 
///* 123 */     } else if (e.getSource() == this.SelectFolderButton) {
///* 124 */       JFileChooser fc = new JFileChooser();
///* 125 */       fc.setCurrentDirectory(new File("."));
///* 126 */       fc.setFileSelectionMode(1);
///* 127 */       int returnVal = fc.showOpenDialog(this);
///* 128 */       if (returnVal == 0) {
///* 129 */         File folder = fc.getSelectedFile();
///* 130 */         this.destinationFolderTextField.setText(folder.getAbsolutePath());
///*     */ 
///*     */       } 
///* 133 */     } else if (e.getSource() == this.createButton) {
///*     */ 
///* 135 */       this.log.append("Export in progress ... ");
///*     */ 
///* 137 */       createExcelFormatFile();
///*     */ 
///* 139 */       JOptionPane.showMessageDialog(null, "File Created Successfully !");
///* 140 */       int dialogButton = 0;
///* 141 */       int dialogResult = JOptionPane.showConfirmDialog(this, "Do you want to generate more files ?", 
///* 142 */           "Input File Creation", dialogButton);
///* 143 */       if (dialogResult == 0) {
///* 144 */         System.out.println("Yes option");
///*     */       } else {
///* 146 */         System.out.println("No Option");
///* 147 */         setVisible(false);
///* 148 */         System.exit(0);
///*     */       } 
///*     */     } 
///*     */   }
///*     */ 
///*     */ 
///*     */ 
///*     */   protected static ImageIcon createImageIcon(String path) {
///* 156 */     URL imgURL = CreateDDTExcelTemplate.class.getResource(path);
///* 157 */     if (imgURL != null)
///* 158 */       return new ImageIcon(imgURL); 
///*     */ 
///* 160 */     System.err.println("Couldn't find file: " + path);
///* 161 */     return null;
///*     */   }
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */   public static void createAndShowGUI() {
///* 171 */     JFrame frame = new JFrame("Create Input Test Data (excel) Format for Business Process ... ");
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///* 177 */     frame.setDefaultCloseOperation(3);
///* 178 */     frame.setBounds(200, 200, 400, 400);
///*     */ 
///*     */ 
///* 181 */     frame.add(new CreateDDTExcelTemplate());
///*     */ 
///*     */ 
///* 184 */     frame.pack();
///* 185 */     frame.setVisible(true);
///*     */   }
///*     */ 
///*     */ 
///*     */   public void createExcelFormatFile() {
///* 190 */     String strFullFilePath = this.selectFileTextField.getText();
///* 191 */     File file = new File(strFullFilePath);
///*     */ 
///*     */ 
///* 194 */     List<String> main_field_list = new ArrayList<>();
///* 195 */     List<String> data_field_list = new ArrayList<>();
///* 196 */     List<String> unique_main_field_list = new ArrayList<>();
///* 197 */     List<String> unique_data_field_list = new ArrayList<>();
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */     try {
///* 203 */       InputStreamReader In = new InputStreamReader(new FileInputStream(file));
///* 204 */       BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), In.getEncoding()));      String sc;
///* 205 */       while ((sc = br.readLine()) != null) {
///* 206 */         String strLine = sc.toString().trim();
///*     */ 
///* 208 */         if (strLine.contains("isMAINFieldExists")) {
///* 209 */           String[] strArr = strLine.split(" ");
///* 210 */           for (int i = 0; i < strArr.length - 1; i++) {
///* 211 */             if (strArr[i].length() > 3) {
///*     */ 
///* 213 */               int startchar = strArr[i].indexOf('"');
///* 214 */               int endchar = strArr[i].lastIndexOf('"');
///*     */ 
///* 216 */               if (startchar > 0 && endchar > startchar) {
///* 217 */                 String newString = strArr[i].substring(startchar + 1, endchar);
///* 218 */                 main_field_list.add(newString);
///*     */ 
///*     */               } 
///*     */             } 
///*     */           } 
///*     */         } 
///* 224 */         if (strLine.contains("initializeInputTestData_DATA")) {
///*     */ 
///*     */ 
///* 227 */           int startchar = strLine.indexOf('"');
///* 228 */           int endchar = strLine.lastIndexOf('"');
///*     */ 
///* 230 */           if (startchar > 0 && endchar > 0) {
///* 231 */             String newString = strLine.substring(startchar + 1, endchar);
///* 232 */             main_field_list.add(newString);
///* 233 */             data_field_list.add(newString);
///*     */ 
///*     */ 
///*     */           } 
///*     */         } 
///* 238 */         if (strLine.contains("isDATAFieldExists")) {
///* 239 */           String[] strArr = strLine.split(" ");
///* 240 */           for (int i = 0; i < strArr.length - 1; i++) {
///* 241 */             if (strArr[i].length() > 3) {
///*     */ 
///* 243 */               int startchar = strArr[i].indexOf('"');
///* 244 */               int endchar = strArr[i].lastIndexOf('"');
///*     */ 
///* 246 */               if (startchar > 0 && endchar > 0) {
///* 247 */                 String newString = strArr[i].substring(startchar + 1, endchar);
///* 248 */                 data_field_list.add(newString);
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */               } 
///*     */             } 
///*     */           } 
///*     */         } 
///*     */       } 
///* 260 */       for (String s : main_field_list) {
///* 261 */         if (!unique_main_field_list.contains(s))
///* 262 */           unique_main_field_list.add(s); 
///*     */       } 
///* 264 */       for (String s : data_field_list) {
///* 265 */         if (!unique_data_field_list.contains(s))
///* 266 */           unique_data_field_list.add(s); 
///*     */       } 
///* 268 */       ExcelWriter(unique_main_field_list, unique_data_field_list);
///* 269 */     } catch (IOException e) {
///*     */ 
///* 271 */       e.printStackTrace();
///*     */     } 
///*     */   }
///*     */ 
///*     */ 
///*     */ 
///*     */   public void ExcelWriter(List<String> main_field_names, List<String> data_field_names) {
///*     */     try {
///* 279 */       XSSFWorkbook workbook = new XSSFWorkbook();
///* 280 */       XSSFSheet main_sheet = workbook.createSheet("MAIN");
///*     */ 
///*     */ 
///*     */ 
///* 284 */       XSSFFont headerFont = workbook.createFont();
///* 285 */       headerFont.setFontHeightInPoints((short)11);
///* 286 */       headerFont.setFontName("Calibri");
///* 287 */       headerFont.setBoldweight((short)700);
///*     */ 
///* 289 */       CellStyle headerStyle = workbook.createCellStyle();
///* 290 */       headerStyle.setFillForegroundColor((short)22);
///* 291 */       headerStyle.setFillPattern((short)1);
///* 292 */       headerStyle.setBorderTop((short)1);
///* 293 */       headerStyle.setBorderBottom((short)1);
///* 294 */       headerStyle.setBorderLeft((short)1);
///* 295 */       headerStyle.setBorderRight((short)1);
///* 296 */       headerStyle.setFont(headerFont);
///*     */ 
///*     */ 
///* 299 */       Row row = main_sheet.createRow(0);
///*     */ 
///*     */ 
///* 302 */       Cell cell = row.createCell(0);
///*     */ 
///* 304 */       cell.setCellValue("ID");
///* 305 */       cell.setCellStyle(headerStyle);
///*     */ 
///* 307 */       cell = row.createCell(1);
///* 308 */       cell.setCellValue("TestStepID");
///* 309 */       cell.setCellStyle(headerStyle);
///*     */ 
///* 311 */       cell = row.createCell(2);
///* 312 */       cell.setCellValue("TestRun");
///* 313 */       cell.setCellStyle(headerStyle);
///*     */ 
///* 315 */       int columnCount = 3;
///* 316 */       for (int x = 0; x < main_field_names.size(); x++) {
///* 317 */         cell = row.createCell(columnCount++);
///* 318 */         cell.setCellValue(main_field_names.get(x));
///* 319 */         cell.setCellStyle(headerStyle);
///*     */       } 
///* 321 */       for (int j = 0; j < columnCount; j++)
///* 322 */         main_sheet.autoSizeColumn(j); 
///*     */ 
///*     */ 
///*     */ 
///* 326 */       if (!data_field_names.isEmpty()) {
///* 327 */         XSSFSheet data_sheet = workbook.createSheet("DATA");
///* 328 */         row = data_sheet.createRow(0);
///* 329 */         columnCount = 0;
///* 330 */         for (int y = 0; y < data_field_names.size(); y++) {
///* 331 */           cell = row.createCell(columnCount++);
///* 332 */           cell.setCellValue(data_field_names.get(y));
///* 333 */           cell.setCellStyle(headerStyle);
///*     */         } 
///* 335 */         for (int i = 0; i < columnCount; i++)
///* 336 */           data_sheet.autoSizeColumn(i); 
///*     */ 
///*     */       } 
///* 339 */       this.strFilePath = this.selectFileTextField.getText();
///* 340 */       File f = new File(this.strFilePath);
///* 341 */       this.strFileName = f.getName();
///*     */ 
///* 343 */       this.strFolderPath = this.destinationFolderTextField.getText();
///* 344 */       File f1 = new File(this.strFolderPath);
/////* 345 */       if (f1.exists()) {
/////* 346 */         strnewFilePath = String.valueOf(this.strFolderPath) + "\\" + this.strFileName;
/////*     */       } else {
/////* 348 */         strnewFilePath = this.strFilePath;
///////*     */       } 
/////* 350 */       String strnewFilePath = strnewFilePath.replace(".svb", ".xlsx");
/////*     */ 
/////* 352 */       FileOutputStream outputStream = new FileOutputStream(strnewFilePath);
/////*     */ 
/////* 354 */       workbook.write(outputStream);
/////* 355 */       workbook.close();
/////*     */ 
///* 357 */     } catch (IOException e) {
///*     */ 
///* 359 */       e.printStackTrace();
///* 360 */     } catch (Exception e) {
///* 361 */       e.printStackTrace();
///*     */     } 
///*     */   }
///*     */ }
