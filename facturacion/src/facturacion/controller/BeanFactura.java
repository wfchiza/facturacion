package facturacion.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import facturacion.model.dao.entities.Cliente;
import facturacion.model.dao.entities.FacturaCab;
import facturacion.model.dao.entities.Producto;
import facturacion.model.manager.ManagerFacturacion;

/**
 * ManagedBean JSF para el manejo de la facturacion.
 * @author mrea
 *
 */
@ManagedBean
@SessionScoped
public class BeanFactura {
	private String cedulaCliente;
	private ManagerFacturacion managerFacturacion;
	private Integer codigoProducto;
	private Integer cantidadProducto;
	private FacturaCab facturaCabTmp;
	private boolean facturaCabTmpGuardada;
	
	public BeanFactura() {
		managerFacturacion = new ManagerFacturacion();
	}

	/**
	 * Action para la creacion de una factura temporal en memoria.
	 * Hace uso del componente {@link facturacion.model.manager.ManagerFacturacion ManagerFacturacion} de la capa model.
	 * @return outcome para la navegacion.
	 */
	public String crearNuevaFactura(){
		facturaCabTmp=managerFacturacion.crearFacturaTmp();
		cedulaCliente=null;
		codigoProducto=0;
		cantidadProducto=0;
		facturaCabTmpGuardada=false;
		return "";
	}
	/**
	 * Action para asignar un cliente a la factura temporal actual.
	 * Hace uso del componente {@link facturacion.model.manager.ManagerFacturacion ManagerFacturacion} de la capa model.
	 * @return outcome para la navegacion.
	 */
	public void asignarCliente(){
		if(facturaCabTmpGuardada==true){
			JSFUtil.crearMensajeWARN("La factura ya fue guardada.");
		}
		try {
			managerFacturacion.asignarClienteFacturaTmp(facturaCabTmp,cedulaCliente);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}
	
	/**
	 * Action que adiciona un item a una factura temporal.
	 * Hace uso del componente {@link model.manager.ManagerFacturacion ManagerFacturacion} de la capa model.
	 * @return
	 */
	public String insertarDetalle(){
		if(facturaCabTmpGuardada==true){
			JSFUtil.crearMensajeWARN("La factura ya fue guardada.");
			return "";
		}
		try {
			managerFacturacion.agregarDetalleFacturaTmp(facturaCabTmp,codigoProducto, cantidadProducto);
			codigoProducto=0;
			cantidadProducto=0;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}		
		return "";
	}
	
	/**
	 * Action que almacena en la base de datos una factura temporal creada en memoria.
	 * Hace uso del componente {@link facturacion.model.manager.ManagerFacturacion ManagerFacturacion} de la capa model.
	 * @return outcome para la navegacion.
	 */
	public String guardarFactura(){
		if(facturaCabTmpGuardada==true){
			JSFUtil.crearMensajeWARN("La factura ya fue guardada.");
			return "";
		}
		try {
			managerFacturacion.guardarFacturaTemporal(facturaCabTmp);
			facturaCabTmpGuardada=true;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
		
		return "";
	}
	
	public String getCedulaCliente() {
		return cedulaCliente;
	}

	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}

	public Integer getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(Integer codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public Integer getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(Integer cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	/**
	 * Devuelve un listado de componentes SelectItem a partir de un listado de {@link model.dao.entities.Cliente Cliente}.
	 * @return listado de SelectItems de clientes.
	 */
	public List<SelectItem> getListaClientesSI(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Cliente> listadoClientes=managerFacturacion.findAllClientes();
		
		for(Cliente c:listadoClientes){
			SelectItem item=new SelectItem(c.getCedulaCliente(), 
					                   c.getApellidos()+" "+c.getNombres());
			listadoSI.add(item);
		}
		return listadoSI;
	}
	/**
	 * Devuelve un listado de componentes SelectItem a partir de un listado de {@link model.dao.entities.Producto Producto}.
	 * 
	 * @return listado de SelectItems de productos.
	 */
	public List<SelectItem> getListaProductosSI(){
		List<SelectItem> listadoSI=new ArrayList<SelectItem>();
		List<Producto> listadoProductos=managerFacturacion.findAllProductos();
		
		for(Producto p:listadoProductos){
			SelectItem item=new SelectItem(p.getCodigoProducto(), 
					                   p.getNombre());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public FacturaCab getFacturaCabTmp() {
		return facturaCabTmp;
	}

	public void setFacturaCabTmp(FacturaCab facturaCabTmp) {
		this.facturaCabTmp = facturaCabTmp;
	}
	
	public List<FacturaCab> getListaFacturasCab(){
		List<FacturaCab> listadoFacturas=managerFacturacion.findAllFacturaCab();
		return listadoFacturas;
	}

	public boolean isFacturaCabTmpGuardada() {
		return facturaCabTmpGuardada;
	}

	public void setFacturaCabTmpGuardada(boolean facturaCabTmpGuardada) {
		this.facturaCabTmpGuardada = facturaCabTmpGuardada;
	}
	
}
