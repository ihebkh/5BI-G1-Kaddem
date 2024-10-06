package tn.esprit.spring.kaddem.AOPConfig;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPDepartement {
    @Before("execution(* tn.esprit.spring.kaddem.services.DepartementServiceImpl.*(..))")
    public void logMethodEntry(JoinPoint joinPoint){
        String name=joinPoint.getSignature().getName();
        System.out.println("dans la methode : "+name+":");
    }
    @After("execution(* tn.esprit.spring.kaddem.services.DepartementServiceImpl.*(..))")
    public void logMethodEntry1(JoinPoint joinPoint){
        String name=joinPoint.getSignature().getName();
        System.out.println("la methode "+name+": est faite");
    }
    @Around("execution(* tn.esprit.spring.kaddem.services.DepartementServiceImpl.*(..))")
    public Object profile(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        long start=System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        long elapsedTime=System.currentTimeMillis() - start;
        System.out.println("Temps d'execution de la methode  :" + elapsedTime+ "milliseconds");
        return obj;
    }
}
