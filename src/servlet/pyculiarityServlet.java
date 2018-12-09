package servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet("/pyculiarity")
public class pyculiarityServlet  extends HttpServlet {

    private String calculate(String[] cmdArr) throws IOException, InterruptedException{
        Process process=Runtime.getRuntime().exec(cmdArr);
        process.waitFor();
        InputStream is=process.getInputStream();
        DataInputStream dis=new DataInputStream(is);
        String imgPath=dis.readLine();

        /*
        String result="";
        String line="";
        while ((line = dis.readLine()) != null) {
            result += line;
        }

        process.waitFor();
        return result;
        */
        process.waitFor();
        return imgPath;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String exe="python3";
        String command="/Users/mac/IdeaProjects/AIOps/out/artifacts/AIOps_war_exploded/python/pyculiarity/getOutGraph.py";
        PrintWriter out=response.getWriter();
        String[] cmdArr=new String[] {exe, command};

        try {
            String imgPath = calculate(cmdArr);
            System.out.println(imgPath);
            HttpSession sess=request.getSession();
            sess.setAttribute("imgPath",imgPath);
            response.sendRedirect("index.jsp");


            //out.print(result);
        }catch(InterruptedException ie){
            System.out.println(ie);
        }


    }
}
