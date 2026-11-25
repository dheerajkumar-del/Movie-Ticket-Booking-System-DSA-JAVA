
import java.util.*;

class Movie {
    int id;
    String name;
    double price;

    Movie(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class Booking {
    int bookingId;
    String movieName;
    int seats;
    double amount;

    Booking(int bookingId, String movieName,
            int seats, double amount) {
        this.bookingId = bookingId;
        this.movieName = movieName;
        this.seats = seats;
        this.amount = amount;
    }

    public String toString() {
        return "Booking ID: " + bookingId +
               " | Movie: " + movieName +
               " | Seats: " + seats +
               " | Amount: Rs." + amount;
    }
}

public class MovieTicketBooking {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Movie> movies = new ArrayList<>();
    static HashMap<Integer, Booking> bookings = new HashMap<>();
    static Stack<Booking> history = new Stack<>();
    static Queue<String> waitingList = new LinkedList<>();

    static boolean[][] seats = new boolean[5][5];

    static int bookingCounter = 1001;

    static void loadMovies() {
        movies.add(new Movie(1, "Avengers", 200));
        movies.add(new Movie(2, "Inception", 180));
        movies.add(new Movie(3, "Interstellar", 220));
        movies.add(new Movie(4, "Jawan", 150));
        movies.add(new Movie(5, "3 Idiots", 120));
    }

    static void showMovies() {
        System.out.println("\n------ AVAILABLE MOVIES ------");

        for (Movie m : movies) {
            System.out.println(
                m.id + ". " + m.name +
                " - Rs." + m.price
            );
        }
    }

    // Linear Search
    static void searchMovie() {
        sc.nextLine();
        System.out.print("Enter movie name: ");
        String name = sc.nextLine();

        boolean found = false;

        for (Movie m : movies) {
            if (m.name.equalsIgnoreCase(name)) {
                System.out.println("Movie Found!");
                System.out.println("Name: " + m.name);
                System.out.println("Price: Rs." + m.price);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Movie not found!");
        }
    }

    // Bubble Sort
    static void sortMovies() {
        for (int i = 0; i < movies.size() - 1; i++) {
            for (int j = 0; j < movies.size() - i - 1; j++) {

                if (movies.get(j).price >
                    movies.get(j + 1).price) {

                    Movie temp = movies.get(j);
                    movies.set(j, movies.get(j + 1));
                    movies.set(j + 1, temp);
                }
            }
        }

        System.out.println("\nMovies sorted by price!");
        showMovies();
    }

    static void showSeats() {
        System.out.println("\n------ SEAT LAYOUT ------");
        System.out.println("O = Available | X = Booked");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(
                    seats[i][j] ? " X " : " O "
                );
            }
            System.out.println();
        }
    }

    // Ticket Booking
    static void bookTicket() {
        showMovies();

        System.out.print("\nEnter Movie ID: ");
        int id = sc.nextInt();

        Movie selected = null;

        for (Movie m : movies) {
            if (m.id == id) {
                selected = m;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Invalid Movie ID!");
            return;
        }

        showSeats();

        System.out.print("Enter number of seats (1-25): ");
        int count = sc.nextInt();

        if (count < 1 || count > 25) {
            System.out.println("Invalid seat count!");
            return;
        }

        int[][] selectedSeats = new int[count][2];
        int booked = 0;

        while (booked < count) {
            System.out.print("Enter row (1-5): ");
            int row = sc.nextInt() - 1;

            System.out.print("Enter column (1-5): ");
            int col = sc.nextInt() - 1;

            if (row < 0 || row >= 5 ||
                col < 0 || col >= 5) {
                System.out.println("Invalid seat!");
                continue;
            }

            if (seats[row][col]) {
                System.out.println("Seat already booked!");
                continue;
            }

            boolean duplicate = false;

            for (int i = 0; i < booked; i++) {
                if (selectedSeats[i][0] == row &&
                    selectedSeats[i][1] == col) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("Seat already selected!");
                continue;
            }

            selectedSeats[booked][0] = row;
            selectedSeats[booked][1] = col;
            booked++;
        }

        double total = selected.price * count;

        for (int i = 0; i < count; i++) {
            seats[selectedSeats[i][0]][selectedSeats[i][1]] = true;
        }

        Booking b = new Booking(
            bookingCounter++,
            selected.name,
            count,
            total
        );

        bookings.put(b.bookingId, b);
        history.push(b);

        System.out.println("\n------ BOOKING SUCCESSFUL ------");
        System.out.println(b);
        System.out.println("Thank you for booking!");
    }

    // Cancel Booking
    static void cancelBooking() {
        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();

        Booking b = bookings.remove(id);

        if (b == null) {
            System.out.println("Booking not found!");
            return;
        }

        // Note: This basic version removes booking
        // details; seat restoration is not included.
        System.out.println("Booking cancelled: " + b);
    }

    static void showHistory() {
        System.out.println("\n------ BOOKING HISTORY ------");

        if (bookings.isEmpty()) {
            System.out.println("No active bookings!");
            return;
        }

        for (Booking b : bookings.values()) {
            System.out.println(b);
        }
    }

    public static void main(String[] args) {

        loadMovies();

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("     MOVIE TICKET BOOKING");
            System.out.println("================================");
            System.out.println("1. View Movies");
            System.out.println("2. Search Movie");
            System.out.println("3. Sort Movies by Price");
            System.out.println("4. View Seats");
            System.out.println("5. Book Ticket");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Booking History");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    showMovies();
                    break;

                case 2:
                    searchMovie();
                    break;

                case 3:
                    sortMovies();
                    break;

                case 4:
                    showSeats();
                    break;

                case 5:
                    bookTicket();
                    break;

                case 6:
                    cancelBooking();
                    break;

                case 7:
                    showHistory();
                    break;

                case 8:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 8);

        sc.close();
    }
}
