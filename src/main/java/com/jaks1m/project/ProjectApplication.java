package com.jaks1m.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ProjectApplication {
	@PostConstruct
	public void started() {TimeZone.setDefault(TimeZone.getTimeZone("KST"));}
	public static void main(String[] args) {SpringApplication.run(ProjectApplication.class, args);}
}
