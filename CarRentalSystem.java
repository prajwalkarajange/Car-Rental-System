import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class CarRentalSystem {

    private List<Car> cars;

    private List<Customer> customers;

    private List<Rental> rentals;

    Scanner sc = new Scanner(System.in);

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent...");
        }

    }

    public void returnCar(Car car) {

        Rental rentalToRemove = null;

        for (Rental rental : rentals) {

            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }

        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            car.returnCar();

        } else {
            System.out.println("Car was not rented..!");
        }
    }

    public void menu() {

        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume new Line

            if (choice == 1) {

                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("Available Cars: ");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }

                }

                System.out.print("Enter a car ID you want to rent: ");
                String carId = sc.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();

                sc.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;

                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }

                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.claculatePrise(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("Confirm rental (Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully..!");
                    } else {
                        System.out.println("\nRental canceled. ");

                    }

                } else {
                    System.out.println("Invalid car selection or Car not available for rent.");
                }

            } else if (choice == 2) {

                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }

                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully By: " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing..");
                    }

                } else {
                    System.out.println("Invalid car ID or car is not rented");
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid option. Please enter a valid option");
            }
        }
        System.out.println("\n Thank you for using the car Rental System..!");

    }
}
