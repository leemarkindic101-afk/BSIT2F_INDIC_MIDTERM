package com.dorm;

import com.dorm.dao.DaoException;
import com.dorm.dao.ScenarioDao;
import com.dorm.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        try {
            new ConsoleUI(new ScenarioDao()).start();
        } catch (DaoException e) {
            System.out.println("FATAL: " + e.getMessage());
        }
    }
}
