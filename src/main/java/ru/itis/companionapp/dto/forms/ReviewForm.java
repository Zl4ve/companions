package ru.itis.companionapp.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewForm {
    @NotBlank(message = "Choose rate")
    @Pattern(regexp = "^[1-5]$", message = "Wrong rate")
    private String rate;

    @NotBlank(message = "Enter text")
    private String text;
}
