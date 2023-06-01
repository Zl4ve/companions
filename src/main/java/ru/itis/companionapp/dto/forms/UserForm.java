package ru.itis.companionapp.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.companionapp.utils.validators.ValidationOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@GroupSequence({UserForm.class, ValidationOrder.FirstGroup.class, ValidationOrder.SecondGroup.class})
public class UserForm {
    @NotBlank(message = "Enter a username", groups = {ValidationOrder.FirstGroup.class})
    @Size(min = 3, max = 35, message = "Username length must be between 3 and 35", groups = {ValidationOrder.SecondGroup.class})
    private String username;

    @NotBlank(message = "Enter a password", groups = {ValidationOrder.FirstGroup.class})
    @Size(min = 6, message = "Password length must be at least 6 characters", groups = {ValidationOrder.SecondGroup.class})
    private String password;

    @NotBlank(message = "Enter your first name", groups = {ValidationOrder.FirstGroup.class})
    private String firstName;

    @NotBlank(message = "Enter your last name", groups = {ValidationOrder.FirstGroup.class})
    private String lastName;

    @NotBlank(message = "Enter your phone number", groups = {ValidationOrder.FirstGroup.class})
    @Pattern(regexp = "^(^8|7|\\+7)((\\d{10})|(\\s\\(\\d{3}\\)\\s\\d{3}\\s\\d{2}\\s\\d{2}))$",
            message = "Invalid phone number",
            groups = {ValidationOrder.SecondGroup.class})
    private String phoneNumber;
}