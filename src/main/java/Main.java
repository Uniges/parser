import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

class Main {

    private static final String URL = "jdbc:mysql://46.30.40.103/vh198200_magazin";
    private static final String USERNAME = "vh198200_admin";
    private static final String PASSWORD = "password";
    private static Connection connection;
    private static Statement statement;
    private static String stringOfChars = "";
    private static List<ParentCategory> pCat = new ArrayList<>();
    private static List<ChildrenCategory> chCat = new ArrayList<>();
    private static List<GrandChildrenCategory> grChCat = new ArrayList<>();
    private static int num = 75; //Первое число для итерации по номеру в базе
    private static int sortNumP = 6; //Число для сортировки категорий - родителей
    private static int sortNumCh = 0; //Число для сортировки категорий - детей
    private static int sortNumGrCh = 0; //Число для сортировки категорий - внуков


    public static void main(String[] args) {

        readFile();
        checkForParentAndChild();
        checkForGrandChild();
        /*viewAllCategories();*/
        try {
            createConnection();
            statement = connection.createStatement();
            startAddParentCategory();
            startAddChildrenCategory();
            startAddGrandChildrenCategory();
            startExecute();
            closeConnection();
        } catch (SQLException e) {
            System.out.println("Не удалось создать соединение!(statement)!");
        }
    }


    public static void viewAllCategories() {
        System.out.println("Родители:");
        for (ParentCategory parent : pCat) {
            System.out.println(parent);
        }
        System.out.println("Дети:");
        for (ChildrenCategory children : chCat) {
            System.out.println(children);
        }
        System.out.println("Внуки:");
        for (GrandChildrenCategory grandchildren : grChCat) {
            System.out.println(grandchildren);
        }
    }

    public static void readFile() {
        FileReader fileReader = null;
        try {
            //Путь к директории с файлом
            fileReader = new FileReader("path");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден!");
        }
        try {
            while (fileReader.ready()) {
                stringOfChars+=(char)fileReader.read();
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения из файла!");
        }
        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Ошибка закрытия потока работы с файлом!");
        }
    }

    public static void checkForParentAndChild() {
        String[] strArr = stringOfChars.split(System.lineSeparator());
        for (String s : strArr) {
            if (!s.contains("/")) {
                pCat.add(new ParentCategory(num, sortNumP, s));
                num++;
                sortNumP++;
            }
            else if (s.contains("/")){
                String[] tempArr = s.split("/");
                pCat.add(new ParentCategory(num, sortNumP, tempArr[0]));
                num++;
                sortNumP++;
                int grChNum = num;
                for (int i = 1; i < tempArr.length; i++) {
                    chCat.add(new ChildrenCategory(num, num - i, sortNumCh, tempArr[i]));
                    sortNumCh++;
                    num++;
                }
                sortNumCh = 0;
            }
        }
    }

    public static void checkForGrandChild() {
        for (ChildrenCategory child : chCat) {
            if (child.getName().contains("#")) {
                String[] tempArray = child.getName().split("#");
                child.setName(tempArray[0]);
                for (int i = 1; i < tempArray.length; i++) {
                    grChCat.add(new GrandChildrenCategory(num, child.getNumber(), child.getParentNumber(), sortNumGrCh, tempArray[i]));
                    num++;
                    sortNumGrCh++;
                }
                sortNumGrCh = 0;
            }
        }
    }

    public static void createConnection() {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить класс драйвера!");
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено!");
            }
        } catch (SQLException e) {
            System.out.println("Соединение с БД не может быть установлено!");
        }
    }

    public static void startExecute() {
        try {
            statement.executeBatch();
        } catch (SQLException e) {
            System.out.println("Ошибка во время выполнения группы созданных команд в БД!");
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Соединение с БД не может быть закрыто!");
        }
    }

    public static void startAddParentCategory() {
        for (ParentCategory parent : pCat) {
            try {
                statement.addBatch("INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `image2`, `category_icontype`, `category_icon`, `category_image`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) VALUES ('" + parent.getNumber() + "', '', '0', '', '0', 'fa none', 'no_image.png', '1', '1', '" + parent.getSortNumber() + "', '1', '2017-09-30 00:00:00', '2017-09-30 00:00:00');");
                statement.addBatch("INSERT INTO `oc_category_description` (`category_id`, `language_id`, `name`, `description`, `meta_title`, `meta_description`, `meta_keyword`) VALUES ('" + parent.getNumber() + "', '1', '" + parent.getName() + "', '', '" + parent.getName() + "', '', '');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + parent.getNumber() + "', '" + parent.getNumber() + "', '1');");
                statement.addBatch("INSERT INTO `oc_category_to_layout` (`category_id`, `store_id`, `layout_id`) VALUES ('" + parent.getNumber() + "', '0', '0');");
                statement.addBatch("INSERT INTO `oc_category_to_store` (`category_id`, `store_id`) VALUES ('" + parent.getNumber() + "', '0');");
            } catch (SQLException e) {
                System.out.println("Ошибка создания sql-команд (parent)!");
            }
        }
    }

    public static void startAddChildrenCategory() {
        for (ChildrenCategory child : chCat) {
            try {
                statement.addBatch("INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `image2`, `category_icontype`, `category_icon`, `category_image`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) VALUES ('" + child.getNumber() + "', '', '" + child.getParentNumber() + "', '', '0', 'fa none', 'no_image.png', '0', '1', '" + child.getSortNumber() + "', '1', '2017-09-30 00:00:00', '2017-09-30 00:00:00');");
                statement.addBatch("INSERT INTO `oc_category_description` (`category_id`, `language_id`, `name`, `description`, `meta_title`, `meta_description`, `meta_keyword`) VALUES ('" + child.getNumber() + "', '1', '" + child.getName() + "', '', '" + child.getName() + "', '', '');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + child.getNumber() + "', '" + child.getNumber() + "', '1');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + child.getNumber() + "', '" + child.getParentNumber() + "', '0');");
                statement.addBatch("INSERT INTO `oc_category_to_layout` (`category_id`, `store_id`, `layout_id`) VALUES ('" + child.getNumber() + "', '0', '0');");
                statement.addBatch("INSERT INTO `oc_category_to_store` (`category_id`, `store_id`) VALUES ('" + child.getNumber() + "', '0');");
            } catch (SQLException e) {
                System.out.println("Ошибка создания sql-команд (children)!");
            }
        }
    }

    public static void startAddGrandChildrenCategory() {
        for (GrandChildrenCategory grandchild : grChCat) {
            try {
                statement.addBatch("INSERT INTO `oc_category` (`category_id`, `image`, `parent_id`, `image2`, `category_icontype`, `category_icon`, `category_image`, `top`, `column`, `sort_order`, `status`, `date_added`, `date_modified`) VALUES ('" + grandchild.getNumber() + "', '', '" + grandchild.getParentNumber() + "', '', '0', 'fa none', 'no_image.png', '0', '1', '" + grandchild.getSortNumber() + "', '1', '2017-09-30 00:00:00', '2017-09-30 00:00:00');");
                statement.addBatch("INSERT INTO `oc_category_description` (`category_id`, `language_id`, `name`, `description`, `meta_title`, `meta_description`, `meta_keyword`) VALUES ('" + grandchild.getNumber() + "', '1', '" + grandchild.getName() + "', '', '" + grandchild.getName() + "', '', '');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + grandchild.getNumber() + "', '" + grandchild.getNumber() + "', '2');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + grandchild.getNumber() + "', '" + grandchild.getParentNumber() + "', '1');");
                statement.addBatch("INSERT INTO `oc_category_path` (`category_id`, `path_id`, `level`) VALUES ('" + grandchild.getNumber() + "', '" + grandchild.getGrandParentNumber() + "', '0');");
                statement.addBatch("INSERT INTO `oc_category_to_layout` (`category_id`, `store_id`, `layout_id`) VALUES ('" + grandchild.getNumber() + "', '0', '0');");
                statement.addBatch("INSERT INTO `oc_category_to_store` (`category_id`, `store_id`) VALUES ('" + grandchild.getNumber() + "', '0');");
            } catch (SQLException e) {
                System.out.println("Ошибка создания sql-команд (grandchildren)!");
            }
        }
    }
}