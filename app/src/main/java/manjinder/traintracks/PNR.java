package manjinder.traintracks;

/**
 * Created by Manjinder_Singh on 12-Sep-17.
 */
public class PNR {
    String No;
    String current;
    String booking;
    String coach;

    public PNR(String no, String current, String booking, String coach) {
        No = no;
        this.current = current;
        this.booking = booking;
        this.coach = coach;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
