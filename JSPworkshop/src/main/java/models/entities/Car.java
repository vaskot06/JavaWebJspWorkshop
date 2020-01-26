package models.entities;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{
    private String brand;
    private String model;
    private int year;
    private EngineEnum engine;
    private User user;

    @Column(nullable = false)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public EngineEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineEnum engine) {
        this.engine = engine;
    }

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
