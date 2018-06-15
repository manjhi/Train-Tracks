package manjinder.traintracks;

/**
 * Created by Manjinder_Singh on 02-Sep-17.
 */
public class Stations {
    String number;
    String name;
    String ar;
    String depa;

    public Stations(String number, String name, String ar, String depa) {
        this.number = number;
        this.name = name;
        this.ar = ar;
        this.depa = depa;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getDepa() {
        return depa;
    }

    public void setDepa(String depa) {
        this.depa = depa;
    }
}
