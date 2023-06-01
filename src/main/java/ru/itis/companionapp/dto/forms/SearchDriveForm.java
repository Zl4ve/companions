package ru.itis.companionapp.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.companionapp.utils.validators.IsDateAndNotPast;
import ru.itis.companionapp.utils.validators.ValidationOrder;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@GroupSequence({SearchDriveForm.class, ValidationOrder.FirstGroup.class, ValidationOrder.SecondGroup.class})
public class SearchDriveForm {
    @NotBlank(message = "Enter source location", groups = {ValidationOrder.FirstGroup.class})
    private String source;

    @NotBlank(message = "Enter destination location", groups = {ValidationOrder.FirstGroup.class})
    private String destination;

    @NotBlank(message = "Enter date", groups = {ValidationOrder.FirstGroup.class})
    @IsDateAndNotPast(groups = {ValidationOrder.SecondGroup.class})
    private String date;
}
