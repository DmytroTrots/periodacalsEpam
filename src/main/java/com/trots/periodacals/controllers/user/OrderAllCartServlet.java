package com.trots.periodacals.controllers.user;

import com.trots.periodacals.entity.Cart;
import com.trots.periodacals.entity.Receipt;
import com.trots.periodacals.service.PeriodicalService;
import com.trots.periodacals.service.ReceiptHasPeriodicalService;
import com.trots.periodacals.service.ReceiptService;
import com.trots.periodacals.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Order all cart servlet.
 */
@WebServlet("/order-all")
public class OrderAllCartServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(OrderAllCartServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Cart> cart_list = (List<Cart>) req.getSession().getAttribute("cart_list");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String telephone = req.getParameter("telephone");
        String address = req.getParameter("address");

        Integer id = (Integer) req.getSession().getAttribute("ID");
        Double actualBalance = (Double) req.getSession().getAttribute("balance");
        Double totalPrice = (Double) req.getSession().getAttribute("totalPrice");
        if (cart_list != null && id != null && actualBalance > totalPrice) {
            Receipt receipt = new Receipt();
            receipt.setUserId(id);
            receipt.setName(name);
            receipt.setAdress(address);
            receipt.setSurname(surname);
            receipt.setEmail(email);
            receipt.setTelephoneNumber(telephone);
            Integer receiptID = ReceiptService.getInstance().insertReceiptAfterPayment(receipt);
            for (Cart c : cart_list) {
                receipt.setPeriodicalSellId(c.getSellId());
                Double price = PeriodicalService.getInstance().getPriceById(c.getSellId()) * c.getMonths();
                receipt.setMonths(c.getMonths());
                receipt.setPricePerMonth(price);

                log.trace("User " + req.getSession().getAttribute("userName") + " ordered periodical " + c.getSellId());
                if (receiptID!=null) {
                    ReceiptHasPeriodicalService.getInstance().insertReceiptAndPeriodical(receipt, receiptID);
                }
                else{
                    break;
                }
            }
            actualBalance = actualBalance-totalPrice;
            UserService.getInstance().updateFieldBalanceAfterTopUp(id, actualBalance);
            cart_list.clear();
            resp.sendRedirect(req.getContextPath()+"/shop?currentPage=1&category=0");
        } else {
            if (id == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
