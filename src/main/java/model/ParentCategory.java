package model;

public class ParentCategory {
    int number;
    int sortNumber;
    String name;

    public ParentCategory(int number, int sortNumber, String name) {
        this.number = number;
        this.sortNumber = sortNumber;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
        return "ParentCategory{" +
                "number=" + number +
                ", sortNumber=" + sortNumber +
                ", name='" + name + '\'' +
                '}';
    }
}
