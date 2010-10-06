package jpcsp.GUI;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class UmdIsoExplorer extends JDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UmdIsoExplorer dialog = new UmdIsoExplorer();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public UmdIsoExplorer() {
		setName("isoExplorerDlg");
		setLocationByPlatform(true);
		setBounds(100, 100, 640, 480);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		fileMenu.add(mntmClose);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton button = new JButton("");
		toolBar.add(button);
		
		JSplitPane splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		splitPane.setLeftComponent(scrollPane);
		
		JTree tree = new JTree();
		tree.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
		scrollPane.setViewportView(tree);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		splitPane.setRightComponent(scrollPane_1);
		
		table = new JTable();
		table.setShowGrid(false);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"Filename", "Size", "Type", "Date", "Attribute"
			}
		) {
			/** serialVersionUID */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, Long.class, String.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_1.setViewportView(table);
		
		JLabel label = new JLabel("New label");
		label.setBorder(UIManager.getBorder("TextField.border"));
		getContentPane().add(label, BorderLayout.SOUTH);

	}

}
