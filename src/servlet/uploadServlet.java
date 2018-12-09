package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/*需要把脚本文件下载到部署目录，这样就不需要重复部署，
 * 而不是下载到项目目录，然后重新部署
 * 部署目录：/Users/mac/IdeaProjects/JavaWeb/out/artifacts/JavaWeb_war_exploded/scripts
 * 项目目录：/Users/mac/IdeaProjects/JavaWeb/web/scripts
 * */
@WebServlet("/uploadServlet")
@MultipartConfig()
public class uploadServlet extends HttpServlet {

    //将样本记录存储到ES中
    private boolean insertRecord(String name){
        return true;
    }

    private String getFilename(Part part) {
        String header = part.getHeader("Content-Disposition");
        String filename = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
        return filename;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");//为了处理中文文件名
        Part part = request.getPart("myFile");//使用getPart()获得Part对象
        String filename = getFilename(part);
        part.write("/Users/mac/IdeaProjects/AIOps/out/artifacts/AIOps/dataset/"+filename);

        insertRecord(filename);

    }
}


