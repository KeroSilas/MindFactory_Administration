package group3.mindfactory_administration.model;

public class Customer {

    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private String phone;
    private String email;

    public Customer(String firstName, String lastName, String position, String department, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName= firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName= lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position= position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department= department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone= phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }
}
