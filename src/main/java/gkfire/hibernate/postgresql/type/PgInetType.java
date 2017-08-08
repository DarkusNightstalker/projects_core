/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.hibernate.postgresql.type;

import gkfire.hibernate.postgresql.PgInet;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Darkus Nightmare
 */
public class PgInetType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @Override
    public Class returnedClass() {
        return PgInet.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x != null) {
            return x.hashCode();
        } else {
            return 0;
        }
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,
            SessionImplementor session, Object owner)
            throws HibernateException, SQLException {

        //Translation from DB to Java
        PgInet address = null;

        String ip = rs.getString(names[0]);

        if (ip != null) {
            address = new PgInet(ip);
        }

        return address;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {

        //Translation from java to DB
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {

            //As inet,macaddr,cdir...are new types object on Postgresql you must
            //create the specific postgresql object and to insert it
            //I created 2 new cast on postgresql: inet As varchar, varchar AS inet
            //but I think it's not neccesary because macaddr type works fine without
            //postgresl casting
            st.setObject(index, getInet(value, st.getConnection()));
        }

    }

    private Object getInet(Object value, Connection connection) {

        //Expected object on postgresql
        Object tempInet = null;
        ClassLoader connectionClassLoader = connection.getClass().getClassLoader();

        try {

            //Class which will create the postgresql
            Class aPGObjectClass = connectionClassLoader.loadClass("org.postgresql.util.PGobject");
            Constructor ct = aPGObjectClass.getConstructor(null);
            try {
                tempInet = ct.newInstance(null);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

            Method setTypeMethod = aPGObjectClass.getMethod("setType", new Class[]{String.class});
            try {

                //Setting postgresql type, inet in this case
                setTypeMethod.invoke(tempInet, new Object[]{"inet"});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            Method setValueMethod = aPGObjectClass.getMethod("setValue", new Class[]{String.class});
            try {
                setValueMethod.invoke(tempInet, new Object[]{value.toString()});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return tempInet;
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        } else {
            PgInet PgInetNew = new PgInet();
            PgInet PgInetOriginal = (PgInet) value;

            PgInetNew.setAddress(PgInetOriginal.getAddress());

            return PgInetNew;
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable)) {
            return (Serializable) deepCopy;
        }

        return null;
    }

    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }

}
