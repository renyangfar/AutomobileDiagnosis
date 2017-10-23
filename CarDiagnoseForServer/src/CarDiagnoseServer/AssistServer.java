package CarDiagnoseServer;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AssistServer
 */
@WebServlet("/AssistServer")
public class AssistServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ServletContext sContext;
	public static final String STYLE = "style";
	public static final String COMMAND = "command";
	public static final String LOGIN = "login";
	public static final String CARID = "carID";
	public static final String GET_COMMAND = "get_command:";
	public static final String REQUEST_FLAG = "req_flag";
	public static final String RESPONSE_FLAG = "res_flag";
	public static final String DATA = "data";
	public static final String POST_DATA = "post_data";
	public static final String GET_DATA = "get_data:";
	public static final String DO_WHAT = "do_what";
	public static final String VISIT = "visit";
	public static final String YES = "yes";
	public static final String NULL = "no";
	public static final String HAVE = "have:";
	public static final String ROOT_PASSWORD = "475490416";

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		sContext = getServletContext();
		sContext.setAttribute(AssistServer.COMMAND, AssistServer.NULL);
		sContext.setAttribute(AssistServer.REQUEST_FLAG, false);
		sContext.setAttribute(AssistServer.RESPONSE_FLAG, false);
		sContext.setAttribute(AssistServer.CARID, "");
		System.out.println("AssistServer Init!!!!!!!!!!!");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().write("hello world AssistServer");
		System.out.println("hello world AssistServer");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
/*		BufferedReader reader = request.getReader();

		String str, wholeStr = "";
		while ((str = reader.readLine()) != null) {
			wholeStr += str;
		}
		System.out.println("data:" + wholeStr);
		response.getWriter().write("data:" + wholeStr + '\n');*/
		String command = request.getParameter(AssistServer.COMMAND);
		response.getWriter().write("data:" + command + '\n');

	}

}
