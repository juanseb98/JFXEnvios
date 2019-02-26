/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import Objetos.Paquete;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author marlera
 */
public class AbrirReportes extends JFrame {

    public void abrirReporte(List<Paquete> lista) throws JRException {
        // TODO code application logic here
        /*
         * JasperReports  nos proporciona las clases:
         JRBeanCollectionDataSource
         JRJpaDataSource
         JRBeanArrayDataSource

         Las cuales ya implementan la interface "JRDataSource". Cada una funciona
         * con distintos tipos de datos, pero en que nos interesa en este momento
         * es "JRBeanCollectionDataSource" que puede convertir una
         * "java.util.Collection" en un DataSource de forma automática
         */
        int entregado = 0;
        for (Paquete object : lista) {
            if (object.isEntregado()) {
                entregado++;
            }
        }

        String fis = "informes/Reporte.jrxml";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("entregados", entregado);

        JasperReport jasperReport = JasperCompileManager.compileReport(fis);
        JasperPrint jasper = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));

        JRViewer viewer = new JRViewer(jasper);

        viewer.setOpaque(true);
        viewer.setVisible(true);
        add(viewer);
        setSize(700, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
