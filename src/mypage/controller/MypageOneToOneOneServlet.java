package mypage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypage.model.service.qService;
import mypage.model.vo.Qna;

/**
 * Servlet implementation class MypageOneToOneOneServlet
 */
@WebServlet("/mypage/mypageOneToOneOne")
public class MypageOneToOneOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MypageOneToOneOneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자입력값 처리
		int cPage = 1;// 초기값 설정
		final int numPerPage = 10;
		try {
			cPage = Integer.parseInt(request.getParameter("cPage"));
		} catch (NumberFormatException e) {
		}
		// 페이징바영역처리
		int totalContent = new qService().selectTotalContent();
		int totalPage = (int) Math.ceil((double) totalContent / numPerPage);
		String pageBar = "";
		int pageBarSize = 5;
		// (공식3)
		int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		// 증감변수 pageNo
		int pageNo = pageStart;
		String memberId = request.getParameter("memberId");
		Qna q = new qService().selectQna(memberId);
		List<Qna> list = new qService().selectOneToOneOne(memberId, cPage, numPerPage);
		// 1.이전
		if (pageNo != 1) {							
			pageBar += "<a href='" + request.getContextPath() + "/mypage/mypageOneToOne?memberId=" + memberId + "&cPage="
					+ (pageNo - 1) + "'>[이전]</a>\n";
		}
		// 2.pageNo
		while (pageNo <= pageEnd && pageNo <= totalPage) {
			// 현재페이지인 경우
			if (cPage == pageNo) {
				pageBar += "<span class='cPage'>" + pageNo + "</span>\n";
			} else {
				pageBar += "<a href='" + request.getContextPath() + "/mypage/mypageOneToOne?memberId=" + memberId
						+ "&cPage=" + pageNo + "'>" + pageNo + "</a>\n";
			}
			pageNo++;
		}
		// 3.다음
		if (pageNo <= totalPage) {
			pageBar += "<a href='" + request.getContextPath() + "/mypage/mypageOneToOne?memberId=" + memberId + "&cPage="
					+ pageNo + "'>[다음]</a>\n";
		}
		request.setAttribute("list", list);
		request.setAttribute("qna", q);
		request.setAttribute("pageBar", pageBar);
		System.out.println("mypage-onetoone-servlet" + list);
		request.getRequestDispatcher("/WEB-INF/views/mypage/mypageOneToOneAjax.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
