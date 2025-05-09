package deu.movietalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFavoriteMovie {
    private String memberId;
    private Long movieId;
    private Long newMovieId;    // 새로운 영화 ID (수정할 ID)

}
