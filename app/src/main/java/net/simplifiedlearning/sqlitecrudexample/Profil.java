package net.simplifiedlearning.sqlitecrudexample;

/**
 * Created by AMRI on 10/30/2019.
 */

public class Profil {
    int id;
    String name, dept, joiningDate;
    String salary, cin , adresse , telephone;

    public Profil(int id, String name, String dept, String joiningDate, String salary) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.joiningDate = joiningDate;
        this.salary = salary;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getSalary() {
        return salary;
    }

    public String getCin() {return  cin;}

    public  String getTelephone() {return  telephone; }


}
