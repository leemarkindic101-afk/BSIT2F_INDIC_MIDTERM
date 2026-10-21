package com.dorm.model;

/** One dormitory bed allocation (resident record). */
public class Allocation {
    private int id;
    private String studentName;
    private String building;
    private String roomNo;
    private double monthlyRent;

    public Allocation(String studentName, String building, String roomNo, double monthlyRent) {
        this(0, studentName, building, roomNo, monthlyRent);
    }

    public Allocation(int id, String studentName, String building, String roomNo, double monthlyRent) {
        this.id = id;
        this.studentName = studentName;
        this.building = building;
        this.roomNo = roomNo;
        this.monthlyRent = monthlyRent;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public double getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(double monthlyRent) { this.monthlyRent = monthlyRent; }
}
