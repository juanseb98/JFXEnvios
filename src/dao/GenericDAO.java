package dao;

import org.hibernate.Session;

import ajustesHibernate.HibernateUtil;
import javax.validation.ConstraintViolationException;

public class GenericDAO<T> {

    /**
     * Metodo generico para guardar objeto en base de datos
     *
     * @param entidad Objeto a guardar y actualizar en la base de datos
     * @throws ConstraintViolationException
     */
    public void guardarActualizar(T entidad) throws ConstraintViolationException {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(entidad);
        } catch (ConstraintViolationException e) {
            session.getTransaction().rollback();
            throw e;
        }
        session.getTransaction().commit();

    }

    /**
     * Metodo generico para eliminar un objeto de la base de datos
     *
     * @param entidad Objeto a eliminar
     * @throws Exception
     */
    public void borrar(T entidad) throws Exception {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(entidad);
        session.getTransaction().commit();
    }

}
