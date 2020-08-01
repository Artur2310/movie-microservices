package ru.kinoservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("Dto for add person in database")
public class PersonParsedDto extends BaseParsedDto {

    @JsonProperty(required = true)
    private String name;

    private Date birthDate;

    @ApiModelProperty(notes = "List source id movies")
    private Set<Integer> moviesByActor;

    @ApiModelProperty(notes = "List source id movies")
    private Set<Integer> moviesByDirector;
}
