package model;

public class ChildrenCategory {
    int number;
    int parentNumber;
    int sortNumber;
    String name;

    public ChildrenCategory(int number, int parentNumber, int sortNumber, String name) {
        this.number = number;
        this.parentNumber = parentNumber;
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
        return "ChildrenCategory{" +
                "number=" + number +
                ", parentNumber=" + parentNumber +
                ", sortNumber=" + sortNumber +
                ", name='" + name + '\'' +
                '}';
    }
}