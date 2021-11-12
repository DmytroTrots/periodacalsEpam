package com.trots.periodacals.controllers;

import com.trots.periodacals.daoimpl.ReceiptDaoImpl;
import com.trots.periodacals.entity.Receipt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(PrintWriter out = resp.getWriter()){
            Integer id = (Integer) req.getSession().getAttribute("ID");
            if(id!=null){
                ReceiptDaoImpl receiptDao = new ReceiptDaoImpl();
                List<Receipt> receiptList= receiptDao.getAllOrdersOfUserById(id);
                if (!receiptList.isEmpty()){
                req.setAttribute("receiptList", receiptList);
                }
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/views/profilePage.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}