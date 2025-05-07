package deu.movietalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MovieSearchSummaryDto {
    private String title;
    private String titleEng;
    private Integer createDts;
    private String genre;
    private String plot;

}
