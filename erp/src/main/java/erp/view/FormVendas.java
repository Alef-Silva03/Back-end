package erp.view;

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
import erp.dao.ProdutoDAO;
import erp.model.Produto;

public class FormVendas extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIdProduto;
	private JTextField txtQuantidade;
	private JTextArea txtCupom;

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

	public FormVendas() {
		setTitle("Vendas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		setBounds(100, 100, 723, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id Produto:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(29, 71, 90, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Quantidade:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(29, 117, 63, 22);
		contentPane.add(lblNewLabel_1);
		
		txtIdProduto = new JTextField();
		txtIdProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtIdProduto.setBounds(129, 81, 96, 18);
		contentPane.add(txtIdProduto);
		txtIdProduto.setColumns(10);
		
		txtQuantidade = new JTextField();
		txtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtQuantidade.setBounds(129, 120, 96, 18);
		contentPane.add(txtQuantidade);
		txtQuantidade.setColumns(10);
		
		// BOTÃO ADICIONAR CORRIGIDO
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
						txtCupom.append("Item: " + p.getNome() + " | Qtd: " + qtd + " | R$ " + subtotal + "\n");
						
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
		btnAdicionar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAdicionar.setBounds(43, 182, 96, 36);
		contentPane.add(btnAdicionar);
		
		// BOTÃO FINALIZAR CORRIGIDO
		JButton btnFinalizar = new JButton("Finalizar Venda");
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Aqui você pode implementar a lógica de baixar todos os itens ou apenas o último
					txtCupom.append("----------------------------------\n");
					txtCupom.append("VENDA FINALIZADA!\n");
					JOptionPane.showMessageDialog(null, "Venda concluída com sucesso!");
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Erro na venda: " + erro.getMessage());
				}
			}
		});
		btnFinalizar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnFinalizar.setBounds(214, 182, 114, 36);
		contentPane.add(btnFinalizar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(129, 309, 431, 146);
		contentPane.add(scrollPane);
		
		txtCupom = new JTextArea();
		txtCupom.setEditable(false);
		txtCupom.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(txtCupom);
	}
}