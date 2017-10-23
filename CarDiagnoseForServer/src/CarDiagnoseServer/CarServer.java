package CarDiagnoseServer;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CarServer
 */
@WebServlet("/CarServer")
public class CarServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext sContext;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		sContext = getServletContext();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarServer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BufferedReader reader = request.getReader();

		String str, data = "";
		while ((str = reader.readLine()) != null) {
			data += str;
		}
		System.out.println("data:" + data);

		if (data.equals(AssistServer.VISIT)) {
			if ((boolean) sContext.getAttribute(AssistServer.REQUEST_FLAG)) {
				String command = sContext.getAttribute(AssistServer.COMMAND).toString();

				response.getWriter().write(AssistServer.GET_COMMAND + AssistServer.HAVE + command);
			} else {
				response.getWriter().write(AssistServer.GET_COMMAND + AssistServer.NULL);
			}
		}else if (data.contains(AssistServer.CARID)) {
			String[] carIDStr = data.split(":");
			String carID = carIDStr[1];
			System.out.println("carID:" + carID);
			sContext.setAttribute(AssistServer.CARID, carID);
			response.getWriter().write("ok");
		}
		else {
			String post_data = data;
			sContext.setAttribute(AssistServer.DATA, post_data);
			sContext.setAttribute(AssistServer.RESPONSE_FLAG, true);
			response.getWriter().write("upload data successful!!!");
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("hello world,carServer");
		System.out.println("hello world,carServer");

	}

}
