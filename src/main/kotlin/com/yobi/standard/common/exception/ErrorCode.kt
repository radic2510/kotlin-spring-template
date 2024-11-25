package com.yobi.standard.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val httpStatus: HttpStatus, val message: String) {
    // auth
    ILLEGAL_REGISTRATION_ID(HttpStatus.NOT_ACCEPTABLE, "illegal registration id"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 시그니처입니다."),

    // member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),

    // funding product
    FUNDING_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "펀딩 상품을 찾을 수 없습니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, "시작 날짜는 완료 날짜 이전이어야 합니다."),
    FUNDING_PRODUCT_NOT_EDIT(HttpStatus.BAD_REQUEST, "펀딩 진행중인 상품은 수정/삭제할 수 없습니다."),
    FUNDING_PRODUCT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 삭제된 상품입니다."),

    // funding
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND, "펀딩을 찾을 수 없습니다."),
    REWARD_NOT_MATCH(HttpStatus.BAD_REQUEST, "해당 상품의 리워드가 아닙니다."),
    ADDRESS_IS_REQUIRED(HttpStatus.BAD_REQUEST, "배송할 주소가 필요합니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
    ALREADY_FUNDED_REWARD(HttpStatus.BAD_REQUEST, "이미 해당 리워드에 펀딩하였습니다."),

    // reward
    REWARD_NOT_FOUND(HttpStatus.NOT_FOUND, "리워드를 찾을 수 없습니다."),

    // payment history
    INVALID_PAYMENT(HttpStatus.BAD_REQUEST, "결제 정보가 일치하지 않습니다."),

    // notification
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),

    // global
    RESOURCE_LOCKED(HttpStatus.LOCKED, "자원이 잠겨있어 접근할 수 없습니다."),
    NO_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 자원을 찾을 수 없습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치못한 에러가 발생했습니다.");
}