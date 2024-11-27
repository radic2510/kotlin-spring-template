package com.yobi.standard.auth.annotaion

import org.springframework.security.access.prepost.PreAuthorize

@Target(allowedTargets = [AnnotationTarget.FUNCTION, AnnotationTarget.CLASS])
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('USER')")
annotation class RoleUser
