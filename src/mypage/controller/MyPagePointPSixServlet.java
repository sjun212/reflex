package mypage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mypage.model.service.MyPageService;
import mypage.model.vo.MyPage;

/**
 * Servlet implementation class MyPagePointPSixServlet
 */
@WebServlet("/mypage/pointPlusSix")
public class MyPagePointPSixServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPagePointPSixServlet() {
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
					// cPage입력값이 없거나, 부정입력한 경우 기본값으로 처리된다.
				}
//								System.out.println("cPage@list="+cPage);

				// 페이징바영역처리
				int totalContent = new MyPageService().selectTotalContent();
				int totalPage = (int) Math.ceil((double) totalContent / numPerPage);// (공식2)
//								System.out.printf("totalContent=%s, totalPage=%s%n", totalContent, totalPage);

				String pageBar = "";
				int pageBarSize = 5;

				// (공식3)
				int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
				int pageEnd = pageStart + pageBarSize - 1;

				// 증감변수 pageNo
				int pageNo = pageStart;

				String memberId = request.getParameter("memberId");
				List<MyPage> list = new MyPageService().selectPointPSix(memberId, cPage, numPerPage);

				System.out.println(list + "$$$$$$$$$$$$$$");
				// 1.이전
				if (pageNo != 1) {
					pageBar += "<a href='" + request.getContextPath() + "/mypage/mypagePoint?memberId=" + memberId + "&cPage="
							+ (pageNo - 1) + "'>[이전]</a>\n";
				}

				// 2.pageNo
				while (pageNo <= pageEnd && pageNo <= totalPage) {
					// 현재페이지인 경우
					if (cPage == pageNo) {
						pageBar += "<span class='cPage'>" + pageNo + "</span>\n";
					} else {
						pageBar += "<a href='" + request.getContextPath() + "/mypage/mypagePoint?memberId=" + memberId
								+ "&cPage=" + pageNo + "'>" + pageNo + "</a>\n";
					}

					pageNo++;
				}
				// 3.다음
				if (pageNo <= totalPage) {
					pageBar += "<a href='" + request.getContextPath() + "/mypage/mypagePoint?memberId=" + memberId + "&cPage="
							+ pageNo + "'>[다음]</a>\n";
				}

				System.out.println("mypage-memberid-servlet" + memberId);

			

				request.setAttribute("list", list);
			
				request.setAttribute("pageBar", pageBar);

				System.out.println("mypage-point-servlet" + list);

				request.getRequestDispatcher("/WEB-INF/views/mypage/mypagePointAjax.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
