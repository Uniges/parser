package model;

public class GrandChildrenCategory {
    int number;
    int parentNumber;
    int grandParentNumber;
    int sortNumber;
    String name;

    public GrandChildrenCategory(int number, int parentNumber, int grandParentNumber, int sortNumber, String name) {
        this.number = number;
        this.parentNumber = parentNumber;
        this.grandParentNumber = grandParentNumber;
        this.sortNumber = sortNumber;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(int parentNumber) {
        this.parentNumber = parentNumber;
    }

    public int getGrandParentNumber() {
        return grandParentNumber;
    }

    public void setGrandParentNumber(int grandParentNumber) {
        this.grandParentNumber = grandParentNumber;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GrandChildrenCategory{" +
                "number=" + number +
                ", parentNumber=" + parentNumber +
                ", grandParentNumber=" + grandParentNumber +
                ", sortNumber=" + sortNumber +
                ", name='" + name + '\'' +
                '}';
    }
}