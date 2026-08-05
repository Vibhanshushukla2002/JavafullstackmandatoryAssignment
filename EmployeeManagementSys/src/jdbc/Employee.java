package jdbc;

import java.sql.Date;

public class Employee {

	private int eno;
	private String ename;
	private double salary;
	private String dept;
	private String gender;
	private Date dob;

	// Default Constructor
	public Employee() {

	}

	// Parameterized Constructor
	public Employee(int eno, String ename, double salary, String dept, String gender, Date dob) {
		this.eno = eno;
		this.ename = ename;
		this.salary = salary;
		this.dept = dept;
		this.gender = gender;
		this.dob = dob;
	}

	// Getters and Setters

	public int getEno() {
		return eno;
	}

	public void setEno(int eno) {
		this.eno = eno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Employee [eno=" + eno + ", ename=" + ename + ", salary=" + salary + ", dept=" + dept
				+ ", gender=" + gender + ", dob=" + dob + "]";
	}
}