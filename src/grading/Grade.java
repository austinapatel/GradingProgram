package grading;

public class Grade {
    private double totalPoints;
    private double points;

    public Grade(double totalPoints, double points) {
        this.totalPoints = totalPoints;
        this.points = points;
    }

    public double getPercentage() {
        return points / totalPoints * 100;
    }

    public String getLetterGrade(GradingScale scale) {
        return scale.getGradeLetter(getPercentage());
    }

    public double getPoints() {
        return points;
    }

    public double getTotalPoints() {

        return totalPoints;
    }
}

