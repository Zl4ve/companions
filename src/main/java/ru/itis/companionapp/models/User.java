package ru.itis.companionapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.itis.companionapp.utils.converters.PostgreSQLEnumType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"drivesAsDriver", "drivesAsCompanion", "reviews", "preferences"})
@Builder
@Entity
@Table(name = "account")
@TypeDef(
        name = "role",
        typeClass = PostgreSQLEnumType.class
)
@TypeDef(
        name = "account_status",
        typeClass = PostgreSQLEnumType.class
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "password")
    private String hashPassword;

    @Embedded
    private ProfileDetails details;

    @Type(type = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "account_status")
    @Type(type = "account_status")
    @Enumerated(EnumType.STRING)
    private Status accountStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.EAGER)
    private Set<Drive> drivesAsDriver;

    @JsonIgnore
    @ManyToMany(mappedBy = "companions", fetch = FetchType.EAGER)
    private Set<Drive> drivesAsCompanion;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Review> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Preference> preferences;

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new HashSet<>();
        }
        reviews.add(review);
    }

    public enum Role {
        USER,
        ADMIN
    }

    public enum Status {
        ACTIVE,
        BANNED
    }

}
