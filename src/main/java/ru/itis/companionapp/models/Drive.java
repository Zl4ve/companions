package ru.itis.companionapp.models;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"driver", "companions"})
@Builder
@Entity
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;

    private String destination;

    private LocalDate date;

    private LocalTime time;

    private Integer price;

    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToMany
    @JoinTable(
            name = "account_drive",
            joinColumns = @JoinColumn(name = "drive_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private Set<User> companions;

    public void addCompanion(User user) {
        if (companions == null) {
            companions = new HashSet<>();
        }
        companions.add(user);
    }
}
