package manjinder.traintracks;

/**
 * Created by Manjinder_Singh on 30-Aug-17.
 */
public class Seat {
    String status;
    String date;

    public Seat(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
