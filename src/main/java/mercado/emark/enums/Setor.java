package mercado.emark.enums;

public enum Setor {
    CAIXA("Caixa"),
    REPOSICAO("Reposição de Estoque"),
    GERENCIA("Gerência"),
    ATENDIMENTO("Atendimento ao Cliente"),
    FISCAL("Fiscal de Loja"),
    LIMPEZA("Limpeza e Zeladoria"),
    PERECIVEIS("Setor de Perecíveis"),
    FRIOS("Setor de Frios e Laticínios"),
    ACOUGUE("Açougue"),
    PADARIA("Padaria e Confeitaria"),
    HORTIFRUTI("Hortifruti"),
    BEBIDAS("Bebidas"),
    MERCEARIA("Mercearia"),
    RH("Recursos Humanos"),
    TI("Tecnologia da Informação"),
    MARKETING("Marketing"),
    SEGURANCA("Segurança"),
    RECEBIMENTO("Recebimento de Mercadorias"),
    ENTREGAS("Entregas e Logística");

    private final String descricao;

    Setor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}