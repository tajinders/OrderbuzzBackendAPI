package com.app.orderbuzz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.orderbuzz.domain.Order;
import com.app.orderbuzz.service.OrderService;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired 
	private OrderService orderService;

	/**
	 * 
	 * @param restId - Restaurant ID
	 * @param status - Pending or Processed
	 * @return - This method will return list of orders for a restaurant based on status
	 */

	@RequestMapping(value = "/getorders/{restId}/{status}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public  List<Order> getOrdersForResturant(@PathVariable long restId, @PathVariable String status){
		return orderService.getOrdersForResturant(restId, status);
	}

	/**
	 * 
	 * @param restId - Stores Restaurant Id
	 * @param orderStatus - OrderStatus could be Pending or Processed
	 * @param orderSummary - Summary of Food Order which user has made
	 * @param userName - User Name or Email ID
	 * @param orderKey - Secret key User Will add While placing Order, 
	 *                   this key will be used as a password when User 
	 *                   Collects his order from Food Outlet
	 * @param orderGcmKey - Mobile Phone Registration Key, 
	 *                      We will send PUSH NOTIFICATION To registered 
	 *                      Mobile Phones when Food order is processed
	 *               
	 */
	@RequestMapping(value = "/submitorder", method = RequestMethod.GET)
	public void	SubmitOrder(
			@RequestParam("restid") String restId,
			@RequestParam("status") String orderStatus,
			@RequestParam("summary") String orderSummary,
			@RequestParam("name") String userName,
			@RequestParam("key") String orderKey,
			@RequestParam("gcmkey") String orderGcmKey){

		Order newOrder = new Order();
		newOrder.setOrderKey(orderKey);
		newOrder.setOrderStatus(orderStatus);
		newOrder.setOrderSummary(orderSummary);
		newOrder.setOrderGcmKey(orderGcmKey);
		newOrder.setUserName(userName);

		orderService.SubmitOrder(newOrder , restId);
	}
	
	/*
	 * This is method mainly used by vendor app and this method process order, 
	 * send pushnotification to user and updates queue number
	 * Service - http://localhost:8081/orderking/order/processedorder?restid=4&orderseqno=1
	 * 
	 */
	@RequestMapping(value = "/processedorder", method = RequestMethod.GET)
	public void	SubmitOrder(
			@RequestParam("restid") String restId,
			@RequestParam("orderseqno") String orderseqno){
		orderService.processedOrder(restId, orderseqno);
	}
	
}