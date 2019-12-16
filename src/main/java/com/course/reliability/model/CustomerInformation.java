package com.course.reliability.model;

public class CustomerInformation {
    private Integer id;
    private Integer age;
    private Double salary;
    private Integer realEstate;
    private Double debt;
    private Double elec;
    private Double gas;
    private Double reliability;

    public CustomerInformation() {
    }

    public CustomerInformation(Integer id, Integer age, Double salary, Integer realEstate, Double debt, Double elec, Double gas, Double reliability) {
        this.id = id;
        this.age = age;
        this.salary = salary;
        this.realEstate = realEstate;
        this.debt = debt;
        this.elec = elec;
        this.gas = gas;
        this.reliability = reliability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(Integer realEstate) {
        this.realEstate = realEstate;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Double getElec() {
        return elec;
    }

    public void setElec(Double elec) {
        this.elec = elec;
    }

    public Double getGas() {
        return gas;
    }

    public void setGas(Double gas) {
        this.gas = gas;
    }

    public Double getReliability() {
        return reliability;
    }

    public void setReliability(Double reliability) {
        this.reliability = reliability;
    }
}
