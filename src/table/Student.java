// Austin Patel
// 4/8/2017
// Student.java

package table;

/**
 * Stores database information for a student
 */
public class Student {

    private String firstName, lastName;

    private int studentId, counselorId, graduationYear;
    private char gender;
    private Date birthday;

    public Student(String firstName, String lastName, int studentId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String toString() {
        return "First Name: " + firstName + "\n" + "Last Name: " + lastName + "\n"
                + "Student ID: " + studentId + "\n" + "Gender: " + ((gender == ' ') ? "Not specified" : gender) + "\n" +
                "Birthdate: " + ((birthday == null) ? "Not specified" : birthday) +
                "\n" + "Graduation Year: " + ((graduationYear == 0) ? "Not specified" : graduationYear);
    }

    public int getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(int counselorId) {
        this.counselorId = counselorId;
    }
}
