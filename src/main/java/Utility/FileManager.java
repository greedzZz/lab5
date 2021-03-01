package Utility;

import org.w3c.dom.*;
import Content.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.TreeMap;

public class FileManager {
    private final File file;

    public FileManager(File file) {
        this.file = file;
    }

    public void manageXML(TreeMap<Integer, SpaceMarine> treeMap, CollectionPutter collectionPutter) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ScannerXML scannerXML = new ScannerXML(file);
            scannerXML.scan();
            Document document = builder.parse(scannerXML.getBufferedIS());
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("SpaceMarine");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                Node nodeName = element.getElementsByTagName("name").item(0).getFirstChild();
                String nameSM;
                if (nodeName == null) {
                    nameSM = null;
                } else {
                    nameSM = nodeName.getNodeValue();
                }

                int xSM = Integer.parseInt((((Element) document.getElementsByTagName("coordinates").item(i))).getAttributeNode("x").getValue());

                String attrY = (((Element) document.getElementsByTagName("coordinates").item(i))).getAttributeNode("y").getValue();
                Integer ySM;
                if (attrY.equals("")) {
                    ySM = null;
                } else {
                    ySM = Integer.parseInt(attrY);
                }

                Node nodeHealth = element.getElementsByTagName("health").item(0).getFirstChild();
                Integer healthSM;
                if (nodeHealth == null) {
                    healthSM = null;
                } else {
                    healthSM = Integer.parseInt(nodeHealth.getNodeValue());
                }

                Node nodeCategory = element.getElementsByTagName("category").item(0).getFirstChild();
                AstartesCategory categorySM;
                if (nodeCategory == null) {
                    categorySM = null;
                } else {
                    categorySM = AstartesCategory.valueOf(nodeCategory.getNodeValue());
                }

                Node nodeWeapon = element.getElementsByTagName("weaponType").item(0).getFirstChild();
                Weapon weaponSM;
                if (nodeWeapon == null) {
                    weaponSM = null;
                } else {
                    weaponSM = Weapon.valueOf(nodeWeapon.getNodeValue());
                }

                Node nodeMelee = element.getElementsByTagName("meleeWeapon").item(0).getFirstChild();
                MeleeWeapon meleeWeaponSM;
                if (nodeMelee == null) {
                    meleeWeaponSM = null;
                } else {
                    meleeWeaponSM = MeleeWeapon.valueOf(nodeMelee.getNodeValue());
                }

                String attrChapterName = (((Element) document.getElementsByTagName("chapter").item(i))).getAttributeNode("name").getValue();
                String chapterNameSM;
                if (attrChapterName.equals("")) {
                    chapterNameSM = null;
                } else {
                    chapterNameSM = attrChapterName;
                }

                String attrChapterWorld = (((Element) document.getElementsByTagName("chapter").item(i))).getAttributeNode("world").getValue();
                String chapterWorldSM;
                if (attrChapterWorld.equals("")) {
                    chapterWorldSM = null;
                } else {
                    chapterWorldSM = attrChapterWorld;
                }

                SpaceMarine sm = new SpaceMarine(nameSM, new Coordinates(xSM, ySM), healthSM, categorySM, weaponSM, meleeWeaponSM, new Chapter(chapterNameSM, chapterWorldSM));
                collectionPutter.put(sm);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Некорректное содержимой файла с данными.\n" +
                    "Дальнейшее заполнение коллекции из данного источника невозможно.\n" +
                    "Продолжите заполнять коллекцию вручную или перезапустите программу, указав корректный файл.");
        }
    }
}