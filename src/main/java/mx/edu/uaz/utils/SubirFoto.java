package mx.edu.uaz.utils;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import mx.edu.uaz.modelos.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SubirFoto implements Receiver, SucceededListener {
	public File file;
	private Image img[];
	private String ruta,userImg;
	private Usuario user;
	
	public SubirFoto(Usuario user, Image... foto) {
		this.user = user;
		userImg = user.getNombre()+"-"+user.getId_usuario();
		ruta = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+"/images/";
		img = foto;
		file = null;
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null;
		
		if (!filename.equals("")){			
			String ext,imgS;
			int dot = filename.lastIndexOf('.');
			ext = filename.substring(dot + 1).toLowerCase();
			if (ext.equals("jpg") || ext.equals("png") || ext.equals("jpeg") || ext.equals("bmp")){
				imgS = searchFoto();
				if (!imgS.equals("")){
					file = new File(ruta + imgS);
					file.delete();				
				}
				try {
					file = new File(ruta + userImg+'.'+ext);
					fos = new FileOutputStream(file);
				}			
	        	catch (final Exception e) {
	        		Notification.show("No se pudo abrir la imagen ",e.getMessage(), Notification.Type.ERROR_MESSAGE);
	        	}
			}
			else{
				Notification.show("Archivo incomplatible ",filename+" no es un formato válido ", Notification.Type.ERROR_MESSAGE);
			}
		}
		else{
			Notification.show("Selecciona un archivo", Notification.Type.ERROR_MESSAGE);
		}
		 return fos; 
		
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		if (file!=null){
			Notification.show("Foto Actualizada", Notification.Type.HUMANIZED_MESSAGE);
			for (int i = 0; i < img.length; i++){
                img[i].setSource(new FileResource(file));
            }
		}
	}

	public FileResource getFoto(){
		String imgS=searchFoto();
		if (imgS.equals(""))
			imgS="user.png";
		return new FileResource(new File(ruta+imgS));
	}

	public String searchFoto(){
		String files[];
		File dir = new File(ruta);
		String archivo;
		String imgS = "";
		files = dir.list();
		for (int i = 0; i < files.length; i++) {
			int dot = files[i].lastIndexOf('.');
			archivo=files[i].substring(0,dot);
			if (archivo.equals(userImg)){
				imgS=files[i];
				break;
			}
		}
		return imgS;
	}

}
