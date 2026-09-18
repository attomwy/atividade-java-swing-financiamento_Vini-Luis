package br.edu.univille;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class JanelaPrincipal extends JFrame {

    private static final double TAXA = 0.32;

    private JComboBox<String> cbMarca;
    private JTextField txtModelo;
    private JComboBox<Integer> cbAno;
    private JTextField txtValor;
    private JRadioButton rbNovo;
    private JRadioButton rbUsado;
    private JPanel painelUsado;
    private JTextField txtQuilometragem;
    private JTextField txtProprietarios;
    private JCheckBox chkEntrada;
    private JPanel painelEntrada;
    private JTextField txtEntrada;
    private JComboBox<Integer> cbParcelas;
    private JPanel painelResultado;
    private JLabel lblValorFinanciado;
    private JLabel lblValorParcela;
    private JLabel lblTotalPagar;

    public JanelaPrincipal() {
        setTitle("Financiamento de Carros");
        setSize(650, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        montarTela();
        atualizarTela();

        setVisible(true);
    }

    private void montarTela() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Financiamento de Carros", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(new EmptyBorder(15, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(new EmptyBorder(5, 15, 15, 15));

        JPanel painelVeiculo = new JPanel(new GridLayout(0, 2, 10, 10));
        painelVeiculo.setBorder(BorderFactory.createTitledBorder("Dados do veículo"));
        painelVeiculo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 230));

        cbMarca = new JComboBox<>(new String[]{
                "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai",
                "Jeep", "Nissan", "Renault", "Toyota", "Volkswagen", "Outra"
        });

        txtModelo = new JTextField();

        Integer[] anos = new Integer[27];
        int ano = 2026;
        for (int i = 0; i < anos.length; i++) {
            anos[i] = ano--;
        }

        cbAno = new JComboBox<>(anos);
        txtValor = new JTextField();

        rbNovo = new JRadioButton("Novo", true);
        rbUsado = new JRadioButton("Usado");

        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbNovo);
        grupoTipo.add(rbUsado);

        JPanel tipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tipo.add(rbNovo);
        tipo.add(rbUsado);

        painelVeiculo.add(new JLabel("Marca:"));
        painelVeiculo.add(cbMarca);
        painelVeiculo.add(new JLabel("Modelo:"));
        painelVeiculo.add(txtModelo);
        painelVeiculo.add(new JLabel("Ano:"));
        painelVeiculo.add(cbAno);
        painelVeiculo.add(new JLabel("Valor do carro:"));
        painelVeiculo.add(txtValor);
        painelVeiculo.add(new JLabel("Tipo:"));
        painelVeiculo.add(tipo);

        painelUsado = new JPanel(new GridLayout(0, 2, 10, 10));
        painelUsado.setBorder(BorderFactory.createTitledBorder("Dados do veículo usado"));
        painelUsado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        txtQuilometragem = new JTextField();
        txtProprietarios = new JTextField();

        painelUsado.add(new JLabel("Quilometragem:"));
        painelUsado.add(txtQuilometragem);
        painelUsado.add(new JLabel("Proprietários:"));
        painelUsado.add(txtProprietarios);

        JPanel painelFinanciamento = new JPanel();
        painelFinanciamento.setLayout(new BoxLayout(painelFinanciamento, BoxLayout.Y_AXIS));
        painelFinanciamento.setBorder(BorderFactory.createTitledBorder("Financiamento"));
        painelFinanciamento.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        chkEntrada = new JCheckBox("Possui entrada");
        JPanel linhaCheck = new JPanel(new FlowLayout(FlowLayout.LEFT));
        linhaCheck.add(chkEntrada);

        painelEntrada = new JPanel(new GridLayout(1, 2, 10, 10));
        txtEntrada = new JTextField();
        painelEntrada.add(new JLabel("Valor da entrada:"));
        painelEntrada.add(txtEntrada);

        JPanel linhaParcelas = new JPanel(new GridLayout(1, 2, 10, 10));
        linhaParcelas.setBorder(new EmptyBorder(8, 0, 0, 0));
        cbParcelas = new JComboBox<>(new Integer[]{12, 24, 36, 48, 60});
        linhaParcelas.add(new JLabel("Quantidade de parcelas:"));
        linhaParcelas.add(cbParcelas);

        painelFinanciamento.add(linhaCheck);
        painelFinanciamento.add(painelEntrada);
        painelFinanciamento.add(linhaParcelas);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCalcular = new JButton("Calcular");
        JButton btnLimpar = new JButton("Limpar");
        botoes.add(btnCalcular);
        botoes.add(btnLimpar);

        painelResultado = new JPanel(new GridLayout(0, 2, 10, 10));
        painelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));
        painelResultado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        lblValorFinanciado = new JLabel();
        lblValorParcela = new JLabel();
        lblTotalPagar = new JLabel();

        painelResultado.add(new JLabel("Valor financiado:"));
        painelResultado.add(lblValorFinanciado);
        painelResultado.add(new JLabel("Valor da parcela:"));
        painelResultado.add(lblValorParcela);
        painelResultado.add(new JLabel("Total a pagar:"));
        painelResultado.add(lblTotalPagar);
        painelResultado.setVisible(false);

        rbNovo.addActionListener(e -> atualizarTela());
        rbUsado.addActionListener(e -> atualizarTela());
        chkEntrada.addActionListener(e -> atualizarTela());
        btnCalcular.addActionListener(e -> calcular());
        btnLimpar.addActionListener(e -> limpar());

        conteudo.add(painelVeiculo);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(painelUsado);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(painelFinanciamento);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(botoes);
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(painelResultado);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(conteudo, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(topo);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        add(scroll, BorderLayout.CENTER);
    }

    private void atualizarTela() {
        painelUsado.setVisible(rbUsado.isSelected());
        painelEntrada.setVisible(chkEntrada.isSelected());

        revalidate();
        repaint();
    }

    private void calcular() {
        try {
            if (txtModelo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o modelo do veículo.");
                return;
            }

            double valorVeiculo = lerNumero(txtValor.getText());

            if (valorVeiculo <= 0) {
                JOptionPane.showMessageDialog(this, "Informe um valor válido para o veículo.");
                return;
            }

            if (rbUsado.isSelected()) {
                double km = lerNumero(txtQuilometragem.getText());
                int proprietarios = Integer.parseInt(txtProprietarios.getText().trim());

                if (km < 0 || proprietarios <= 0) {
                    JOptionPane.showMessageDialog(this, "Preencha os dados do veículo usado corretamente.");
                    return;
                }
            }

            double entrada = 0;

            if (chkEntrada.isSelected()) {
                entrada = lerNumero(txtEntrada.getText());

                if (entrada < 0 || entrada >= valorVeiculo) {
                    JOptionPane.showMessageDialog(this, "Informe um valor de entrada válido.");
                    return;
                }
            }

            int parcelas = (Integer) cbParcelas.getSelectedItem();

            double valorFinanciado = valorVeiculo - entrada;
            double valorTotal = valorFinanciado * (1 + TAXA);
            double valorParcela = valorTotal / parcelas;

            NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

            lblValorFinanciado.setText(moeda.format(valorFinanciado));
            lblValorParcela.setText(moeda.format(valorParcela));
            lblTotalPagar.setText(moeda.format(valorTotal));

            painelResultado.setVisible(true);
            revalidate();
            repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preencha os campos numéricos corretamente.");
        }
    }

    private double lerNumero(String texto) {
        String valor = texto.trim().replace("R$", "").replace(" ", "");

        if (valor.contains(",")) {
            valor = valor.replace(".", "").replace(",", ".");
        }

        return Double.parseDouble(valor);
    }

    private void limpar() {
        cbMarca.setSelectedIndex(0);
        txtModelo.setText("");
        cbAno.setSelectedIndex(0);
        txtValor.setText("");

        rbNovo.setSelected(true);
        txtQuilometragem.setText("");
        txtProprietarios.setText("");

        chkEntrada.setSelected(false);
        txtEntrada.setText("");
        cbParcelas.setSelectedIndex(0);

        painelResultado.setVisible(false);
        atualizarTela();
    }
}
