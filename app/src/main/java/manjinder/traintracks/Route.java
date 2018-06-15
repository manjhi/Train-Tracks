package manjinder.traintracks;

/**
 * Created by Manjinder_Singh on 30-Aug-17.
 */
public class Route {
    String sta_name;
    String dep;
    String arr;


    public Route(String sta_name, String dep, String arr) {
        this.sta_name = sta_name;
        this.dep = dep;
        this.arr = arr;
    }


    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }
}
