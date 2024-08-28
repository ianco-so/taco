package me.taco.controller.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "taco.props")
@Validated
@Data
public class Props {
    
    @Min(value = 5, message = "Page size must be at least 5 and at most 25")
    @Max(value = 25,message = "Page size must be at least 5 and at most 25")
    private int pageSize;
}
