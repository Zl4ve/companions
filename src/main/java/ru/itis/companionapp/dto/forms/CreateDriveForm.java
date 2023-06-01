package ru.itis.companionapp.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.companionapp.utils.validators.IsDateAndNotPast;
import ru.itis.companionapp.utils.validators.IsTime;
import ru.itis.companionapp.utils.validators.ValidationOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@GroupSequence({CreateDriveForm.class, ValidationOrder.FirstGroup.class, ValidationOrder.SecondGroup.class})
public class CreateDriveForm {
    @NotBlank(message = "Enter source location", groups = {ValidationOrder.FirstGroup.class})
    private String source;

    @NotBlank(message = "Enter destination location", groups = {ValidationOrder.FirstGroup.class})
    private String destination;

    @NotBlank(message = "Enter date", groups = {ValidationOrder.FirstGroup.class})
    @IsDateAndNotPast(groups = {ValidationOrder.SecondGroup.class})
    private String date;

    @NotBlank(message = "Enter time", groups = {ValidationOrder.FirstGroup.class})
    @IsTime(groups = {ValidationOrder.SecondGroup.class})
    private String time;

    @NotBlank(message = "Enter price", groups = {ValidationOrder.FirstGroup.class})
    @Pattern(regexp = "^[1-9]\\d*$", message = "Wrong number", groups = {ValidationOrder.SecondGroup.class})
    private String price;

    @NotBlank(message = "Enter number of passengers", groups = {ValidationOrder.FirstGroup.class})
    @Pattern(regexp = "^[1-4]$", message = "Wrong number of passengers", groups = {ValidationOrder.SecondGroup.class})
    private String numberOfPassengers;
}
