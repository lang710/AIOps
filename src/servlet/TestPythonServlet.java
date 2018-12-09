package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/testPythonServlet")
public class TestPythonServlet extends HttpServlet {

    private String calculate(String[] cmdArr) throws IOException, InterruptedException{
        Process process=Runtime.getRuntime().exec(cmdArr);
        process.waitFor();
        InputStream is=process.getInputStream();
        DataInputStream dis=new DataInputStream(is);
        String result=dis.readLine();
        process.waitFor();
        return result;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String count1=request.getParameter("count1");
        //System.out.println(count1);
        String count2=request.getParameter("count2");
        //#System.out.println(count2);
        String exe="python";
        String command="/Users/mac/IdeaProjects/AIOps/src/python/calculator_simple.py";
        PrintWriter out=response.getWriter();
        String[] cmdArr=new String[] {exe, command, count1, count2};
        try {
            String result = calculate(cmdArr);
            System.out.println(result);
            out.print(result);
        }catch(InterruptedException ie){
            System.out.println(ie);
        }

    }
}
