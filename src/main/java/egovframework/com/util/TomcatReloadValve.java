package egovframework.com.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
/*
	import 에 빨간 줄이 갈 경우 project 우클릭 > Build Path > Add Libraries... > Server Runtime 추가
*/
public class TomcatReloadValve extends ValveBase{

	//컨텍스트를 다시 부르고 싶을때 부를 주소
	private static final String RELOAD_CONTEXT_URI = "/reloadContext";
	
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		
		Container container = getContainer();
		String requestUri = request.getRequestURI();
		String reloadUri = request.getContextPath() + RELOAD_CONTEXT_URI;

		if (requestUri.startsWith(reloadUri) && container instanceof Context) {
			reloadContext(response, container);
			return;
		}

		getNext().invoke(request, response);
		
	}
	
	/** tomcat 의 컨텍스트를 다시 읽어온다.
	 * @param response
	 * @param container
	 * @throws IOException
	 */
	private void reloadContext(Response response, Container container)	throws IOException {
		((Context) container).reload();
		HttpServletResponse httpResponse = response.getResponse();
		httpResponse.setContentType("text/plain;charset=utf-8");
		httpResponse.getWriter().write("Context Reloaded!!");
		httpResponse.getWriter().close();
		return;
	}

}
