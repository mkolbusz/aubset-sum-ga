/**
 * Created by kolbusz on 18.04.15.
 */
public class Gene {
    private Boolean activity;

    public Gene(Boolean activity) {
        this.setActivity(activity);
    }

    public Boolean isActive() {
        return activity;
    }

    public void setActivity(Boolean activity) {
        this.activity = activity;
    }

    public void changeActivity(){
        this.setActivity(!this.isActive());
    }

    @Override
    public String toString() {
        return String.valueOf(activity);
    }
}
