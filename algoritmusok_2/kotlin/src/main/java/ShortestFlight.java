import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

enum State {
    UNVISITED, VISITING, VISITED
}

//EDGE
class Flight {
    private final int departureTimeOnDayInMins;
    private final int flightDuration;
    private final Airport departure;
    private final Airport arrival;

    public Flight(int departureTimeOnDayInMins, int flightDuration, Airport departure, Airport arrival) {
        this.departureTimeOnDayInMins = departureTimeOnDayInMins;
        this.flightDuration = flightDuration;
        this.departure = departure;
        this.arrival = arrival;
    }

    public int getDepartureTimeOnDayInMins() {
        return departureTimeOnDayInMins;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public Airport getDeparture() {
        return departure;
    }

    public Airport getArrival() {
        return arrival;
    }

}

//NODE
class Airport implements Comparable<Airport> {
    private final int name;
    private int flightTimeFromStart = Integer.MAX_VALUE;
    private State state = State.UNVISITED;
    private Airport predecessor;
    private final List<Flight> flightsTo;

    public Airport(int name) {
        this.name = name;
        this.flightsTo = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flightsTo.add(flight);
    }

    @Override
    public int compareTo(Airport o) {
        return Integer.compare(flightTimeFromStart, o.flightTimeFromStart);
    }

    public int getName() {
        return name;
    }

    public int getFlightTimeFromStart() {
        return flightTimeFromStart;
    }

    public void setFlightTimeFromStart(int flightTimeFromStart) {
        this.flightTimeFromStart = flightTimeFromStart;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Airport getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Airport predecessor) {
        this.predecessor = predecessor;
    }

    public List<Flight> getFlightsTo() {
        return flightsTo;
    }

}


public class ShortestFlight {

    private static final int DAY_MINS = 1440;

    private Airport[] airports;
    private Airport departure;
    private Airport arrival;
    private PriorityQueue<Airport> pq;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int countOfCities = scanner.nextInt();
        int directFlights = scanner.nextInt();
        int departureIndex = scanner.nextInt();
        int arrivalIndex = scanner.nextInt();

        Airport[] airports = new Airport[countOfCities + 1];
        Airport departure = new Airport(departureIndex);
        Airport arrival = new Airport(arrivalIndex);
        airports[departureIndex] = departure;
        airports[arrivalIndex] = arrival;

        for (int i = 0; i < directFlights; i++) {
            int departureFrom = scanner.nextInt();
            int arriveAt = scanner.nextInt();
            int departureTimeInMins = scanner.nextInt();
            int flightDuration = scanner.nextInt();

            Airport from = airports[departureFrom];
            Airport to = airports[arriveAt];
            if (from == null) {
                from = new Airport(departureFrom);
                airports[departureFrom] = from;
            }
            if (to == null) {
                to = new Airport(arriveAt);
                airports[arriveAt] = to;
            }

            Flight flight = new Flight(departureTimeInMins, flightDuration, from, to);
            from.addFlight(flight);
        }

        ShortestFlight shortestFlight = new ShortestFlight(airports, departure, arrival);
        shortestFlight.calculateFastestPath();

    }

    public ShortestFlight(Airport[] airports, Airport departure, Airport arrival) {
        this.airports = airports;
        this.departure = departure;
        this.arrival = arrival;
        this.pq = new PriorityQueue<>();
    }

    public void calculateFastestPath() {
        departure.setFlightTimeFromStart(0);
        pq.add(departure);
        while (!pq.isEmpty() && arrival.getState() != State.VISITED) {
            Airport start = pq.poll();
            start.getFlightsTo().forEach(flight -> {
                Airport target = flight.getArrival();
                target.setState(State.VISITING);
                int flightDuration = calculateDurationToNextStation(start, flight);
                if (flightDuration < target.getFlightTimeFromStart()) {
                    pq.remove(target);
                    target.setFlightTimeFromStart(flightDuration);
                    target.setPredecessor(start);
                    pq.add(target);
                }
            });
            start.setState(State.VISITED);
        }
        printResult();
    }

    private int calculateDurationToNextStation(Airport airport, Flight flight) {
        //TODO startnál nem kell 60ig várni
        int minTimeToDeparture = (airport.getFlightTimeFromStart() + 60) % DAY_MINS;
        int waitTime;
        if (flight.getDepartureTimeOnDayInMins() < minTimeToDeparture) {
            waitTime = DAY_MINS - ((airport.getFlightTimeFromStart() % DAY_MINS) - flight.getDepartureTimeOnDayInMins());
        } else {
            waitTime = flight.getDepartureTimeOnDayInMins() - (airport.getFlightTimeFromStart() % DAY_MINS);
        }
        return airport.getFlightTimeFromStart() + flight.getFlightDuration() + waitTime;
    }

    private void printResult() {
        if (arrival.getFlightTimeFromStart() != Integer.MAX_VALUE && arrival.getPredecessor() != null) {
            System.out.println(arrival.getFlightTimeFromStart());
            int sumOf = 0;
            String result = "";
            Airport actualAirport = arrival.getPredecessor();

            while (actualAirport != null) {
                sumOf++;
                result = actualAirport.getName() + " "+result;
                actualAirport = actualAirport.getPredecessor();
            }
            System.out.println(sumOf + " " + result);
        } else {
            System.out.println(0);
        }

    }

}
