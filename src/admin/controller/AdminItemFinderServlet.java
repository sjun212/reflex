package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;
import item.model.vo.Item;

/**
 * Servlet implementation class AdminItemFinderServlet
 */
@WebServlet("/admin/item/itemFinder")
public class AdminItemFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminItemFinderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.parameter handling
		int cPage = 1;
		final int numPerPage = 10;
		
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));			
		}catch(NumberFormatException e) {
			
		}
		
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		String keyword="";
    	switch(searchKeyword){
    	case "반려동물": keyword = "CT01"; break;
    	case "육아": keyword = "CT02"; break;
    	case "파티": keyword = "CT03"; break;
    	case "운동": keyword = "CT04"; break;
    	case "여행": keyword = "CT05"; break;
    	case "캠핑": keyword = "CT06"; break;
    	default: keyword=searchKeyword;break;
    	}
		System.out.println("searchType@finder="+searchType);
		System.out.println("searchKeyword@finder="+keyword);
		
		//2.업무로직
		List<Item> list = null;
		AdminService adminService = new AdminService();
		System.out.println("searchType="+searchType);
		
		switch(searchType) {
		case "item_name": list = adminService.selectItemByItemName(keyword, cPage, numPerPage); break;
		case "category_no": list = adminService.selectItemByCategoryNo(keyword, cPage, numPerPage); break;
		}
		System.out.println("---------list@finderSevelt="+list);
		
		//페이징바 영역
		int totalItem = 0;
		switch (searchType) {
		case "item_name"	:totalItem = new AdminService().selectTotalItemByItemName(keyword);break;
		case "category_no" :totalItem = new AdminService().selectTotalItemByCategoryNo(keyword);break;
		}
		//(공식2)totalPage구하기
		int totalPage = (int)Math.ceil((double)totalItem/numPerPage);
		System.out.println("totalItem="+totalItem+", totalPage="+totalPage);
		
		//페이지바 html코드
		String pageBar = "";	
		//페이지바 길이
		int pageBarSize = 5;
		//(공식3)시작페이지 번호 세팅
		int pageStart = ((cPage - 1)/pageBarSize) * pageBarSize +1;
		//종료페이지 번호 세팅
		int pageEnd = pageStart+pageBarSize-1;
		System.out.println("pageStart["+pageStart+"] ~ pageEnd["+pageEnd+"]");
		int pageNo = pageStart;		


		//이전 section
		if(pageNo == 1 ){

		}
		else {
			pageBar += "<li><a href='"+request.getContextPath()+"/admin/item/itemFinder?searchType="+searchType+"&searchKeyword="+searchKeyword+"&cPage="+(pageNo-pageBarSize)+"'><span aria-hidden='true'>&laquo;</span></a></li>";
		}
		// pageNo section
		while(pageNo<=pageEnd && pageNo<=totalPage){
			if(cPage ==  pageNo ){
				pageBar += "<li class='active'><span class='cPage'>"+pageNo+"</span></li>";
			} 
			else {
				pageBar += "<li><a href='"+request.getContextPath()+"/admin/item/itemFinder?searchType="+searchType+"&searchKeyword="+searchKeyword+"&cPage="+pageNo+"'>"+pageNo+"</a></li>";
			}
			pageNo++;
		}
		
		//[다음]
		if(pageNo > totalPage){
			
		} else {
			
			pageBar += "<li><a href='"+request.getContextPath()+"/admin/item/itemFinder?searchType="+searchType+"&searchKeyword="+searchKeyword+"&cPage="+pageNo+"'><span aria-hidden='true'>&raquo;</span></a></li>";
		}
		
		
		
		System.out.println("list@finder="+list);
		System.out.println("pageBar@finder="+pageBar);
		
		//전체 상품수
		int totalContent = new AdminService().selectTotalItem();
		
		//판매중인 상품수
		int sellingItemCnt = new AdminService().selectSellingItem();
		
		//품절인 상품수
		int soldoutItemCnt = new AdminService().selectSoldoutItem();
		
		//3.view단 처리
		request.setAttribute("list", list);
		request.setAttribute("pageBar",pageBar);
		request.setAttribute("cPage",cPage);
		
		request.setAttribute("totalContent", totalContent);
		request.setAttribute("sellingItemCnt", sellingItemCnt);
		request.setAttribute("soldoutItemCnt", soldoutItemCnt);

		request.getRequestDispatcher("/WEB-INF/views/admin/item/adminItemSearch.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
