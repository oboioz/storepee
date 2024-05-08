//package controller;
//
//import java.io.IOException;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import dao.UserDAO;
//import entity.User;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet(name="UserServlet", urlPatterns = "/users")
//public class UserServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        UserDAO userDAO = new UserDAO();
//        try {
//            List<User> userList = userDAO.getAllUsers();
//            
//            System.out.println("User List:");
//            for (User user : userList) {
//                System.out.println(user);
//            }
//            
//            // Log to console if userList is not empty
//            if (!userList.isEmpty()) {
//                System.out.println("User list is not empty. Size: " + userList.size());
//            }
//            
//            request.setAttribute("userList", userList);
//            request.getRequestDispatcher("/users.jsp").forward(request, response);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            // Handle exception
//            response.sendRedirect(request.getContextPath() + "/error.jsp");
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
