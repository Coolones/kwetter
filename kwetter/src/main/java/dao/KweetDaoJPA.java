package dao;

import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@JPA
@Stateless
@SuppressWarnings("unchecked")
public class KweetDaoJPA extends DaoFacade<Kweet> implements IKweetDao {
    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public KweetDaoJPA() {
        super(Kweet.class);
    }

    public List<Kweet> findByText(String text) {
        return em.createQuery("SELECT k FROM Kweet k WHERE k.text LIKE :text")
                .setParameter("text", text)
                .getResultList();
    }

    public List<Kweet> findByUser(long id) {
        return em.createQuery("SELECT k from Kweet k WHERE k.user.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Kweet> findForUser(User entity) {
        return em.createQuery("SELECT k from Kweet k WHERE k.user.id = :id OR k.user = k.user.following ORDER BY k.date")
                .setParameter("id", entity.getId())
                .getResultList();
    }
}
