package interfacegui.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnProdutos = new JButton("Gerenciar Estoque");
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Abre a tela de produtos e fecha apenas ela ao sair (DISPOSE)
				FormProdutos telaProdutos = new FormProdutos();
				telaProdutos.setLocationRelativeTo(null);
				telaProdutos.setVisible(true);
			}
		});
		btnProdutos.setBounds(88, 88, 121, 46);
		contentPane.add(btnProdutos);
		
		JButton btnVendas = new JButton("Ponto de Venda");
		btnVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FormVendas telaVendas = new FormVendas();
				telaVendas.setLocationRelativeTo(null);
				telaVendas.setVisible(true);
			}
		});
		btnVendas.setBounds(246, 88, 115, 46);
		contentPane.add(btnVendas);
		
		JButton btnSair = new JButton("Sair");
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
		btnSair.setBounds(312, 214, 98, 36);
		contentPane.add(btnSair);

	}

}
