package hendi.export2xls.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;

@SuppressWarnings("serial")
public class ExportFile extends JFrame 
{

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtNama;
	private JTextField txtAlamat;
	private JLabel lblId;
	private JLabel lblNama;
	private JLabel lblAlamat;
	private JButton btnSimpan;
	private JButton btnEdit;
	private JButton btnHapus;
	private JTable tabel;
	DefaultTableModel tabelModel;
	ModifyTable mt;
	String[] data = {"ID","Nama","Alamat"};
	/**
	 * Create the frame.
	 */
	public ExportFile() 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblId = new JLabel("ID : ");
		lblId.setForeground(new Color(255, 255, 255));
		lblId.setBounds(16, 6, 60, 15);
		contentPane.add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(106, 0, 122, 27);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		lblNama = new JLabel("Nama : ");
		lblNama.setForeground(new Color(255, 255, 255));
		lblNama.setBounds(16, 43, 60, 15);
		contentPane.add(lblNama);
		
		txtNama = new JTextField();
		txtNama.setBounds(106, 37, 230, 27);
		contentPane.add(txtNama);
		txtNama.setColumns(10);
		
		lblAlamat = new JLabel("Alamat : ");
		lblAlamat.setForeground(new Color(255, 255, 255));
		lblAlamat.setBounds(16, 88, 60, 15);
		contentPane.add(lblAlamat);
		
		txtAlamat = new JTextField();
		txtAlamat.setBounds(106, 82, 274, 27);
		contentPane.add(txtAlamat);
		txtAlamat.setColumns(10);
		
		btnSimpan = new JButton("Simpan");
		btnSimpan.setIcon(new ImageIcon("src/hendi/export2xls/images/Simpan.png"));
		btnSimpan.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent act)
			{
				try
				{
					Connection konek = Koneksi.getKoneksi();
					String query = "INSERT INTO Data VALUES (?,?,?)";
					PreparedStatement prepare = konek.prepareStatement(query);
					
					prepare.setInt(1,Integer.parseInt(txtId.getText()));
					prepare.setString(2,txtNama.getText());
					prepare.setString(3,txtAlamat.getText());
					prepare.executeUpdate();
					JOptionPane.showMessageDialog(null,"Data berhasil disimpan","Pesan",JOptionPane.INFORMATION_MESSAGE);
					prepare.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Data gagal disimpan","Pesan",JOptionPane.INFORMATION_MESSAGE);
					System.out.println(ex);
				}
				finally
				{
					muatData();
					autoNumber();
				}
			}
		});
		btnSimpan.setBounds(16, 166, 114, 49);
		contentPane.add(btnSimpan);
		
		btnEdit = new JButton("Edit");
		btnEdit.setIcon(new ImageIcon("src/hendi/export2xls/images/update.png"));
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent act) 
			{
				try
				{
					Connection konek = Koneksi.getKoneksi();
					String query = "UPDATE Data set Nama = ?, Alamat = ? WHERE ID = ? ";
					PreparedStatement prepare = konek.prepareStatement(query);
					
					prepare.setString(1,(txtNama.getText()));
					prepare.setString(2,(txtAlamat.getText()));
					prepare.setInt(3,Integer.parseInt(txtId.getText()));
					prepare.executeUpdate();
					JOptionPane.showMessageDialog(null,"Data berhasil diupdate","Pesan",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/Gambar/paneBerhasil.png"));
					prepare.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Data gagal diupdate","Pesan",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/Gambar/paneGagal.png"));
					System.out.println(ex);
				}
				finally
				{
					muatData();
				}
			}
		});
		btnEdit.setBounds(128, 166, 114, 49);
		contentPane.add(btnEdit);
		
		btnHapus = new JButton("Hapus");
		btnHapus.setIcon(new ImageIcon("src/hendi/export2xls/images/delete.png"));
		btnHapus.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent act) 
			{
				try
				{
					Connection konek = Koneksi.getKoneksi();
					String query = "DELETE FROM Data WHERE ID = ?";
					PreparedStatement prepare = konek.prepareStatement(query);
					
					int id = Integer.parseInt(txtId.getText());
					String digit = String.format("%03d",id);
					prepare.setInt(1,Integer.parseInt(digit));
					prepare.executeUpdate();
					JOptionPane.showMessageDialog(null,"Data berhasil dihapus","Pesan",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/Gambar/paneBerhasil.png"));
					prepare.close();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null,"Data gagal dihapus","Pesan",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/Gambar/paneGagal.png"));
					System.out.println(ex);
				}
				finally
				{
					muatData();
					txtNama.setText("");
					txtAlamat.setText("");
					txtNama.requestFocus();
				}
			}
		});
		btnHapus.setBounds(240, 166, 122, 49);
		contentPane.add(btnHapus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(398, 6, 421, 247);
		contentPane.add(scrollPane);
		
		tabelModel = new DefaultTableModel(null,data);
		tabel = new ModifyTable(tabelModel);
		tabel.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent me) 
			{
				int pilih = tabel.getSelectedRow();
				if(pilih < 0)
				{
					return;
				}
				int id = (int) tabelModel.getValueAt(pilih, 0);
				String digit = String.format("%03d", id);
				txtId.setText(digit);
				String nama = (String) tabelModel.getValueAt(pilih, 1);
				txtNama.setText(nama);
				String alamat = (String) tabelModel.getValueAt(pilih, 2);
				txtAlamat.setText(alamat);
			}
		});
		tabel.setModel(tabelModel);
		scrollPane.setViewportView(tabel);
		
		JButton btnExport = new JButton("Export ke Open Office Calc");
		btnExport.setIcon(new ImageIcon("src/hendi/export2xls/images/ooCalc.png"));
		btnExport.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent act)
			{
				exportCalc(tabel,new File("src/hendi/export2xls/result/DataExport.docx"));
			}
		});
		btnExport.setBounds(521, 257, 239, 51);
		contentPane.add(btnExport);
		
		JLabel lblBg = new JLabel("");
		lblBg.setIcon(new ImageIcon("src/hendi/export2xls/images/bubble.jpg"));
		lblBg.setBounds(0, 0, 833, 308);
		contentPane.add(lblBg);
		setLocationRelativeTo(null);
		muatData();
		autoNumber();
	}//Akhir Konstruktor
	
	public void muatData()
	{
		tabelModel.getDataVector().removeAllElements();
		tabelModel.fireTableDataChanged();
		
		try
		{
			Connection konek = Koneksi.getKoneksi();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM Data";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{
				Object obj[] = new Object[3];
				obj[0] = rs.getInt(1);
				obj[1] = rs.getString(2);
				obj[2] = rs.getString(3);
				
				tabelModel.addRow(obj);
			}
			rs.close();
			state.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void autoNumber()
	{
		try
		{
			Connection konek = Koneksi.getKoneksi();
			Statement state = konek.createStatement();
			String query = "SELECT MAX(ID) FROM Data";
			
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{
				int a = rs.getInt(1);
				txtId.setText(String.format("%03d",a+1));
				txtNama.setText("");
				txtAlamat.setText("");
			}
			rs.close();
			state.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void exportCalc(JTable tabel,File f)
	{
		try
		{
			int a,b;
			tabelModel = (DefaultTableModel) tabel.getModel();
			FileWriter fw = new FileWriter(f);
			for(a=0;a<tabelModel.getColumnCount();a=a+1)
			{
				fw.write(tabelModel.getColumnName(a) + " \t");
			}
			fw.write(" \n");
				for(a=0;a<tabelModel.getRowCount();a=a+1)
				{
					for(b=0;b<tabelModel.getColumnCount();b=b+1)
					{
						fw.write(tabelModel.getValueAt(a, b).toString() + " \t");
					}
					fw.write(" \n");
				}
				fw.close();
			JOptionPane.showMessageDialog(null,"Data berhasil di export ke Open Office Calc");
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] ar)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				try 
				{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					ExportFile frame = new ExportFile();
					frame.setVisible(true);
				} 
				catch (UnsupportedLookAndFeelException e) {
				} 
				catch (ClassNotFoundException e){
				} 
				catch (InstantiationException e) {
				} 
				catch (IllegalAccessException e) {
				}
			}
		});
	}
}
