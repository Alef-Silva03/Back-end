package interfacegui.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import interfacegui.dao.ProdutoDAO;
import interfacegui.model.Produto;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class FormProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField txtEstoque;
	private JTable tabelaProdutos;
	private JButton btnEditar;
	private JButton btnExcluir;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormProdutos frame = new FormProdutos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FormProdutos() {
		setTitle("Cadastro de Produtos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(100, 100, 690, 466);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nome Produto:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(18, 26, 109, 31);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Preço:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(18, 79, 80, 42);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Estoque:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(18, 150, 63, 31);
		contentPane.add(lblNewLabel_2);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNome.setBounds(171, 26, 292, 31);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtPreco = new JTextField();
		txtPreco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPreco.setBounds(171, 86, 106, 29);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);

		txtEstoque = new JTextField();
		txtEstoque.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtEstoque.setBounds(171, 154, 80, 31);
		contentPane.add(txtEstoque);
		txtEstoque.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Produto obj = new Produto();
					obj.setNome(txtNome.getText());
					obj.setPreco(Double.parseDouble(txtPreco.getText()));
					obj.setEstoque(Integer.parseInt(txtEstoque.getText()));

					ProdutoDAO dao = new ProdutoDAO();
					dao.cadastrar(obj);

					JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
					
					// Atualiza a tabela após salvar
					listarNaTabela();
					
					// Limpa os campos
					txtNome.setText("");
					txtPreco.setText("");
					txtEstoque.setText("");
					
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar: " + erro.getMessage());
				}
			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSalvar.setBounds(29, 233, 90, 30);
		contentPane.add(btnSalvar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(142, 300, 450, 104);
		contentPane.add(scrollPane);

		// Inicializando a tabela com um modelo padrão
		tabelaProdutos = new JTable();
		tabelaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Ao clicar em uma linha, os dados vão para os campos de texto
			    txtNome.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1).toString());
			    txtPreco.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 2).toString());
			    txtEstoque.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 3).toString());
			}
		});
		tabelaProdutos.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "ID", "Produto", "Preço", "Estoque" }
		));
		scrollPane.setViewportView(tabelaProdutos);
		
		btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				    Produto obj = new Produto();
				    // Pegamos o ID da linha selecionada na tabela
				    int linha = tabelaProdutos.getSelectedRow();
				    obj.setId((int) tabelaProdutos.getValueAt(linha, 0));
				    
				    obj.setNome(txtNome.getText());
				    obj.setPreco(Double.parseDouble(txtPreco.getText()));
				    obj.setEstoque(Integer.parseInt(txtEstoque.getText()));

				    ProdutoDAO dao = new ProdutoDAO();
				    dao.editar(obj);
				    
				    listarNaTabela();
				    JOptionPane.showMessageDialog(null, "Alterado com sucesso!");
				} catch (Exception erro) {
				    JOptionPane.showMessageDialog(null, "Selecione um produto e preencha os campos.");
				}
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnEditar.setBounds(142, 233, 90, 30);
		contentPane.add(btnEditar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linha = tabelaProdutos.getSelectedRow();
				if (linha != -1) {
				    int id = (int) tabelaProdutos.getValueAt(linha, 0); // Pega o ID da primeira coluna
				    int conf = JOptionPane.showConfirmDialog(null, "Deseja excluir este item?");
				    if (conf == JOptionPane.YES_OPTION) {
				        ProdutoDAO dao = new ProdutoDAO();
				        dao.excluir(id);
				        listarNaTabela(); // Atualiza a tabela
				        JOptionPane.showMessageDialog(null, "Excluído com sucesso!");
				    }
				} else {
				    JOptionPane.showMessageDialog(null, "Selecione um item na tabela primeiro.");
				}
			}
		});
		btnExcluir.setBounds(267, 233, 90, 30);
		contentPane.add(btnExcluir);
		
		// Carrega os dados assim que a tela abre
		listarNaTabela();
	}

	public void listarNaTabela() {
		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> lista = dao.listarProdutos();

		DefaultTableModel dados = (DefaultTableModel) tabelaProdutos.getModel();
		dados.setNumRows(0);

		for (Produto p : lista) {
			dados.addRow(new Object[] { 
				p.getId(), 
				p.getNome(), 
				p.getPreco(), 
				p.getEstoque() 
			});
		}
	}
}

