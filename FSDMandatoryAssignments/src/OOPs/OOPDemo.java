package OOPs;

//Interface
interface GPS {
 void showLocation();
}

//Abstract Class
abstract class Vehicle {
 String brand;
 int speed;

 // Constructor
 Vehicle(String brand, int speed) {
     this.brand = brand;
     this.speed = speed;
 }

 // Concrete Method
 void displayInfo() {
     System.out.println("Brand : " + brand);
     System.out.println("Speed : " + speed + " km/h");
 }

 // Abstract Method
 abstract void start();
}

//Inheritance
class Car extends Vehicle implements GPS {

 // Constructor
 Car(String brand, int speed) {
     super(brand, speed);
 }

 // Method Overriding
 @Override
 void start() {
     System.out.println("Car starts using Push Button.");
 }

 // Interface Method
 @Override
 public void showLocation() {
     System.out.println("Current Location : Delhi");
 }

 // Method Overloading
 void fuel() {
     System.out.println("Fuel Tank is Full");
 }

 void fuel(int litres) {
     System.out.println("Added " + litres + " litres of fuel.");
 }
}

public class OOPDemo {

 public static void main(String[] args) {

     // Class and Object
     Car c1 = new Car("Toyota", 180);

     c1.displayInfo();

     c1.start();

     c1.showLocation();

     c1.fuel();
     c1.fuel(20);
 }
}