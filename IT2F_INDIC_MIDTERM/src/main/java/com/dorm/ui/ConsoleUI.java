package com.dorm.ui;

import com.dorm.dao.DaoException;
import com.dorm.dao.ScenarioDao;
import com.dorm.model.Allocation;

import java.util.List;
import java.util.Scanner;

/** Console menu loop. Talks to the database only through ScenarioDao. */
public class ConsoleUI {
    private static final String LINE = "=".repeat(54);

    private final ScenarioDao dao;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(ScenarioDao dao) {
        this.dao = dao;
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Enter choice: ");
            switch (choice) {
                case "1" -> addAllocation();
                case "2" -> viewAllocations();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nERROR: Invalid choice. Please enter 1, 2, or 3.\n");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println(LINE);
        System.out.println("       STUDENT DORMITORY BED ALLOCATION");
        System.out.println(LINE);
        System.out.println("[1] Add New Allocation");
        System.out.println("[2] View All Allocations");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
    }

    private void addAllocation() {
        System.out.println("\n--- ADD NEW BED ALLOCATION ---");
        String name = readRequired("Enter Student Name: ");
        String building = readRequired("Enter Building Name: ");
        String room = readRequired("Enter Room Number: ");
        double rent = readRent("Enter Monthly Rent: ");

        try {
            dao.insert(new Allocation(name, building, room, rent));
            System.out.println("\nSUCCESS: Dorm bed allocated successfully!\n");
        } catch (DaoException e) {
            System.out.println("\nERROR: " + e.getMessage() + "\n");
        }
    }

    private void viewAllocations() {
        List<Allocation> list;
        try {
            list = dao.findAll();
        } catch (DaoException e) {
            System.out.println("\nERROR: " + e.getMessage() + "\n");
            return;
        }

        System.out.println();
        if (list.isEmpty()) {
            System.out.println("No records found.\n");
            return;
        }

        // Column widths grow with the data so the table stays aligned.
        int wId = "ID".length(), wName = "STUDENT NAME".length(), wBld = "BUILDING".length(),
            wRoom = "ROOM NO".length(), wRent = "MONTHLY RENT".length();
        for (Allocation a : list) {
            wId = Math.max(wId, String.valueOf(a.getId()).length());
            wName = Math.max(wName, a.getStudentName().length());
            wBld = Math.max(wBld, a.getBuilding().length());
            wRoom = Math.max(wRoom, a.getRoomNo().length());
            wRent = Math.max(wRent, String.format("%.2f", a.getMonthlyRent()).length());
        }

        String border = "+" + "-".repeat(wId + 2) + "+" + "-".repeat(wName + 2) + "+"
                + "-".repeat(wBld + 2) + "+" + "-".repeat(wRoom + 2) + "+"
                + "-".repeat(wRent + 2) + "+";
        String rowFmt = "| %-" + wId + "s | %-" + wName + "s | %-" + wBld + "s | %-"
                + wRoom + "s | %-" + wRent + "s |%n";

        System.out.println("DORMITORY BED ALLOCATIONS");
        System.out.println(border);
        System.out.printf(rowFmt, "ID", "STUDENT NAME", "BUILDING", "ROOM NO", "MONTHLY RENT");
        System.out.println(border);
        for (Allocation a : list) {
            System.out.printf(rowFmt, a.getId(), a.getStudentName(), a.getBuilding(),
                    a.getRoomNo(), String.format("%.2f", a.getMonthlyRent()));
        }
        System.out.println(border);
        System.out.println("Total Records Displayed: " + list.size() + "\n");
    }

    /** Reads one full line (avoids the nextInt/nextLine newline problem). */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return "3"; // input closed: exit cleanly
        }
        return scanner.nextLine().trim();
    }

    private String readRequired(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("ERROR: This field cannot be empty.");
        }
    }

    private double readRent(String prompt) {
        while (true) {
            String raw = readLine(prompt).replace(",", "");
            try {
                double value = Double.parseDouble(raw);
                if (Double.isFinite(value) && value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through to the error message
            }
            System.out.println("ERROR: Enter a valid positive number (e.g., 3500.00).");
        }
    }
}
