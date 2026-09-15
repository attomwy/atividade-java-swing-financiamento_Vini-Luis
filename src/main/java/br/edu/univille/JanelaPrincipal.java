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
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Financiamento de Carros", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(new EmptyBorder(15, 10, 5, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel conteudo = new JPanel();
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
        conteudo.setBorder(new EmptyBorder(5, 15, 15, 15));

        conteudo.add(criarPainelVeiculo());
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(criarPainelUsado());
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(criarPainelFinanciamento());
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(criarPainelBotoes());
        conteudo.add(Box.createVerticalStrut(10));
        conteudo.add(criarPainelResultado());

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(conteudo, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(topo);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel criarPainelVeiculo() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createTitledBorder("Dados do veículo"));

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

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTipo.add(rbNovo);
        painelTipo.add(rbUsado);

        rbNovo.addActionListener(e -> atualizarTela());
        rbUsado.addActionListener(e -> atualizarTela());

        painel.add(new JLabel("Marca:"));
        painel.add(cbMarca);
        painel.add(new JLabel("Modelo:"));
        painel.add(txtModelo);
        painel.add(new JLabel("Ano:"));
        painel.add(cbAno);
        painel.add(new JLabel("Valor do carro:"));
        painel.add(txtValor);
        painel.add(new JLabel("Tipo:"));
        painel.add(painelTipo);

        return painel;
    }

    private JPanel criarPainelUsado() {
        painelUsado = new JPanel(new GridLayout(0, 2, 10, 10));
        painelUsado.setBorder(BorderFactory.createTitledBorder("Dados do veículo usado"));

        txtQuilometragem = new JTextField();
        txtProprietarios = new JTextField();

        painelUsado.add(new JLabel("Quilometragem:"));
        painelUsado.add(txtQuilometragem);
        painelUsado.add(new JLabel("Proprietários:"));
        painelUsado.add(txtProprietarios);

        return painelUsado;
    }

    private JPanel criarPainelFinanciamento() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createTitledBorder("Financiamento"));

        chkEntrada = new JCheckBox("Possui entrada");
        chkEntrada.addActionListener(e -> atualizarTela());

        painelEntrada = new JPanel(new GridLayout(1, 2, 10, 10));
        txtEntrada = new JTextField();
        painelEntrada.add(new JLabel("Valor da entrada:"));
        painelEntrada.add(txtEntrada);

        JPanel linhaParcelas = new JPanel(new GridLayout(1, 2, 10, 10));
        cbParcelas = new JComboBox<>(new Integer[]{12, 24, 36, 48, 60});
        linhaParcelas.add(new JLabel("Quantidade de parcelas:"));
        linhaParcelas.add(cbParcelas);

        painel.add(chkEntrada);
        painel.add(painelEntrada);
        painel.add(linhaParcelas);

        return painel;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnLimpar = new JButton("Limpar");
        JButton btnCalcular = new JButton("Calcular");

        btnLimpar.addActionListener(e -> limpar());
        btnCalcular.addActionListener(e -> calcular());

        painel.add(btnLimpar);
        painel.add(btnCalcular);

        return painel;
    }

    private JPanel criarPainelResultado() {
        painelResultado = new JPanel(new GridLayout(0, 2, 10, 10));
        painelResultado.setBorder(BorderFactory.createTitledBorder("Resultado"));

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

        return painelResultado;
    }

    private void atualizarTela() {
        if (painelUsado != null) {
            painelUsado.setVisible(rbUsado.isSelected());
        }

        if (painelEntrada != null) {
            painelEntrada.setVisible(chkEntrada.isSelected());
        }

        revalidate();
        repaint();
    }

    private void calcular() {
        try {
            if (txtModelo.getText().trim().isEmpty()) {
                mostrarErro("Informe o modelo do veículo.");
                return;
            }

            double valorVeiculo = lerNumero(txtValor.getText());
            if (valorVeiculo <= 0) {
                mostrarErro("O valor do veículo deve ser maior que zero.");
                return;
            }

            if (rbUsado.isSelected()) {
                double km = lerNumero(txtQuilometragem.getText());
                int proprietarios = Integer.parseInt(txtProprietarios.getText().trim());

                if (km < 0 || proprietarios <= 0) {
                    mostrarErro("Preencha os dados do veículo usado corretamente.");
                    return;
                }
            }

            double entrada = 0;
            if (chkEntrada.isSelected()) {
                entrada = lerNumero(txtEntrada.getText());

                if (entrada < 0 || entrada >= valorVeiculo) {
                    mostrarErro("A entrada deve ser menor que o valor do veículo.");
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
            mostrarErro("Preencha os campos numéricos corretamente.");
        }
    }

    private double lerNumero(String texto) {
        String valor = texto.trim().replace("R$", "").replace(" ", "");

        if (valor.isEmpty()) {
            throw new NumberFormatException();
        }

        if (valor.contains(",")) {
            valor = valor.replace(".", "").replace(",", ".");
        }

        return Double.parseDouble(valor);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
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
