package ru.itis.companionapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itis.companionapp.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.details.phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query("update User u set u.details.avatarReference = ?2 where u.username = ?1")
    void setAvatarRef(String username, String avatarRef);

    @Modifying
    @Query("update User u set u.details.carBrand = ?2, u.details.carModel = ?3 where u.username = ?1")
    void setCarBrandAndModel(String username, String brand, String model);

    Optional<User> findById(Long id);
}
