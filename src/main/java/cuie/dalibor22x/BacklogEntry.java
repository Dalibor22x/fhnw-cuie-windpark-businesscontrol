package cuie.dalibor22x;

import java.time.LocalTime;

public class BacklogEntry {

    private String project;
    private LocalTime time;

    public BacklogEntry(String project, LocalTime time) {
        this.project = project;
        this.time = time;
    }

    public String getProject() {
        return project;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean equals (BacklogEntry e) {
        if (e.project == null || e.time == null) return false;

        return e.project.equals(project) && e.time.equals(time);
    }
}
