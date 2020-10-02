package mentoring.entities;

public class UserBody {
    private String name;
    private String job;

    public UserBody(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public UserBody() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
