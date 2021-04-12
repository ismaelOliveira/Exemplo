package br.com.bonsai.repository;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;

import br.com.bonsai.auditoria.EntidadeComRevisao;
import br.com.bonsai.auditoria.Revisao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@Transactional
public class GenericRevisionRepository<T> {

	@PersistenceContext
	private EntityManager entityManager;

	public List<EntidadeComRevisao<T>> listaRevisoes(Integer id, Class<T> tipo) {
		AuditReader auditReader = AuditReaderFactory.get(entityManager);
		List<Number> idsRevisao = auditReader.getRevisions(tipo, id);
		List<Object[]> resultList = auditReader.createQuery()
			    .forRevisionsOfEntity(tipo, tipo.getName(), false, true)
			    .add(AuditEntity.revisionNumber().in(idsRevisao)).getResultList();
		
		
		List<EntidadeComRevisao<T>> allRevisions = new ArrayList<>();
		for (Object[] resultado : resultList) {
			
			allRevisions.add(new EntidadeComRevisao((Revisao)resultado[1],resultado[0],	resultado[2].toString()));
		}
		return allRevisions;
	}

}
