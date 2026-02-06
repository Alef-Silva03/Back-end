package interfacegui.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import interfacegui.dao.ProdutoDAO;
import interfacegui.model.Produto;

public class FormVendas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdProduto;
	private JTextField txtQuantidade;
	private JTextArea txtCupom2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormVendas frame = new FormVendas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormVendas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id Produto:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(161, 93, 80, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(161, 147, 80, 14);
		contentPane.add(lblNewLabel_1);
		
		txtIdProduto = new JTextField();
		txtIdProduto.setBounds(251, 92, 130, 20);
		contentPane.add(txtIdProduto);
		txtIdProduto.setColumns(10);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setBounds(251, 146, 130, 20);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Validação simples para evitar erro de campo vazio
					if(txtIdProduto.getText().isEmpty() || txtQuantidade.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha ID e Quantidade!");
						return;
					}

					int id = Integer.parseInt(txtIdProduto.getText().trim());
					int qtd = Integer.parseInt(txtQuantidade.getText().trim());

					ProdutoDAO dao = new ProdutoDAO();
					Produto p = dao.buscarPorId(id);

					if (p != null && p.getNome() != null) {
						double subtotal = p.getPreco() * qtd;
						txtCupom2.append("Item: " + p.getNome() + " | Qtd: " + qtd + " | R$ " + subtotal + "\n");
						
						txtIdProduto.setText("");
						txtQuantidade.setText("");
						txtIdProduto.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "Produto não encontrado!");
					}
				} catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(null, "Erro: Digite apenas números!");
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro: " + erro.getMessage());
				}
			}
		});
		btnAdicionar.setBounds(128, 246, 130, 28);
		contentPane.add(btnAdicionar);
		
		JButton btnFinalizar = new JButton("Finalizar Venda");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Aqui você pode implementar a lógica de baixar todos os itens ou apenas o último
					txtCupom2.append("----------------------------------\n");
					txtCupom2.append("VENDA FINALIZADA!\n");
					JOptionPane.showMessageDialog(null, "Venda concluída com sucesso!");
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro na venda: " + erro.getMessage());
				}
			}
		});
		btnFinalizar.setBounds(352, 248, 130, 25);
		contentPane.add(btnFinalizar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(94, 303, 430, 117);
		contentPane.add(scrollPane);
		
		JTextArea txtCupom2 = new JTextArea();
		scrollPane.setViewportView(txtCupom2);

	}
}
