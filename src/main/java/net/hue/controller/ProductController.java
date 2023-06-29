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
import net.hue.service.StockService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.ProductVO;
import net.hue.vo.StockVO;

@Controller
public class ProductController {

	@Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private StockService stockService;
    
    //대분류 카테고리 상품
    @RequestMapping("/showLargeCategory")
    public String showLargeCategory(HttpServletRequest request,
    		HttpServletResponse response,CategoryVO c,ProductVO p, Model model) throws Exception {
    	
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
        
        int lcno=0;
		if(request.getParameter("lcno") != null) {
		    try {
		        lcno = Integer.parseInt(request.getParameter("lcno"));
		    } catch (NumberFormatException e) {
		        e.printStackTrace();
		    } 
		}
		
		int page=1;
		int limit=8;
        
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount=productService.getLargeListCount(lcno);
		
		p.setStartrow((page-1)*8+1);
		p.setEndrow(p.getStartrow()+limit-1);
		
		List<ProductVO> plist = productService.getLcList(lcno,p);
		List<ProductVO> plist2 = productService.getLcList2(lcno,p);
		List<ProductVO> plist3 = productService.getLcList3(lcno,p);
		
		
		int maxpage=(int)((double)listcount/limit+0.95); //총페이지 수
		int startpage=(((int)((double)page/8+0.875))-1)*8+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+8-1) endpage=startpage+8-1;
        
        model.addAttribute("plist", plist);
        model.addAttribute("plist2", plist2);
        model.addAttribute("plist3", plist3);
        model.addAttribute("page", page);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage",endpage);
        model.addAttribute("maxpage", maxpage);
        model.addAttribute("listcount", listcount);
        model.addAttribute("lcno", lcno);
		
        return "member/showLargeCategory";
    } //대분류 카테고리 상품
    
  //소분류 카테고리 상품
    @RequestMapping("/showSmallCategory")
    public String showSmallCategory(HttpServletRequest request,
    		HttpServletResponse response,CategoryVO c,ProductVO p, Model model) throws Exception {
    	
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
        
        int scno=0;
		if(request.getParameter("scno") != null) {
		    try {
		        scno = Integer.parseInt(request.getParameter("scno"));
		    } catch (NumberFormatException e) {
		        e.printStackTrace();
		    }
		}
		
		int lcno=0;
		if(request.getParameter("lcno") != null) {
		    try {
		        lcno = Integer.parseInt(request.getParameter("lcno"));
		    } catch (NumberFormatException e) {
		        e.printStackTrace();
		    }
		}
		
		int page=1;
		int limit=8;
        
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		int listcount=productService.getSmallListCount(lcno,scno);
		
		p.setStartrow((page-1)*8+1);
		p.setEndrow(p.getStartrow()+limit-1);
		
		List<ProductVO> plist = productService.getScList(lcno,scno,p);
		List<ProductVO> plist2 = productService.getScList2(lcno,scno,p);
		List<ProductVO> plist3 = productService.getScList3(lcno,scno,p);
		List<ProductVO> lplist = productService.getLcList(lcno,p);
        model.addAttribute("lplist", lplist);
		
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/8+0.875))-1)*8+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+8-1) endpage=startpage+8-1;
        
        model.addAttribute("plist", plist);
        model.addAttribute("plist2", plist2);
        model.addAttribute("plist3", plist3);
        model.addAttribute("page", page);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage",endpage);
        model.addAttribute("maxpage", maxpage);
        model.addAttribute("listcount", listcount);
        model.addAttribute("lcno", lcno);
        model.addAttribute("scno", scno);

        return "member/showSmallCategory";
    } //소분류 카테고리 상품    
    
    
  //상품디테일
    @RequestMapping("/getProduct_detail_ok")
    public String ProductDetailOk(HttpServletRequest request,
    		HttpServletResponse response,CategoryVO c,ProductVO p,
    		StockVO s,Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();  // 출력 스트림 생성
        
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
        
       int pno = Integer.parseInt(request.getParameter("no"));
       List<StockVO> slist = stockService.getAllStockByPno(pno);
		
       int no = Integer.parseInt(request.getParameter("no"));
       p.setNo(no);
       
       ProductVO prod = productService.getProduct(no);
       
       if(prod == null) {
    	   out.println("<script>");
    	   out.println("alert('상품정보를 찾을 수 없습니다!');");
    	   out.println("history.back();");
    	   out.println("</script>");
       }else {
    	   model.addAttribute("prodNo", prod.getNo());
    	   model.addAttribute("prodName", prod.getName());
    	   model.addAttribute("prodInfo", prod.getInfo());
    	   model.addAttribute("prodOriPri", prod.getOriprice());
    	   model.addAttribute("prodDisPri", prod.getDiscprice());
    	   model.addAttribute("prodMainImg", prod.getMainImgN());
    	   model.addAttribute("prodDetailImg1", prod.getDetailImgN1());
    	   model.addAttribute("prodDetailImg2", prod.getDetailImgN2());
    	   model.addAttribute("prodDetailImg3", prod.getDetailImgN3());
    	   model.addAttribute("prodDetailImg4", prod.getDetailImgN4());
    	   model.addAttribute("Lc", prod.getLcname());
    	   model.addAttribute("Sc", prod.getScname());
    	   model.addAttribute("slist", slist);
        
    	   return "member/productDetail";
       }
       return null;
    } //대분류 카테고리 상품
}
