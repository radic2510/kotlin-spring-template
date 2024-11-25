plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.kotlinSpring)
	alias(libs.plugins.springBoot)
	alias(libs.plugins.springDependencyManagement)
	alias(libs.plugins.kotlinJpa)
}

group = "com.yobi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.bundles.kotlin.common)

	implementation(libs.springBootStarterActuator)
	implementation(libs.springBootStarterDataJpa)
	implementation(libs.springBootStarterOauth2Client)
	implementation(libs.springBootStarterSecurity)
	implementation(libs.springBootStarterWeb)

	runtimeOnly(libs.h2Database)
	runtimeOnly(libs.postgresql)

	testImplementation(libs.springBootStarterTest)
	testImplementation(libs.kotlinTestJunit5)
	testImplementation(libs.springSecurityTest)
	testRuntimeOnly(libs.junitPlatformLauncher)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.bootJar {
	enabled = true
	archiveFileName = "standard.jar"
	mainClass.set("com.yobi.standard.StandardApplicationKt")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
