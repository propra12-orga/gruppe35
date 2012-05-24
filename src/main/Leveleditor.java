package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Leveleditor {
	public static void main(String[] args) throws Exception {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Enter Name of the level: ");
			String levelname = bf.readLine();
			System.out.print("Enter X size of your level ");
			String str = bf.readLine();
			int xsize = Integer.parseInt(str);
			System.out.print("Enter Y size of your level ");
			str = bf.readLine();
			int ysize = Integer.parseInt(str);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement(levelname);
			document.appendChild(rootElement);
			System.out
					.println("Valid fieldtypes are f = Floor, s = Stone, e = Earth and x = Exit");
			for (int x = 1; x <= xsize; x++) {
				Element xElement = document.createElement("X");
				rootElement.appendChild(xElement);
				for (int y = 1; y <= ysize; y++) {
					Element yElement = document.createElement("Y");
					xElement.appendChild(yElement);
					System.out.print("Enter the type of field " + x + "," + y + ":");
					String fieldtype = "";
					Scanner kb = new Scanner(System.in);
					boolean pressed = false;
					while (!pressed) {
						fieldtype = kb.next();
						if ((fieldtype.equals("F")) || fieldtype.equals("f"))
							pressed = true;
						if ((fieldtype.equals("S")) || fieldtype.equals("s"))
							pressed = true;
						if ((fieldtype.equals("E")) || fieldtype.equals("e"))
							pressed = true;
						if ((fieldtype.equals("X")) || fieldtype.equals("x"))
							pressed = true;
					}
					yElement.appendChild(document.createTextNode(fieldtype));
				}
			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File("Levels/"
					+ levelname + ".txt"));
			transformer.transform(source, result);
			System.out.println("Level created!");
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}
	}
}