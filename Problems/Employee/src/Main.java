class Employee {

    String name;
    int salary;
    String address;

    Employee() {
        this("unknown", 0);
    }

    Employee(String name, int salary) {
        this(name, salary, "unknown");
    }

    Employee(String name, int salary, String address) {
        this.name = name;
        this.salary = salary;
        this.address = address;
    }
}