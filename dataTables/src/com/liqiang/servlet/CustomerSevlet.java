package com.liqiang.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.liqiang.entity.Customer;
import com.liqiang.util.DataTablePageResult;

/**
 * Servlet implementation class CustomerSevlet
 */
public class CustomerSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Customer> customers = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerSevlet() {
		super();
		customers = new ArrayList<Customer>();
		for (int i = 0; i < 100; i++) {
			Customer customer = new Customer();
			customer.setName("小明" + i);
			customer.setAge(12);
			customer.setSex(true);
			customer.setAddress("广东省广州市天河区凌塘村" + i);
			customers.add(customer);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataTablePageResult pageResult = new DataTablePageResult();
		int pageNumber =Integer.valueOf( request.getParameter("page.currentPage"));
		int pageSize =  Integer.valueOf( request.getParameter("page.PAGESIZE"));
		List<Customer> datas = new ArrayList<Customer>();
		int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
		for (int i = 0; i < pageSize && i < customers.size() - currIdx; i++) {
			Customer customer = customers.get(currIdx + i);
			datas.add(customer);
		}
		pageResult.setData(datas);
		pageResult.setRecordsFiltered(customers.size());
		pageResult.setRecordsTotal(customers.size());
		com.liqiang.util.JsonWriterUtil.strJsonWriter(response, pageResult, null);
	}


}
