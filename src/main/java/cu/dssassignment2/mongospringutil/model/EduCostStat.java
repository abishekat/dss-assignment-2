package cu.dssassignment2.mongospringutil.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EduCostStat")
public class EduCostStat {
    private String id;
    private int year;
    private String state;
    private String type;

    public EduCostStat(String id, int year, String state, String type, String length, String expense, double value) {
        this.id = id;
        this.year = year;
        this.state = state;
        this.type = type;
        this.length = length;
        this.expense = expense;
        this.value = value;
    }

    private String length;
    private String expense;
    private double value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EduCostStat{" +
                "id='" + id + '\'' +
                ", year=" + year +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                ", length='" + length + '\'' +
                ", expense='" + expense + '\'' +
                ", value=" + value +
                '}';
    }
}
