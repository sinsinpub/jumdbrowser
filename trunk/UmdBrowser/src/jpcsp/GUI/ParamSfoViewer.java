package jpcsp.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class ParamSfoViewer extends JDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable paramSfo;

	/**
	 * Create the dialog.
	 */
	public ParamSfoViewer(Frame owner, String title) {
		super(owner, title);
		setName("paramSfoDlg");
		setLocationByPlatform(true);
		setTitle("UMD Parameters");
		setBounds(100, 100, 400, 420);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				paramSfo = new JTable();
				paramSfo.setModel(new DefaultTableModel(new Object[][] { {
						null, null }, }, new String[] { "PropertyKey",
						"PropertyValue" }) {
					/** serialVersionUID */
					private static final long serialVersionUID = 1L;
					Class[] columnTypes = new Class[] { String.class,
							Object.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { false, true };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				scrollPane.setViewportView(paramSfo);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Apply");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
