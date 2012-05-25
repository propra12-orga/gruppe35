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
			String str = "";
			boolean valid;
			int xsize = 1;
			int ysize = 1;
			// Read in levelname
			System.out.print("Enter Name of the level: ");
			String levelname = bf.readLine();

			// Create document
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element rootElement = document.createElement(levelname);
			document.appendChild(rootElement);

			// Read in xsize and ysize
			System.out.print("Enter X size of your level ");
			valid = false;
			while (!valid) {
				try {
					str = bf.readLine();
					xsize = Integer.parseInt(str);
					if (xsize > 0) {
						valid = true;
					}

				} catch (NumberFormatException e) {
					valid = false;
				}
			}
			rootElement.setAttribute("xsize", str);
			System.out.print("Enter Y size of your level ");
			valid = false;
			while (!valid) {
				try {
					str = bf.readLine();
					ysize = Integer.parseInt(str);
					if (ysize > 0) {
						valid = true;
					}

				} catch (NumberFormatException e) {
					valid = false;
				}
			}
			rootElement.setAttribute("ysize", str);
			// Lese Felder ein
			Element fieldsElement = document.createElement("fields");
			rootElement.appendChild(fieldsElement);
			System.out
					.println("Valid fieldtypes are f = Floor, s = Stone, e = Earth and x = Exit");
			for (int x = 1; x <= xsize; x++) {
				for (int y = 1; y <= ysize; y++) {
					System.out.print("Enter the type of field " + x + "," + y
							+ ":");
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
					Element fieldElement = document.createElement("field");
					fieldsElement.appendChild(fieldElement);
					fieldElement.setAttribute("x", String.valueOf(x));
					fieldElement.setAttribute("y", String.valueOf(y));
					fieldElement.setAttribute("type", fieldtype);
				}
			}
			// Spawnpoints
			// Füge Spawnpoints element hinzu
			Element spawnpointsElement = document.createElement("spawnpoints");
			rootElement.appendChild(spawnpointsElement);
			// Lese Anzahl Spawnpoints ein
			System.out.print("Enter number of spawnpoints for your level: ");
			valid = false;
			int spawnpointnum = 1;
			while (!valid) {
				try {
					str = bf.readLine();
					spawnpointnum = Integer.parseInt(str);
					if (spawnpointnum > 0) {
						valid = true;
					}

				} catch (NumberFormatException e) {
					valid = false;
				}
			}
			// Füge Attribute x und y hinzu
			for (int i = 1; i <= spawnpointnum; i++) {
				Element spawnpointElement = document
						.createElement("spawnpoint");
				spawnpointsElement.appendChild(spawnpointElement);

				System.out.print("Enter X coordinate of spawnpoint number " + i
						+ " :");
				valid = false;
				while (!valid) {
					try {
						str = bf.readLine();
						int spawnx = Integer.parseInt(str);
						if ((spawnx <= xsize) && (spawnx > 0)) {
							valid = true;
						}

					} catch (NumberFormatException e) {
						valid = false;
					}
				}
				spawnpointElement.setAttribute("x", str);

				System.out.print("Enter Y coordinate of spawnpoint number " + i
						+ " :");
				valid = false;
				while (!valid) {
					try {
						str = bf.readLine();
						int spawny = Integer.parseInt(str);
						if ((spawny <= xsize) && (spawny > 0)) {
							valid = true;
						}

					} catch (NumberFormatException e) {
						valid = false;
					}
				}
				spawnpointElement.setAttribute("y", str);

			}
			
			// Exits
			// Füge Exits element hinzu
			Element exitsElement = document.createElement("exits");
			rootElement.appendChild(exitsElement);
						// Lese Anzahl Spawnpoints ein
						System.out.print("Enter number of exits for your level: ");
						valid = false;
						int exitnum = 1;
						while (!valid) {
							try {
								str = bf.readLine();
								exitnum = Integer.parseInt(str);
								if (spawnpointnum > 0) {
									valid = true;
								}

							} catch (NumberFormatException e) {
								valid = false;
							}
						}
						// Füge Attribute x und y hinzu
						for (int i = 1; i <= exitnum; i++) {
							Element exitElement = document
									.createElement("exit");
							exitsElement.appendChild(exitElement);

							System.out.print("Enter X coordinate of exit number " + i
									+ " :");
							valid = false;
							while (!valid) {
								try {
									str = bf.readLine();
									int exitx = Integer.parseInt(str);
									if ((exitx <= xsize) && (exitx > 0)) {
										valid = true;
									}

								} catch (NumberFormatException e) {
									valid = false;
								}
							}
							exitElement.setAttribute("x", str);

							System.out.print("Enter Y coordinate of exit number " + i
									+ " :");
							valid = false;
							while (!valid) {
								try {
									str = bf.readLine();
									int exity = Integer.parseInt(str);
									if ((exity <= xsize) && (exity > 0)) {
										valid = true;
									}

								} catch (NumberFormatException e) {
									valid = false;
								}
							}
							exitElement.setAttribute("y", str);

						}

			// In Datei schreiben

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