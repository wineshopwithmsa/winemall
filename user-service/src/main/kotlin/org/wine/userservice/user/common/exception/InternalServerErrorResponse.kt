package org.wine.userservice.user.common.exception

import io.swagger.v3.oas.annotations.media.Schema
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.http.HttpStatus

@Schema(description = "서버 오류 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
class InternalServerErrorResponse<T> {
    @Schema(title = "응답 코드", description = "응답 코드입니다", example = "500")
    private val code = HttpStatus.INTERNAL_SERVER_ERROR.value()

    @Schema(title = "응답 메세지", description = "응답 메세지입니다", example = "INTERNAL SERVER ERROR")
    private val message = HttpStatus.INTERNAL_SERVER_ERROR.name

    @Schema(description = "응답 데이터입니다")
    private val data: T? = null
}
