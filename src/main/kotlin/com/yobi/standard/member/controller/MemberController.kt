package com.yobi.standard.member.controller

import com.yobi.standard.auth.annotaion.RoleUser
import com.yobi.standard.member.dto.MemberUpdateRequest
import com.yobi.standard.member.dto.model.MemberDto
import com.yobi.standard.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@Tag(name = "Member-Controller", description = "멤버 관련 컨트롤러")
@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @Operation(summary = "회원 정보 조회", description = "회원 정보 조회 API",
//        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(responseCode = "200", description = "성공",
        content = [Content(schema = Schema(implementation = MemberDto::class))]
    )
    @RoleUser
    @GetMapping
    fun memberInfo(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<MemberDto> {
        return ResponseEntity.ok(memberService.getMember(userDetails.username))
    }

    @Operation(summary = "회원 업데이트", description = "회원 정보 업데이트 API",
//        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(responseCode = "200", description = "성공",
        content = [Content(schema = Schema(implementation = MemberDto::class))]
    )
    @RoleUser
    @PatchMapping
    fun memberEdit(@RequestBody @Valid request: MemberUpdateRequest,
                   @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<MemberDto> {
        return ResponseEntity.ok(memberService.updateMember(request, userDetails.username))
    }
}