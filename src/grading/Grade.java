package grading;

public class Grade {
    private double totalPoints;
    private double points;
    private double percent;
    private String letterGrade, firstname, lastname;
    private int studentId;

    public Grade(double totalPoints, double points, int studentId) {
        this.totalPoints = totalPoints;
        this.points = points;
        this.studentId = studentId;
    }

    public double getPercentage() {
        return points / totalPoints * 100;
    }

    public String getLetterGrade(GradingScale scale) {
        return scale.getGradeLetter(getPercentage());
    }

    public int getStudentId() {
        return studentId;
    }

    public double getPoints() {
        return points;
    }

    public double getTotalPoints() {

        return totalPoints;
    }
}

