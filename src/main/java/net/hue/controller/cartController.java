package net.hue.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberService;
import net.hue.service.ProductService;
import net.hue.vo.CartVO;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;

@Controller
public class cartController {

	@Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;
    
  //장바구니 목록
    @RequestMapping("/showCart")
    public String ShowCart(HttpServletRequest request,
    		HttpServletResponse response,CategoryVO c, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
     // 헤더 부분  오른쪽상단 메뉴바
        HttpSession session = request.getSession();
        String memid = (String) session.getAttribute("memid");

        MemberVO mbean = memberService.getMemberById(memid);

        if (memid != null) {
            int result = 0;
            if (session.getAttribute("memno") != null) {
                int memno = (int) session.getAttribute("memno");
                result = cartService.countItemInCart(memno);
                model.addAttribute("result", result);
            }
        }
        model.addAttribute("mbean", mbean);
        // 헤더 오른쪽 상단 메뉴 end

        // 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤데 카테고리 end
                
        int mno = (int) session.getAttribute("memno");
        List<CartVO> ctlist = cartService.getAllItem(mno);
    	
        model.addAttribute("ctlist", ctlist);
    	
        return "member/cart";
    }//장바구니 목록
    
    //장바구니 삭제
    @RequestMapping("/deleteFromCart")
    public String deleteFromCart(HttpServletRequest request,
    		HttpServletResponse response, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        int itemno = Integer.parseInt(request.getParameter("itemno"));
        
        int cnt = cartService.deleteItem(itemno);
        
        if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('장바구니 삭제 성공');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('장바구니 삭제 실패');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }
    	
        return null;
    } //장바구니 삭제
    
  //장바구니 추가
    @RequestMapping("/addCart")
    public String addCart(HttpServletRequest request,
    		HttpServletResponse response, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        HttpSession session = request.getSession();
        
        int mno = (int) session.getAttribute("memno");
		int pno = Integer.parseInt(request.getParameter("pno"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String opname = request.getParameter("opname");
        
		int cnt = cartService.insertItem(mno,pno,qty,opname);
		
        if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('장바구니 추가 성공');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('장바구니 담기 실패');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }
    	
        return null;
    } //장바구니 추가
    
    //장바구니 수정
    @RequestMapping("/updateCartItem")
    public String updateCartItem(HttpServletRequest request,
    		HttpServletResponse response, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        int no = Integer.parseInt(request.getParameter("cartItemNum"));
		int qty = Integer.parseInt(request.getParameter("qty"));
        
		int cnt = cartService.updateItem(no, qty);
		
        if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('장바구니 수정 성공');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('장바구니 수정 실패');");
        	out.println("location='showCart';");
        	out.println("</script>");
        }
    	
        return null;
    } //장바구니 수정
}
