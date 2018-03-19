package lumber.exceptionhandler;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lumber.tool.JsonTool;

/*
 * 处理程序运行过程中发生的一切异常
 */
@RestControllerAdvice
public class AllExceptionHandler {
	//数据库异常处理
	@ExceptionHandler(SQLException.class)  
    public String sqlExceptionHandler(SQLException ex){ 
         System.out.println("数据库异常");
         ex.printStackTrace();
        return JsonTool.Tip(false);  
    }  
  
    @ExceptionHandler(Exception.class)  
    public String exceptionHandler(Exception ex){  
         System.out.println("其他异常");
         ex.printStackTrace();
        return JsonTool.Tip(false);    
    } 
}
