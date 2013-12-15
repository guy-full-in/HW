import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DOCXParser {
	private String name;
	private String surname;
	private String path;
	private String temp = "docx";
	private String wordDocument = temp + "/word/document.xml";
	private String newZip = "result.docx";
	private List<String> fileList;
	
	public DOCXParser(String name, String surname, String path) {
		this.name = name;
		this.surname = surname;
		this.path = path;
		this.fileList = new ArrayList<String>();
	}

	public void unzip() throws IOException {
		byte[] buffer = new byte[1024];

		try {

			File folder = new File(temp);
			if (!folder.exists()) {
				folder.mkdir();
			}

			ZipInputStream zis = new ZipInputStream(new FileInputStream(path));

			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				String fileName = entry.getName();
				File newFile = new File(temp + "/" + fileName);
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				entry = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void parse(){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try{
			
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(wordDocument);
			NodeList nodeList = document.getElementsByTagName("w:t");
			
			for(int i =0;i<nodeList.getLength();i++){
				Node text = nodeList.item(i);
				if(text.getTextContent().contains("template_name")){
					text.setTextContent(text.getTextContent().replaceAll("template_name", name));
				}
				if(text.getTextContent().contains("template_surname")){
					text.setTextContent(text.getTextContent().replaceAll("template_surname", surname));
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(wordDocument);
			DOMSource source = new DOMSource(document);
			transformer.transform(source, result);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

	public void zip() {
		
		getFileList(new File(temp));

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(newZip);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String file : this.fileList) {

				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(temp + "/" + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}


	public void getFileList(File node) {

		if (node.isFile()) {
			fileList.add(getEntry(node.toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				getFileList(new File(node, filename));
			}
		}

	}

	private String getEntry(String file) {
		return file.substring(temp.length() + 1, file.length());
	}
}
