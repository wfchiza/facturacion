package facturacion.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import facturacion.model.dao.entities.Parametro;
import facturacion.model.manager.ManagerFacturacion;

/**
 * ManagedBean JSF para el manejo de parametros.
 * @author mrea
 *
 */
@ManagedBean
@RequestScoped
public class BeanParametro {
	private ManagerFacturacion mFacturacion=new ManagerFacturacion();
	
	public List<Parametro> getListaParametros(){
		return mFacturacion.findAllParametros();
	}
	
}
