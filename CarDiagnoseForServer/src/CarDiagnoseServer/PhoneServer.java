package CarDiagnoseServer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PhoneServer
 */
@WebServlet("/PhoneServer")
public class PhoneServer extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServletContext sContext;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		sContext = getServletContext();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String style = request.getParameter(AssistServer.STYLE);
		if (style.contains(AssistServer.COMMAND)) {
			// 请求命令方式
			System.out.println("command request");
			commandExcute(request, response);

		} else if (style.contains(AssistServer.LOGIN)) {
			// 登录请求方式
			System.out.println("login request");
			loginExcute(request, response);

		} else if (style.contains(AssistServer.CARID)) {
			// 获取汽车ID
			System.out.println("getCarID request");
			getCarIDExcute(request, response);
		} else if (style.contains("register")) {
			// 注册
			System.out.println("register request");
			registerExcute(request, response);
		}

	}

	private void registerExcute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String carID = request.getParameter("carID");
		String password = request.getParameter("password");

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/automobilediagnose?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", AssistServer.ROOT_PASSWORD);//475490416 1234
			Statement st = conn.createStatement();
			String sql = "INSERT INTO `automobilediagnose`.`mytable`\n" + "VALUES (NULL," + carID + ',' + password
					+ ");";
			st.executeUpdate(sql);
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("mysql error");
			response.getWriter().write("failed");
		}
		
		response.getWriter().write("pass");

	}

	private void getCarIDExcute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String carID = (String) sContext.getAttribute(AssistServer.CARID);
		response.getWriter().write(carID);

	}

	private void loginExcute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean flag = false;
		String carID = request.getParameter("carID");
		String carPassword = request.getParameter("carPassword");
		// 在数据库中寻找carID
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/automobilediagnose?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", AssistServer.ROOT_PASSWORD);
			Statement st = conn.createStatement();
			String sql = "select * from mytable";
			ResultSet resultSet = st.executeQuery(sql);
			while (resultSet.next()) {

				String carID1 = resultSet.getString("carID");
				if (carID1.equals(carID)) {
					String password1 = resultSet.getString("password");
					if (password1.equals(carPassword)) {
						flag = true;
						sContext.setAttribute(AssistServer.CARID, carID);
						System.out.println("register pass");
						response.getWriter().write("pass");
					}
					break;
				}

			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("mysql error");
			response.getWriter().write("failed");
		}

		if (!flag) {
			response.getWriter().write("failed");
			System.out.println("signed failed");
		}

	}

	private void commandExcute(HttpServletRequest request, final HttpServletResponse response) throws IOException {

		Calendar calendar = Calendar.getInstance();
		boolean carResponseFlag = true;
		String command = request.getParameter(AssistServer.COMMAND);
		System.out.println("command:" + command);
		sContext.setAttribute(AssistServer.REQUEST_FLAG, true);
		sContext.setAttribute(AssistServer.RESPONSE_FLAG, false);
		sContext.setAttribute(AssistServer.COMMAND, command);

		int minite = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int hour = calendar.get(Calendar.HOUR);
		int time_old = hour * 60 * 60 + minite * 60 + second;
		System.out.println("记录秒：" + time_old);
		int time_over = time_old + 10;
		System.out.println("超时秒：" + time_over);

		while (!(boolean) sContext.getAttribute(AssistServer.RESPONSE_FLAG)) {
			Calendar calendar2 = Calendar.getInstance();
			int time_now = calendar2.get(Calendar.HOUR) * 60 * 60 + calendar2.get(Calendar.MINUTE) * 60
					+ calendar2.get(Calendar.SECOND);
			System.out.println("当前秒:" + time_now);
			try {
				Thread.currentThread();
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 毫秒

			if (time_now > time_over) {
				carResponseFlag = false;
				System.out.println("have not car response");
				break;
			}

		}
		if (carResponseFlag) {

			// 返回数据
			String get_data = sContext.getAttribute(AssistServer.DATA).toString();
			response.getWriter().write(AssistServer.GET_DATA + get_data);
		}

		sContext.setAttribute(AssistServer.REQUEST_FLAG, false);
		sContext.setAttribute(AssistServer.COMMAND, AssistServer.NULL);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().write("hello world!phoneServer");
		System.out.println("hello world,phoneServer");

	}

}
