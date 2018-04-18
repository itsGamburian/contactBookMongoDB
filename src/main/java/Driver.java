/********************
 Hambartzum Gamburian
 Driver.java
 **********************/

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Database db = new Database();
        db.loadFromDBinit();

        Scanner input = new Scanner(System.in);
        String cmd;

        System.out.println("Welcome to Contact Book.\n");
        cmd = "";

         //If quit is entered then the loop is ended
        while (!cmd.equals("quit")) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Enter a command: add, show all, find name, find phone, find comment, remove name, remove phone, remove comment, mongodb settings, or quit");
            cmd = input.nextLine();

            //Allowing user to add a contact
            if (cmd.toLowerCase().contains("add")) {
                System.out.println("Name?");
                String name = input.nextLine();
                System.out.println("Phone Number?");
                long number = input.nextLong();
                input.nextLine();
                System.out.println("Comment?");
                String comment = input.nextLine();
                db.addContact(name, number, comment);
            }

            //Allowing user to find a contact
            if (cmd.toLowerCase().contains("find")) {

                if (cmd.toLowerCase().contains("name")) {
                    cmd = cmd.replaceAll("find name", "");
                    cmd = cmd.trim();
                    System.out.println(cmd);
                    if (cmd.length() > 0){
                        db.showByName(cmd);
                    }
                    else {
                        unrecognized();
                    }
                }

                else if (cmd.toLowerCase().contains("phone")) {
                    cmd = cmd.replaceAll("find phone ", "");
                    cmd = cmd.trim();
                    if (cmd.length() > 0){
                        long pN = Long.valueOf(cmd);
                        db.showByNumber(pN);
                    }
                    else {
                        unrecognized();
                    }
                }

                else if (cmd.toLowerCase().contains("comment")) {
                    cmd = cmd.replaceAll("find comment ", "");
                    cmd = cmd.trim();
                    if (cmd.length() > 0){
                        db.showByComment(cmd);
                    }
                    else {
                        unrecognized();
                    }
                }

                else {
                    cmd = "";
                    unrecognized();
                }
            }

            //Allowing user to remove a contact
            if (cmd.toLowerCase().contains("remove")) {

                if (cmd.toLowerCase().contains("name")) {
                    cmd = cmd.replaceAll("remove name ", "");
                    cmd = cmd.trim();
                    if (cmd.length() > 0){
                        db.removeContactByName(cmd);
                    }
                    else {
                        unrecognized();
                    }
                }

                else if (cmd.toLowerCase().contains("phone")) {
                    cmd = cmd.replaceAll("remove phone ", "");
                    cmd = cmd.trim();
                    if (cmd.length() > 0){
                        long pN = Long.valueOf(cmd);
                        db.removeContactByNumber(pN);
                    }
                    else {
                        unrecognized();
                    }
                }

                else if (cmd.toLowerCase().contains("comment")) {
                    cmd = cmd.replaceAll("remove comment ", "");
                    cmd = cmd.trim();
                    if (cmd.length() > 0){
                        db.removeContactByComment(cmd);
                    }
                    else {
                        unrecognized();
                    }
                }

                else {
                    cmd = "";
                    unrecognized();
                }
            }

            if (cmd.toLowerCase().contains("show all")) {
                db.printAll();
            }

            //MongoDB settings
            if (cmd.toLowerCase().contains("mongodb settings")) {
                while (!cmd.equals("return menu")) {
                    System.out.println("Enter a command: create new collection, drop a collection, remove all data in current collection, or return menu");
                    cmd = input.nextLine();
                    if (cmd.toLowerCase().equals("create new collection")) {
                        db.createNewMongoCollection();
                    }
                    else if (cmd.toLowerCase().equals("drop a collection")) {
                        db.dropCurrentMongoCollection();
                    }
                    else if (cmd.toLowerCase().equals("remove all data in current collection")) {
                        db.removeAllData();
                    }
                }
            }

        }//End of loop

    } //End of main method

    public static void unrecognized() {
        System.out.println("Unrecognized command, please enter a recognized command.");
    }

} //End of class