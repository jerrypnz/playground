package jerry.chatroom.client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jerry.chatroom.protocol.COMMANDS;
import jerry.niof.client.Client;
import jerry.niof.client.ClientCallback;
import jerry.niof.util.CONSTANTS;

@SuppressWarnings("serial")
class LogInDialog extends JDialog implements ActionListener
{
	private JTextField addressField;

	private JTextField portField;

	private JTextField userField;

	private JButton okButton;

	private JButton cancelButton;

	private String address;

	private String port;

	private String username;

	boolean result = false;

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	public LogInDialog(JFrame parent)
	{
		super(parent);
		buildUI();
		this.setModal(true);
	}

	private void buildUI()
	{
		this.setTitle("登录");
		GridLayout dlgLayout = new GridLayout(4, 2);
		dlgLayout.setHgap(5);
		dlgLayout.setVgap(5);
		this.setLayout(dlgLayout);
		JLabel infoUser = new JLabel("用户名:");
		JLabel infoAddress = new JLabel("服务器地址:");
		JLabel infoPort = new JLabel("服务器端口:");
		addressField = new JTextField();
		portField = new JTextField();
		portField.setColumns(5);
		portField.setText(String.valueOf(CONSTANTS.DEFAULT_PORT));
		userField = new JTextField();
		okButton = new JButton("确定");
		cancelButton = new JButton("取消");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		this.add(infoUser);
		this.add(userField);
		this.add(infoAddress);
		this.add(addressField);
		this.add(infoPort);
		this.add(portField);
		this.add(okButton);
		this.add(cancelButton);
		
		int width=300,height=120;
		int x = this.getParent().getX() + this.getParent().getWidth()/2 - width/2;
		int y = this.getParent().getY() + this.getParent().getHeight()/2 - height/2;
		this.setBounds(x, y, width, height);
		this.setResizable(false);
	}

	boolean showDialog()
	{
		this.setVisible(true);
		return result;
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == okButton)
		{
			this.address = addressField.getText();
			this.port = portField.getText();
			this.username = userField.getText();
			if ("".equals(address) || "".equals(port) || "".equals(username))
			{
				JOptionPane.showMessageDialog(null, "请输入以上信息再登录");
				return;
			}
			result = true;
			this.setVisible(false);
		}
		else if (e.getSource() == cancelButton)
		{
			result = false;
			this.setVisible(false);
		}
	}
}

@SuppressWarnings("serial")
public class ChatClient extends JFrame implements ActionListener,
		ListSelectionListener, ClientCallback
{
	private static int DEFAULT_LEFT = 200;

	private static int DEFAULT_TOP = 200;

	private static int DEFAULT_HEIGHT = 632;

	private static int DEFAULT_WIDTH = 321;

	private LogInDialog loginDialog;

	private String host;

	private String portStr;

	private String username;

	public static void main(String[] args)
	{
		ChatClient mainFrm = new ChatClient();
		mainFrm.setVisible(true);
	}

	private JTextArea msgArea;

	private JTextArea inputArea;

	private JList userList;

	private DefaultListModel userListModel;

	private JButton sendMsg;

	private JButton sendPrivateMsg;

	private JMenuItem connect;

	private JMenuItem disConnect;

	private JMenu mainMenu;

	private String CurrentSelectedUser;

	private Client client = null;

	public ChatClient()
	{
		super();
		buildUI();
		disableControls();
	}

	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == sendMsg)
			this.sendMessage();
		else if (event.getSource() == sendPrivateMsg)
			this.sendPrivateMessage();
		else if (event.getSource() == connect)
			this.connect();
		else if (event.getSource() == disConnect)
			this.disConnect();
	}

	public void addNewUser(String user)
	{
		userListModel.addElement(user);
	}

	protected void buildUI()
	{
		this.setTitle("Swing聊天室 - 未连接");
		setBounds(DEFAULT_LEFT, DEFAULT_TOP, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		connect = new JMenuItem("连接到...");
		disConnect = new JMenuItem("断开连接");
		connect.addActionListener(this);
		disConnect.addActionListener(this);
		mainMenu = new JMenu("连接");
		mainMenu.add(connect);
		mainMenu.add(disConnect);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		this.getContentPane().setBackground(Color.WHITE);
		this.setLayout(null);

		msgArea = new JTextArea();
		TitledBorder msgBorder = BorderFactory.createTitledBorder("消息");
		msgBorder.setTitleJustification(TitledBorder.LEFT);
		msgArea.setBounds(0, 0, 200, 450);
		msgArea.setEditable(false);
		JScrollPane msgPane = new JScrollPane(msgArea);
		msgPane.setBounds(5, 5, 200, 450);
		msgPane.setBorder(msgBorder);
		msgPane.setBackground(Color.WHITE);
		this.getContentPane().add(msgPane);

		userListModel = new DefaultListModel();
		userList = new JList(userListModel);
		userList.addListSelectionListener(this);
		JScrollPane userListPane = new JScrollPane(userList);
		TitledBorder userlistBorder = BorderFactory.createTitledBorder("在线用户");
		userlistBorder.setTitleJustification(TitledBorder.LEFT);
		userListPane.setBounds(210, 5, 100, 450);
		userListPane.setBorder(userlistBorder);
		userListPane.setBackground(Color.white);
		this.getContentPane().add(userListPane);

		inputArea = new JTextArea();
		TitledBorder inputBorder = BorderFactory.createTitledBorder("发送消息");
		inputBorder.setTitleJustification(TitledBorder.LEFT);
		inputArea.setBounds(0, 0, 200, 110);
		JScrollPane inputPane = new JScrollPane(inputArea);
		inputPane.setBounds(5, 460, 200, 110);
		inputPane.setBorder(inputBorder);
		inputPane.setBackground(Color.WHITE);
		this.getContentPane().add(inputPane);

		sendMsg = new JButton("发送");
		sendMsg.setBounds(210, 468, 100, 45);
		sendMsg.addActionListener(this);
		this.getContentPane().add(sendMsg);

		sendPrivateMsg = new JButton("发送悄悄话");
		sendPrivateMsg.setBounds(210, 523, 100, 45);
		sendPrivateMsg.addActionListener(this);
		this.getContentPane().add(sendPrivateMsg);

		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.loginDialog = new LogInDialog(this);
		this.setVisible(false);

	}

	private void connect()
	{
		boolean result = loginDialog.showDialog();
		if (result == false)
			return;
		host = loginDialog.getAddress();
		portStr = loginDialog.getPort();
		username = loginDialog.getUsername();
		int port = Integer.parseInt(portStr);
		this.setTitle("Swing聊天室 - 连接中……");
		client = new Client(host, port, this);
		client.start();
	}

	public void deleteUser(String user)
	{
		if (CurrentSelectedUser.equals(user))
			CurrentSelectedUser = "";
		userListModel.removeElement(user);
	}

	protected void disableControls()
	{
		inputArea.setEnabled(false);
		sendMsg.setEnabled(false);
		sendPrivateMsg.setEnabled(false);
	}

	private void disConnect()
	{
		client.close();
		disableControls();
	}

	protected void enableControls()
	{
		inputArea.setEnabled(true);
		sendMsg.setEnabled(true);
		sendPrivateMsg.setEnabled(true);
	}

	private void getOnlineUsers()
	{
		String cmdStr = COMMANDS.GET_ONLINE_USER + COMMANDS.SPLITTER;
		client.sendMsg(cmdStr);
	}

	private boolean handleSystemMsg(String msg)
	{
		boolean continueParse = false;
		if (msg.equalsIgnoreCase(COMMANDS.ERROR_BAD_COMMAND))
			JOptionPane.showMessageDialog(null, "System error.");
		else if (msg.equalsIgnoreCase(COMMANDS.ERROR_NOT_LOGGED_IN))
			JOptionPane.showMessageDialog(null, "尚未登录，请登录");
		else if (msg.equalsIgnoreCase(COMMANDS.ERROR_USER_NOT_EXIST))
			JOptionPane.showMessageDialog(null, "此用户不存在，不能发送悄悄话");
		else if (msg.equalsIgnoreCase(COMMANDS.ERROR_USERNAME_EXISTS))
		{
			JOptionPane.showMessageDialog(null, "此用户名已被使用，请重新登录");
			reLogin();
		}
		else if (msg.equalsIgnoreCase(COMMANDS.LOGIN_SUCCESS))
		{
			JOptionPane.showMessageDialog(null, "登录成功");
			getOnlineUsers();
			enableControls();
			this.setTitle("Swing聊天室 - 已连接到" + this.host + ":" + this.portStr);
		}
		else
			continueParse = true;
		return continueParse;
	}

	private void login()
	{
		this.setTitle("Swing聊天室 - 登录中……");
		String loginStr = COMMANDS.LOGIN + COMMANDS.SPLITTER + username;
		client.sendMsg(loginStr);
	}

	// 当登录时的用户名已经被使用的时候调用这个方法
	// 要求用户再次输入一个用户名，重新登录
	private void reLogin()
	{
		username = JOptionPane.showInputDialog("请输入一个新用户名");
		while ("".equals(username))
		{
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			username = JOptionPane.showInputDialog("请输入一个新用户名");
		}
		login();
	}

	public void onException(Exception e, Client client)
	{
		e.printStackTrace();
	}

	public void onReceiveMsg(String msg, Client client)
	{
		if (handleSystemMsg(msg) == false)
			return;
		int splitPos = msg.indexOf(COMMANDS.SPLITTER);
		// 没有分割符说明请求不合法
		if (splitPos < 0)
			return;
		// 分离出命令
		String cmd = msg.substring(0, splitPos).trim();
		// 根据消息执行对应的处理方法
		if (cmd.equalsIgnoreCase(COMMANDS.NEW_MSG))
			showNewMessage(msg.substring(splitPos + 1));
		else if (cmd.equalsIgnoreCase(COMMANDS.NEW_PRIVATE_MSG))
			showNewPrivateMessage(msg.substring(splitPos + 1));
		else if (cmd.equalsIgnoreCase(COMMANDS.ONLINE_USER))
			showOnlineUser(msg.substring(splitPos + 1));
		else if (cmd.equalsIgnoreCase(COMMANDS.USER_LOGIN))
			addNewUser(msg.substring(splitPos + 1));
		else if (cmd.equalsIgnoreCase(COMMANDS.USER_LOGOFF))
			deleteUser(msg.substring(splitPos + 1));
	}

	public void onStart(Client client)
	{
		login();
	}

	public void onStop(Client client)
	{
		JOptionPane.showMessageDialog(null, "连接已经断开");
		this.setTitle("Swing聊天室 - 未连接");
		disableControls();
	}

	private void sendMessage()
	{
		String msg = inputArea.getText();
		if ("".equals(msg))
		{
			JOptionPane.showMessageDialog(null, "不能发送空消息");
			return;
		}
		String cmdStr = COMMANDS.SEND_MSG + COMMANDS.SPLITTER + COMMANDS.ALL
				+ COMMANDS.SPLITTER + msg;
		client.sendMsg(cmdStr);
		inputArea.setText("");
	}

	private void sendPrivateMessage()
	{
		String msg = inputArea.getText();
		if ("".equals(msg))
		{
			JOptionPane.showMessageDialog(null, "不能发送空消息");
			return;
		}
		if ("".equals(CurrentSelectedUser))
			JOptionPane.showMessageDialog(null, "请先从列表中选择一个用户");
		String cmdStr = COMMANDS.SEND_MSG + COMMANDS.SPLITTER
				+ CurrentSelectedUser + COMMANDS.SPLITTER + msg;
		client.sendMsg(cmdStr);
		inputArea.setText("");
		msgArea.append("\n你悄悄对" + CurrentSelectedUser + "说：\n");
		msgArea.append("   " + msg + "\n");
	}

	public void showNewMessage(String msg)
	{
		int temp = msg.indexOf(COMMANDS.SPLITTER);
		if (temp < 0)
			return;
		String user = msg.substring(0, temp);
		String message = msg.substring(temp + 1);
		msgArea.append("\n" + user + "对大家说：\n");
		msgArea.append("   " + message + "\n");
	}

	public void showNewPrivateMessage(String msg)
	{
		int temp = msg.indexOf(COMMANDS.SPLITTER);
		if (temp < 0)
			return;
		String user = msg.substring(0, temp);
		String message = msg.substring(temp + 1);
		msgArea.append("\n" + user + "悄悄对你说：\n");
		msgArea.append("   " + message + "\n");
	}

	public void showOnlineUser(String users)
	{
		StringTokenizer tokens = new StringTokenizer(users, COMMANDS.SPLITTER);
		while (tokens.hasMoreTokens())
		{
			String aUser = tokens.nextToken();
			userListModel.addElement(aUser);
		}
	}

	public void valueChanged(ListSelectionEvent event)
	{
		JList list = (JList) event.getSource();
		CurrentSelectedUser = (String) list.getSelectedValue();
		// JOptionPane.showMessageDialog(null, CurrentSelectedUser);
	}

}
