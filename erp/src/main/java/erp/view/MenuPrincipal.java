package erp.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Define o visual moderno do sistema operacional
					javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
					
					MenuPrincipal frame = new MenuPrincipal();
					frame.setLocationRelativeTo(null); // Centraliza o menu
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		setTitle("Sistema ERP - Menu Principal");
		// REGRA DE OURO: EXIT_ON_CLOSE apenas no Menu Principal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnProdutos = new JButton("Gerenciar Estoque");
		btnProdutos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnProdutos.setBounds(10, 95, 136, 39);
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre a tela de produtos e fecha apenas ela ao sair (DISPOSE)
				FormProdutos telaProdutos = new FormProdutos();
				telaProdutos.setLocationRelativeTo(null);
				telaProdutos.setVisible(true);
			}
		});
		contentPane.add(btnProdutos);
		
		JButton btnVendas = new JButton("Ponto de Venda");
		btnVendas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnVendas.setBounds(156, 95, 115, 39);
		btnVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormVendas telaVendas = new FormVendas();
				telaVendas.setLocationRelativeTo(null);
				telaVendas.setVisible(true);
			}
		});
		contentPane.add(btnVendas);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSair.setBounds(295, 95, 84, 39);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(null, 
						"Você tem certeza que deseja fechar o sistema?", 
						"Confirmação de Saída", 
						JOptionPane.YES_NO_OPTION);
				
				if (resposta == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		contentPane.add(btnSair);
	}
}