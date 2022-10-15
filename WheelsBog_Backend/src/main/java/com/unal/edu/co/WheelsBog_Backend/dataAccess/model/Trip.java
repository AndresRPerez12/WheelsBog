package com.unal.edu.co.WheelsBog_Backend.dataAccess.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "trip" )
public class Trip {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_driver")
    private User driver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_origin")
    private Location origin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_destination")
    private Location destination;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "passenger_capacity")
    private Long passengerCapacity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trip_has_passengers",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = { @JoinColumn(name = "user_id") } )
    private List<User> passengers;

    @Column(name = "trip_completed")
    private Boolean tripCompleted = false;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(Long passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public Boolean getTripCompleted() {
        return tripCompleted;
    }

    public void setTripCompleted(Boolean tripCompleted) {
        this.tripCompleted = tripCompleted;
    }
}
