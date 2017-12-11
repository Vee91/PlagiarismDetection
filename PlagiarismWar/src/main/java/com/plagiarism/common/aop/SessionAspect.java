package com.plagiarism.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.plagiarism.sessionbean.UserDetailsSessionBean;


@Aspect
public class SessionAspect {
	@Autowired
	private UserDetailsSessionBean userBean;

	@Before("execution(* com.plagiarism.controller.*.*(..))")
	public void populateUserBean(JoinPoint joinPoint) {

		try {

			System.out.println("Session here");
			if (null == userBean) {
				System.out.println("Session working");
				// populate userbean
				}

			
		} catch (Exception e) {
		    
		}
	}

}