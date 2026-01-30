
package org.tracker.entity;

public class Application {

    private int id;
    private String company;
    private String position;
    private String status;

    public Application(String company, String position, String status) {
        this.company = company;
        this.position = position;
        this.status = status;
    }
    public Application(int id, String company, String position, String status) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
    }



    public String getCompany() { return company; }
    public String getPosition() { return position; }
    public String getStatus() { return status; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
