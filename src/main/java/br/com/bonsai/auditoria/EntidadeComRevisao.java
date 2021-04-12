package br.com.bonsai.auditoria;

public class EntidadeComRevisao<T> {

	private Revisao revisao;

    private T entidade;
    
    private String operacao;

    public EntidadeComRevisao(Revisao revision, T entity, String operacao) {
        this.revisao = revision;
        this.entidade = entity;
        this.operacao = operacao;
    }

    public Revisao getRevisao() {
        return revisao;
    }

    public void setRevisao(Revisao revisao) {
        this.revisao = revisao;
    }

    public T getEntidade() {
        return entidade;
    }

    public void setEntidade(T entidade) {
        this.entidade = entidade;
    }

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
}
