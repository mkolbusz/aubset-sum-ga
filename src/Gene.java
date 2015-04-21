/**
 * Created by kolbusz on 18.04.15.
 */
public class Gene {
    private Integer activity;

    public Gene(Integer activity) {
        this.setActivity(activity);
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity > 0 ? 1 : 0;
    }

    public void changeActivity(){
        this.setActivity(this.getActivity() == 1 ? 0 : 1);
    }

    @Override
    public String toString() {
        return String.valueOf(activity);
    }
}
