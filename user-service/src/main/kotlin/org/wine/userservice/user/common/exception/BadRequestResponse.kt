package org.wine.userservice.user.common.exception

import io.swagger.v3.oas.annotations.media.Schema
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.http.HttpStatus

@Schema(description = "유효성 오류 응답 DTO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
class BadRequestResponse<T> {
    @Schema(title = "응답 코드", description = "응답 코드입니다", example = "400")
    private val code = HttpStatus.BAD_REQUEST.value()

    @Schema(title = "응답 메세지", description = "응답 메세지입니다", example = "BAD REQUEST")
    private val message = HttpStatus.BAD_REQUEST.name

    @Schema(description = "응답 데이터입니다")
    private val data: T? = null
}
